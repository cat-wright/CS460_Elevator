package Cabin;
import java.util.Random;

import Request.*;

public class CabinRequestGenerator
{
    private int numberOfFloors;

    CabinRequestGenerator(int numberOfFloors)
    {
        this.numberOfFloors = numberOfFloors;
    }

    /**
     * Simulates a random button press in the cabin. There is a 5% chance
     * of successfully pressing a button. The rest of the time a null
     * value is returned so the queue isn't overwhelmed with requests.
     *
     * @return a Request object or null
     */
    public Request getRandomFloor()
    {
        final int MAX = numberOfFloors;
        final int MIN = 1;
        final double PROBABILITY = 0.05; // 5% chance of pressing button

        // get random number between 1 and 10
        Random r = new Random();
        Integer randFloor = r.nextInt(MAX - MIN + 1) + MIN;

        if (r.nextDouble() < PROBABILITY)
        {
            return new Request(randFloor, Type.CABIN);
        }

        return null; // no button pressed
    }
}
