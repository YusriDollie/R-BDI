package plainBDI;


import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;
import jadex.bdiv3.annotation.GoalCreationCondition;
import jadex.bdiv3.annotation.GoalContextCondition;
import jadex.bdiv3.annotation.GoalDropCondition;
import jadex.bdiv3.annotation.GoalInhibit;
import jadex.bdiv3.annotation.GoalMaintainCondition;
import jadex.bdiv3.annotation.GoalTargetCondition;
import jadex.bdiv3.annotation.GoalRecurCondition;
import jadex.bdiv3.model.MProcessableElement.ExcludeMode;
import jadex.bdiv3.model.MGoal;

import jadex.bdiv3.annotation.Deliberation;
import jadex.bdiv3.annotation.GoalMaintainCondition;
import jadex.bdiv3.annotation.GoalTargetCondition;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.commons.future.IFuture;
import jadex.commons.transformation.annotations.Classname;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentKilled;
import jadex.bdiv3.runtime.impl.PlanFailureException;

import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.micro.annotation.AgentFeature;
import jadex.bdiv3.annotation.Body;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.service.annotation.Service;
import jadex.bdiv3.annotation.PlanContextCondition;
import jadex.bdiv3.annotation.PlanPassed;
import jadex.bdiv3.annotation.PlanFailed;
import jadex.bdiv3.annotation.PlanAborted;
import jadex.bdiv3.annotation.PlanAPI;
import jadex.bdiv3.runtime.IPlan;
import jadex.bdiv3.annotation.Plans;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Description;
import jadex.bdiv3.runtime.ChangeEvent;
import jadex.bdiv3.annotation.Belief;
import jadex.bridge.component.IExecutionFeature;
import jadex.rules.eca.ChangeInfo;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.State;



/**
 *  QCog BDI Agent
 *  Author Yusri Dollie
 *
 */
@Agent
@Description("BCog BDI Agent. <br>  all plans contained as inner classes attached to goals.")
//outer plan annotation
//@Plans(@Plan(body=@Body(ActionPlan.class)))
public class plainBDI {

    public enum Action {

        EXPLORE, REST, SEEK_WATER, MOVE_TO_LOCATION, ENGAGE_ENTITY, EAT_ITEM, DRINK, ATTACK, FLEE, SEEK_FOOD;

        int select() {

            switch (this) {

                case EXPLORE:
                    return 0;
                case REST:
                    return 1;
                case MOVE_TO_LOCATION:
                    return 4;
                case ENGAGE_ENTITY:
                    return 5;
                case EAT_ITEM:
                    return 6;
                case DRINK:
                    return 7;
                case ATTACK:
                    return 9;
                case FLEE:
                    return 8;
                case SEEK_FOOD:
                    return 3;
                case SEEK_WATER:
                    return 2;
                default:
                    return -1;

            }

        }

    }


    String stateJson;
    BCogstate state;
    ObjectMapper mapper;

    /**
     * The agent.
     */


    @AgentFeature
    protected IBDIAgentFeature bdiFeature;

    @AgentFeature
    protected IExecutionFeature execFeature;


    @Belief
    protected boolean run = true;


    @Belief
    protected int SuccessCount = 0;

    @Belief
    public int getSuccessCount() {
        return SuccessCount;
    }

    @Belief
    public void setSuccessCount(int SuccessCount) {
        this.SuccessCount = SuccessCount;
    }

    @Belief
    protected int KillCount = 0;

    @Belief
    public int getKillCount() {
        return KillCount;
    }

    @Belief
    public void setKillCount(int KillCount) {
        this.KillCount = KillCount;
    }



    @Belief
    protected int DeathCount = 0;

    @Belief
    public int getDeathCount() {
        return DeathCount;
    }

    @Belief
    public void setDeathCount(int DeathCount) {
        this.DeathCount = DeathCount;
    }


    //learning health thresholds for fight v flight

    @Belief
    protected int HThresh = 5;

    @Belief
    public int getHThresh() {
        return HThresh;
    }

    @Belief
    public void setHThresh(int HThresh) {
    //clamping thresholds
        if(HThresh>1 && HThresh<10) {

            this.HThresh = HThresh;
        }
    }



    //learning danger thresholds for fight v flight

