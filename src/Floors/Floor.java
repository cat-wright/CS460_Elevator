package Floors;

/**
 * Represents each floor in the building. Holds values for the the upButton and
 * downButton lights one each floor as well as the number of the floor the
 * object represents. Also the two up and down arrival lights are stored
 * in this class.
 */
public class Floor
{
    // up and down buttons on each floor
    private boolean upButton, downButton;

    // up and down arrival lights on each floor
    private boolean upArrivalLight, downArrivalLight;

    // number of floors in the building
    private int floor;

    Floor(int floor)
    {
        this.floor = floor;
        upButton = downButton = false;
        upArrivalLight = downArrivalLight = false;
    }

    public boolean isUpButton()
    {
        return upButton;
    }

    public void setUpButton(boolean upButton)
    {
        this.upButton = upButton;
    }

    public boolean isDownButton()
    {
        return downButton;
    }

    public void setDownButton(boolean downButton)
    {
        this.downButton = downButton;
    }

    public boolean isUpArrivalLight()
    {
        return upArrivalLight;
    }

    public void setUpArrivalLight(boolean upArrivalLight)
    {
        this.upArrivalLight = upArrivalLight;
    }

    public boolean isDownArrivalLight()
    {
        return downArrivalLight;
    }

    public void setDownArrivalLight(boolean downArrivalLight)
    {
        this.downArrivalLight = downArrivalLight;
    }

    public int getFloor()
    {
        return floor;
    }
}