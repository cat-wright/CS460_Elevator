package Emergency;

import java.util.Random;

public class EmergencyGenerator
{
    /**
     *  A state of emergency has a 0.01% chance of occurring.
     *
     * @return true if there is an emergency
     */
    public boolean isEmergency()
    {
        final double PROBABILITY = 0.0001; // 0.01% chance of pressing button

        Random r = new Random();

        return r.nextDouble() < PROBABILITY;
    }
}
