package plainBDI;
import java.util.ArrayList;

import Agents.Entity;
import Agents.Humanoid;
import Agents.Predator;
import Collectables.EdibleItem;
import Utility.Vector3;
import model.State;

public class BDIHumanoid extends Humanoid
{
    public static final int EXPLORE = 0;
    public static final int REST = 1;
    public static final int SEEK_WATER = 2;

    public static final int MOVE_TO_LOCATION = 4;
    public static final int ENGAGE_ENTITY = 5;
    public static final int EAT_ITEM = 6;
    public static final int DRINK = 7;

    // Activities:
    public static final int ATTACK = 9;
    public static final int FLEE = 8;
    public static final int SEEK_FOOD = 3;

    public EdibleItem currentFood;

    public BDIHumanoid()
    {
        currentFood = null;
        health = 10;
    }

    public EdibleItem getCurrentFood()
    {
        return currentFood;
    }

    /*
     * Return a representation of the current world state. (Discretized)
     */
    public State GetCurrentState()
    {
        int health_indicator = 0;
        int danger = CalculateDanger();

        if (health >= 5)
            health_indicator = 1;

        return new State(danger, health_indicator);
    }



    //Method uses number of predators and distance to predators to return a danger value
    private int CalculateDanger()
    {
        int predator_count = 0;
        int danger = 0;
        ArrayList<Double> predator_distances = new ArrayList<Double>();

        ArrayList<Entity> entities = this.GetCurrentVisibleEntities(100);

        for(int i = 0 ; i < entities.size(); i++)
        {
            if (entities.get(i) instanceof Predator)
            {
                predator_count++;
                Vector3 predator_position = entities.get(i).position;
                predator_distances.add(Vector3.GetDistance(this.position, predator_position));
            }
        }

        if (predator_count == 0)
        {
            danger = 0;
        }
        else if (predator_count >= 1 && predator_count <= 2)
        {
            danger = 1;
        }
        else // If the predator count is high, we need to evaluate distances
        {
            int num_close = 0;
            int num_medium = 1;

            for(int i = 0 ; i < predator_distances.size(); i++)
            {
                if(predator_distances.get(i) <= 6)
                    num_close++;

                else if (predator_distances.get(i) <= 15)
                    num_medium++;
            }

            if (num_close > 2 ||
                    (num_close > 1 && num_medium > 2))
                danger = 2;

            else if (num_close > 1 && num_medium < 2)
                danger = 1;
        }

        return danger;
    }
}
