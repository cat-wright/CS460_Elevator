package Floors;

import Request.*;

import java.util.List;

public class FloorRequests
{
    private int numberOfFloors;
    private BuildingFloors buildingFloors;
    private FloorRequestGenerator requestGenerator;

    public FloorRequests(int numberOfFloors)
    {
        this.numberOfFloors = numberOfFloors;
        this.buildingFloors = new BuildingFloors(numberOfFloors);
        this.requestGenerator = new FloorRequestGenerator(numberOfFloors);
    }

    /**
     * Generates a random lobby button being pressed on a random floor. Returns
     * a Request object. The direction in the request object refers to which
     * direction the passenger wants to go.
     *
     * @return a Request object
     */
    public Request getFloorRequest()
    {
        Request request;

        if ((request = requestGenerator.getFloorRequest()) != null)
        {
            int floor = request.getDestination();
            Directions dir = request.getDirection();

            // Update the button press to true (turn light on)
            setLobbyButton(floor, dir, true);

            return request; // button pressed
        }

        return null; // no button pressed
    }

    /**
     * Calls implementing method in BuildingFloors.
     *
     * @param floor floor to be updated
     * @param dir dir up/down light to be updated
     * @param button turn light on/off (true/false)
     */
    public void setLobbyButton(int floor, Directions dir, boolean button)
    {
        buildingFloors.setLobbyButton(floor, dir, button);
    }

    /**
     * Sets the arrival lights on the designated floor (on/off).
     *
     * @param floor floor to be updated
     * @param dir dir up/down light to be updated
     * @param light turn light on/off (true/false)
     */
    public void setArrivalLight(int floor, Directions dir, boolean light)
    {
        buildingFloors.setArrivalLight(floor, dir, light);
    }

    /**
     * The list of all floor objects is used by the GUI to update the state
     * of all floor lights.
     *
     * @return list of all floor objects
     */
    public List<Floor> getAllFloors()
    {
        return buildingFloors.getAllFloors();
    }
}
