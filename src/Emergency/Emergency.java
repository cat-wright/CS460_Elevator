package Emergency;

public class Emergency
{
    /**
     *  A state of emergency has a 0.01% chance of occurring. Create an
     *  instance of the EmergencyGenerator and check if there is an emergency.
     *
     * @return true if there is an emergency
     */
    public boolean isEmergency()
    {
        EmergencyGenerator eGen = new EmergencyGenerator();

        return eGen.isEmergency();
    }
}
