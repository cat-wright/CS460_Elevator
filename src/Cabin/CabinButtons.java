package Cabin;

import java.util.ArrayList;
import java.util.List;

public class CabinButtons
{
    private int numberOfFloors;
    private List<Boolean> buttons;

    CabinButtons(int numberOfFloors)
    {
        this.numberOfFloors = numberOfFloors;
        initButtons();
    }

    /**
     * Toggle state of button light to true/false (on/off)
     *
     * @param floor the button number to be toggled
     * @param state the state of the light true/false (on/off)
     */
    public void setButton(int floor, boolean state)
    {
        buttons.set(floor - 1, state);
    }

    /**
     * @return list off all buttons
     */
    public List<Boolean> getAllButtons()
    {
        return buttons;
    }

    /**
     * Initialize all buttons to false (off).
     */
    private void initButtons()
    {
        buttons = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++)
        {
            buttons.add(false);
        }
    }
}
