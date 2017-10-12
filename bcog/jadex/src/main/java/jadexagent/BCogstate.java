package jadexagent;

import Agents.Entity;
import Agents.Humanoid;
//import CustomNetworkInterface.CustomHumanoid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import Agents.Humanoid;
import CustomNetworkInterface.CustomHumanoid;
import Network_Interface.NetworkInterface;
import Network_Interface.TCPMessage;
import Network_Interface.TCPMessageScanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import java.util.concurrent.CountDownLatch;

import model.State;
import java.util.Optional;

/**
 * Created by ivan1931 on 2017/08/08.
 */

public class BCogstate {


    private int counter =0; //tcp message counter




    ObjectMapper mapper = new ObjectMapper();

    private Optional<double[]> lastWolfPosition = Optional.empty();
    private Optional<double[]> lastFoodPosition = Optional.empty();
    private double[] position = new double[3];
    private int entityId;
    private double stamina;
    private double health;
    private State state;
    private int deaths;
    private int kills;
    private  NetworkInterface connection;
    private  BDIHumanoid humanoid;
    public boolean foodFlag;
    public boolean enemyFlag;


    // JSON construction for BDI-POMDP message passing
    @JsonIgnore
    public Optional<double[]> getLastWolfPosition() {
        return lastWolfPosition;
    }

    @JsonProperty("lastWolfPosition")
    private double[] lastWolfPosition() {
        return lastWolfPosition.orElse(null);
    }

    public void setLastWolfPosition(Optional<double[]> lastWolfPosition) {
        this.lastWolfPosition = lastWolfPosition;
    }

    @JsonIgnore
    public Optional<double[]> getLastFoodPosition() {
        return lastFoodPosition;
    }

    @JsonProperty("lastFoodPosition")
    private double[] nullableLastFoodPosition() {
        return lastFoodPosition.orElse(null);
    }

    public void setLastFoodPosition(Optional<double[]> lastFoodPosition) {
        this.lastFoodPosition = lastFoodPosition;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public double getStamina() {
        return stamina;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state=state;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public double getHealth() {
        return health;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setHealth(double health) {
        this.health = health;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public int getKills() {
        return kills;
    }

    public BCogstate() {

        //create new instance of humanoid and start TCP connection
        humanoid = new BDIHumanoid();

      try {
          connection = new NetworkInterface("localhost", 1302, humanoid, new TCPMessageScanner(humanoid));

      }catch (Exception e){
          System.out.println(e.getStackTrace());

      }
        //initialize humanoid world perceptions
        for (Entity entity: humanoid.seen_entities) {
            if (entity.name.contains("Wolf")) {
                enemyFlag=true;
                this.setLastWolfPosition(Optional.of(entity.position.array()));
            } else if (entity.name.contains("Berry")) {
                this.setLastFoodPosition(Optional.of(entity.position.array()));
                foodFlag=true;
            }
        }
        this.position = humanoid.position.array();
        this.stamina = humanoid.stamina;
        this.health = humanoid.health;
        this.state=humanoid.GetCurrentState();
    }

//sending TCP commands
    public void sendCommand(int value) throws InterruptedException{


        TCPMessage message = new TCPMessage(counter);
        message.AddData(value+"");
        this.connection.SendMessage(message);
        counter++;

        //if the action sent is not EXPLORE we ewait for an action complete flag to ensure Synchronicity
        if(value!=0) {
            humanoid.setActionComplete(false);
            long start_time = System.currentTimeMillis();
            while (!humanoid.getActionComplete()) {
                long new_time = System.currentTimeMillis();

                //debug messages
                if (new_time - start_time >= 100)
                {
                //wait for action
                System.out.println("Waiting for complete");
                    start_time = new_time;
                }

            }

        }

    }

    //Update sets the humanoid perceptiosn each time its called
    public void update(){

        enemyFlag=false;
        foodFlag=false;
        //initial check for if simulation has ended
        if(!connection.is_running){

            System.exit(0);
        }
        //then update humanoid perceptions
                    for (Entity entity: humanoid.GetCurrentVisibleEntities(100)) {


                        if (entity.name.contains("Wolf")) {
                            enemyFlag=true;
                            this.setLastWolfPosition(Optional.of(entity.position.array()));
                        }  if (entity.name.contains("Berry")) {
                            foodFlag=true;
                            this.setLastFoodPosition(Optional.of(entity.position.array()));
                        }
                    }
                    this.position = humanoid.position.array();
                    this.stamina = humanoid.stamina;
                    this.health = humanoid.health;
                    this.state=humanoid.GetCurrentState();
                    this.deaths=humanoid.getDeaths();
                    this.kills=humanoid.getKills();



                }




    }