    @Belief
    protected int DThresh = 1;

    @Belief
    public int getDThresh() {
        return DThresh;
    }

    @Belief
    public void setDThresh(int DThresh) {
    //clamping thresholds
        if(DThresh>0 && DThresh<4) {

            this.DThresh = DThresh;

        }
    }


    @Belief
    protected boolean seenEnemy = false;

    @Belief
    public boolean getSeenEnemy() {
        return seenEnemy;
    }

    @Belief
    public void setSeenEnemy(boolean seenEnemy) {

        //System.out.println("Enemy currently Visible:"+seenEnemy);
        this.seenEnemy = seenEnemy;
    }



    @Belief
    protected boolean seenFood = false;

    @Belief
    public boolean getSeenFood() {
        return seenFood;
    }

    @Belief
    public void setSeenFood(boolean seenFood) {

        //System.out.println("Food currently Visible:"+ seenFood);
        this.seenFood = seenFood;
    }


    //dynamic danger belief based on state calcualtion done by humanoid
    @Belief
    protected int danger = 0;

    @Belief
    public int getDanger() {
        return danger;
    }

    @Belief
    public void setDanger(int danger) {
        this.danger = danger;
    }


    //dynamic health belief based on perceptions updated from unity

    @Belief
    protected int health = 10;

    @Belief
    public int getHealth() {
        return health;
    }

    @Belief
    public void setHealth(int health) {
        this.health = health;
    }


    @AgentCreated
    public void init() {

        state = new BCogstate();

    }


    @Goal(deliberation=@Deliberation(inhibits={ExploreGoal.class, AttackGoal.class}),excludemode = ExcludeMode.Never, orsuccess=false)
    public class SurvivalGoal {


        @GoalMaintainCondition(beliefs = {"health","HThresh"})
        protected boolean maintain() {
            return health > 5;
        }

        @GoalTargetCondition(beliefs = {"health","HThresh"})
        protected boolean target() {
            return health >= 7;
        }



    }


//    @Plan(trigger = @Trigger(goals = SurvivalGoal.class), priority= "eatProb")
@Plan(trigger = @Trigger(goals = SurvivalGoal.class))
    protected void findFood(){
        System.out.println("Survival Eat Plan");
        System.out.println("Food Visible:"+getSeenFood());
        System.out.println("Health:"+getHealth());
        System.out.println("Theshold:"+ getHThresh());
        System.out.println("Enemy Visible:"+getSeenEnemy());
        if(getSeenFood()){
            System.out.println("Triggered Eat");

           // System.out.println(mapper.writeValueAsString(state));
            state.sendCommand(Action.EAT_ITEM.select());
            //prevent double eating
            execFeature.waitForDelay(500).get();

        }
        else{

            //find food
            state.sendCommand(Action.EXPLORE.select());
            //prevent exploring per frame
            execFeature.waitForDelay(1000).get();


        }


    }



//   @Plan(trigger = @Trigger(goals = SurvivalGoal.class), priority= "fleeProb")
 @Plan(trigger = @Trigger(goals = SurvivalGoal.class))
    protected void runAway() {


        System.out.println("Survival Flee Plan");
        System.out.println("Food Visible:" + getSeenFood());
        System.out.println("Health:" + getHealth());
        System.out.println("Theshold:" + getHThresh());
        System.out.println("Enemy Visible:" + getSeenEnemy());
        if(getDanger()>0){
            System.out.println("Triggered Flee");
            state.sendCommand(Action.FLEE.select());
            //execFeature.waitForDelay(500).get();

        }

    }


    @Goal(excludemode = ExcludeMode.Never, orsuccess=false)
    public class PercieveGoal {
        @GoalContextCondition(beliefs="run")
        public boolean checkContext()
        {
            return run==true;
        }



    }

    @Plan(trigger = @Trigger(goals = PercieveGoal.class))
    protected void look() {
       // System.out.println("Percieving");
        state.update();
        setHealth((int)state.getHealth());
        setDanger(state.getState().danger_level);
        setSeenEnemy(state.enemyFlag);
        setSeenFood(state.foodFlag);
        setDeathCount(state.getDeaths());
        setKillCount(state.getKills());
        execFeature.waitForDelay(50).get();
    }


