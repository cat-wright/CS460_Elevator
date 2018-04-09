import java.util.Random;

public class CabinButton
{
    private final int MAX = 10;
    private final int MIN = 1;
    private final double PROBABILITY = 0.05;

    public Integer getRandomFloor()
    {
        // get random number between 1 and 10
        Random r = new Random();
        Integer randFloor = r.nextInt(MAX - MIN + 1) + MIN;

        if (r.nextDouble() < PROBABILITY) return randFloor;

        return -1; // no button pressed
    }
}
