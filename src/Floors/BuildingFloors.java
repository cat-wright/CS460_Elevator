package Floors;

import Request.Directions;

import java.util.ArrayList;
import java.util.List;

public class BuildingFloors
{
    private int numberOfFloors; // number of floors in the building
    private List<Floor> floors; // list of all floor objects

    /**
     * Basic constructor to set the number of floors in the building
     *
     * @param numberOfFloors the number of floors in the building
     */
    BuildingFloors(int numberOfFloors)
    {
        this.numberOfFloors = numberOfFloors;
        initButtonPairs();
    }

    /**
     * Sets the lobby button specified on the given floor. Turns the up/down
     * button light on/off.
     *
     * @param floor floor on which the designated button is
     * @param dir up/down button to be updated
     * @param button turn light on/off (true/false)
     */
    void setLobbyButton(int floor, Directions dir, boolean button)
    {
        Floor f = floors.get(floor - 1);

        if (dir == Directions.UP) f.setUpButton(button);
        else f.setDownButton(button);
    }

    /**
     * Sets the arrival lights on the designated floor (on/off).
     *
     * @param floor floor to be updated
     * @param dir dir up/down light to be updated
     * @param light turn light on/off (true/false)
     */
    void setArrivalLight(int floor, Directions dir, boolean light)
    {
        Floor f = floors.get(floor - 1);

        if (dir == Directions.UP) f.setUpArrivalLight(light);
        else f.setDownArrivalLight(light);
    }

    /**
     * @return list of all floor objects
     */
    List<Floor> getAllFloors()
    {
        return floors;
    }

    /**
     * Create a new floor object for each floor.
     *
     * Set the up and down buttons on each floor to false to indicate t
     * hey are off.
     *
     * Give each floor object a number from 1 to numberOfFloors to indicate
     * which floor the object represents.
     *
     * Add all the floors to the floors list.
     */
    private void initButtonPairs()
    {
        floors = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++)
        {
            floors.add(new Floor(i + 1));
        }
    }
}