    @Goal(excludemode = ExcludeMode.Never,orsuccess=false)
    public class ExploreGoal {
        @GoalContextCondition(beliefs="danger")
        public boolean checkContext()
        {
            return danger<2;
        }
    }

    @Plan(trigger = @Trigger(goals = ExploreGoal.class))
    protected void explore() {
        System.out.println("Triggered Explore");
        state.sendCommand(Action.EXPLORE.select());
        //prevent exploring per frame
        execFeature.waitForDelay(1000).get();
        state.update();
        setHealth((int)state.getHealth());
        setDanger(state.getState().danger_level);
        setSeenEnemy(state.enemyFlag);
        setSeenFood(state.foodFlag);
        setDeathCount(state.getDeaths());
        setKillCount(state.getKills());

    }



    @Goal(deliberation=@Deliberation(inhibits={ExploreGoal.class}),excludemode = ExcludeMode.Never, orsuccess=false)
    public class AttackGoal {
        @GoalContextCondition(beliefs={"danger","health","HThresh","DThresh","seenEnemy"})
        public boolean checkContext()
        {
            return danger<2 && health>5 && seenEnemy;
        }
    }

    @Plan(trigger = @Trigger(goals = AttackGoal.class))
    protected void attack() {

        System.out.println("Triggered Attack");
        System.out.println("Attack Plan");
        System.out.println("Enemy Visible:" + getSeenEnemy());
        System.out.println("Food Visible:" + getSeenFood());
        System.out.println("Health:" + getHealth());
        System.out.println("Theshold:" + getHThresh());
        System.out.println("Kills:" + getKillCount());
        while(getHealth()>1 && seenEnemy) {
            state.sendCommand(Action.ATTACK.select());
            state.update();
            setHealth((int)state.getHealth());
            setDanger(state.getState().danger_level);
            setSeenEnemy(state.enemyFlag);
            setSeenFood(state.foodFlag);
            setDeathCount(state.getDeaths());
            setKillCount(state.getKills());
            execFeature.waitForDelay(100).get();
        }

    }


    //@Plan(trigger = @Trigger(goals = SurvivalGoal.class))
    @Plan(trigger=@Trigger(factchangeds="health"))
    protected void healthCheck()
    {
       if(getHealth()<4 && getDanger()>2){
           while(getHealth()>1 && seenEnemy) {
               state.sendCommand(Action.ATTACK.select());
               state.update();
               setHealth((int)state.getHealth());
               setDanger(state.getState().danger_level);
               setSeenEnemy(state.enemyFlag);
               setSeenFood(state.foodFlag);
               setDeathCount(state.getDeaths());
               setKillCount(state.getKills());
               execFeature.waitForDelay(100).get();
           }

           if(getDanger()>0) {

               state.sendCommand(Action.FLEE.select());
           }
       }

    }

//    @Plan(trigger=@Trigger(factchangeds="DeathCount"))
//    protected void deathUpdate()
//    {
//        System.out.println("Triggered Died");
//        setHThresh(getHThresh()+1);
//        setDThresh(getDThresh()+1);
//    }
//
//
//    @Plan(trigger=@Trigger(factchangeds="SuccessCount"))
//    protected void SuccessUpdate()
//    {
//        System.out.println("Triggered Sucess");
//        setHThresh(getHThresh()-1);
//        setDThresh(getDThresh()-1);
//    }
//
//    @Plan(trigger=@Trigger(factchangeds="KillCount"))
//    protected void KillUpdate()
//    {
//        System.out.println("Triggered Kill check");
//        if(KillCount%2==0) {
//            System.out.println("Triggered Kill update");
//            setHThresh(getHThresh() - 1);
//            setDThresh(getDThresh() - 1);
//        }
//    }

    /**
     * The agent body.
     */

    @AgentBody
    public void body() {
        //inittially start exploring
        state.sendCommand(Action.EXPLORE.select());
        bdiFeature.dispatchTopLevelGoal(new PercieveGoal());
        bdiFeature.dispatchTopLevelGoal(new SurvivalGoal());
        bdiFeature.dispatchTopLevelGoal(new ExploreGoal());
        bdiFeature.dispatchTopLevelGoal(new AttackGoal());

    }

}
