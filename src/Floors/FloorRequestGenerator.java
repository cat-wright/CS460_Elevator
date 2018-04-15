package Floors;

import Request.*;

import java.util.Random;

public class FloorRequestGenerator
{
    private int numberOfFloors;

    FloorRequestGenerator(int numberOfFloors)
    {
        this.numberOfFloors = numberOfFloors;
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
        final int MAX = 10;
        final int MIN = 1;
        final double PROBABILITY = 0.05; // 5% chance of pressing button

        // get random number between 1 and 10
        Random r = new Random();
        Integer randFloor = r.nextInt(MAX - MIN + 1) + MIN;

        if (r.nextDouble() < PROBABILITY)
        {
            Request request = new Request(randFloor, Type.FLOOR);
            Directions dir = getRandomDirection(randFloor);

            request.setDirection(dir); // set direction in Request object

            return request;
        }

        return null; // no button pressed
    }

    /**
     * Generates a random direction based on the floor that is sending the
     * request.
     *
     * Note: This direction refers the the direction the person in the lobby
     * wants to go.
     *
     * @param floor floor that the button is pressed on
     * @return the direction of the lobby button pressed
     */
    private Directions getRandomDirection(int floor)
    {
        Random r = new Random();

        // if first floor, you can only go up
        if (floor == 1) return Directions.UP;

        // if top floor, you can only go down
        if (floor == numberOfFloors) return Directions.DOWN;

        // if not top floor or bottom floor then randomly generate direction
        if ((r.nextInt() % 2) == 0) return Directions.UP;
        else return Directions.DOWN;
    }
}
