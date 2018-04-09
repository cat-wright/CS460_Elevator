import java.util.Random;

public class CabinButton
{
    public Integer getRandomFloor()
    {
        final int MAX = 10;
        final int MIN = 1;
        final double PROBABILITY = 0.05; // 5% chance of pressing button

        // get random number between 1 and 10
        Random r = new Random();
        Integer randFloor = r.nextInt(MAX - MIN + 1) + MIN;

        if (r.nextDouble() < PROBABILITY) return randFloor;

        return -1; // no button pressed
    }
}
