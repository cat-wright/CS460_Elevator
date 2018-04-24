package Cabin;

import Request.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 4/4/2018.
 */
public class Cabin
{

    private Integer cabinLocation;
    private Directions direction;
    private boolean isMoving;
    private int cabinNumber;
    private Motion motion;
    private CabinRequests cabinRequests;
    private CabinButtons buttons;

    public Cabin(Integer cabinLocation, int cabin)
    {
        this.cabinLocation = cabinLocation;
        this.cabinNumber = cabin;
        motion = new Motion();
        cabinRequests = new CabinRequests(10);
        buttons = new CabinButtons(10);

        direction = Directions.UP;
        this.isMoving = false;
    }

    public int getCabinNumer ()
    {
        return cabinNumber;
    }

    /**
     * Move the cabin to the given floor.
     *
     * @param floorToMoveTo the floor the elevator needs to go to
     */
    public void moveCabin(int floorToMoveTo)
    {
        if (cabinLocation == floorToMoveTo)
        {
            System.out.println("You are already on this floor.");
            isMoving = false;
            printIsMoving();
            return;
        }
        else
        {
            System.out.println("Moving to floor " + floorToMoveTo);
            isMoving = true;
            printIsMoving();

            if (cabinLocation < floorToMoveTo)
            {
                direction = Directions.UP;
            }
            else direction = Directions.DOWN;

            motion.moveCabin(floorToMoveTo);
        }

        System.out.println("At Destination");
        isMoving = false;
        printIsMoving();
    }

    /**
     * Get random request.
     *
     * @return a Request object
     */
    public Request cabinRequest()
    {
        Request request;

        if ((request = cabinRequests.getCabinRequest(cabinLocation)) != null)
        {
            // toggle on the button that was "pressed"
            setCabinButton(request.getDestination(), true);
        }

        return request;
    }

    /**
     * Toggle state of button light to true/false (on/off)
     *
     * @param floor the button number to be toggled
     * @param state the state of the light true/false (on/off)
     */
    public void setCabinButton(int floor, boolean state)
    {
        buttons.setButton(floor, state);
    }

    /**
     * Used by GUI to update all button lights. Only called once to pass
     * instance of the list to the GUI.
     *
     * @return list off all buttons
     */
    public ArrayList<Boolean> getAllButtons() { return buttons.getAllButtons(); }

    public Integer getCabinLocation()
    {
        cabinLocation = motion.getCabinLocation();

        return cabinLocation;
    }

    public boolean isCabinMoving()
    {
        return isMoving;
    }

    public Directions getDirection() {return direction;}

    public void setCabinLocation()
    {
        this.cabinLocation = cabinLocation;
    }

    public void setMoving(boolean moving)
    {
        isMoving = moving;
    }

    public void changeDirection(Directions d) {direction = d;}

    public void currentLocation()
    {
        System.out.println("Current Location Is Floor " + cabinLocation);
    }

    public void printIsMoving()
    {
        System.out.println("Status of Cabin = " + isMoving);
    }
}
