package Cabin;

import Request.Request;

public class CabinRequests
{
    private CabinRequestGenerator requestGenerator;

    CabinRequests(int numberOfFloors)
    {
        requestGenerator = new CabinRequestGenerator(numberOfFloors);
    }

    /**
     * Get a random request from the generator and update the direction of
     * the Request object based on the current cabin location.
     *
     * @param cabinLocation current location of cabin
     * @return a Request object
     */
    public Request getCabinRequest(Integer cabinLocation)
    {
        Request request = requestGenerator.getRandomFloor();

        if (request != null && request.getDestination() > cabinLocation)
        {
            request.setDirection(Request.Directions.UP);
        }
        else if (request != null && request.getDestination() < cabinLocation)
        {
            request.setDirection(Request.Directions.DOWN);
        }
        else request = null;

        return request;
    }
}
