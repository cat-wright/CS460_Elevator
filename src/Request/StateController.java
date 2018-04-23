package Request;
import Cabin.Cabin;
import java.util.*;
/**
 * Created by Dominic on 4/11/2018.
 */
public class StateController
{
  Cabin cabin;
  Cabin e1;
  Cabin e2;
  Cabin e3;
  Cabin e4;
  
  //Comparator for requests anonymous class implementation
  public Comparator<Request> requestComparator = new Comparator<Request>(){
    
    @Override
    public int compare(Request r1, Request r2) {
      
      if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.UP)
      {
        if(r1.getDestination() < r2.getDestination()) {return -1; }
        else if(r1.getDestination() > r2.getDestination()) {return 1;}
        else return 0;
      }
  
      else if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.DOWN)
      {
        if(cabin.getCabinLocation() < r1.getDestination() || cabin.getCabinLocation() == r1.getDestination()) {return -1;}
        else if(cabin.getCabinLocation() > r1.getDestination()) {return 1;}
      }

      else if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.UP)
      {
        if(cabin.getCabinLocation() < r2.getDestination()) {return 1;}
        else if(cabin.getCabinLocation() > r2.getDestination()) {return -1;}
      }

      else if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.DOWN)
      {
        if(r1.getDestination() > r2.getDestination()) {return -1;}
        else if(r1.getDestination() < r2.getDestination()) {return 1;}
        else return 0;
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.DOWN)
      {
        if(r1.getDestination() > r2.getDestination()) {return -1; }
        else if(r1.getDestination() < r2.getDestination()) {return 1;}
        else return 0;
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.UP)
      {
        if(cabin.getCabinLocation() > r1.getDestination() || cabin.getCabinLocation() == r1.getDestination()) {return -1;}
        else if(cabin.getCabinLocation() < r1.getDestination()) {return 1;}
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.DOWN)
      {
        if(cabin.getCabinLocation() > r2.getDestination()) {return 1;}
        else if(cabin.getCabinLocation() < r2.getDestination()) {return -1;}
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.UP)
      {
        if(r1.getDestination() < r2.getDestination()) {return -1;}
        else if(r1.getDestination() > r2.getDestination()) {return 1;}
        else return 0;
      }
      return 0;
    }
  };
  PriorityQueue<Request> requestE1 = new PriorityQueue<Request>(requestComparator);
  PriorityQueue<Request> requestE2 = new PriorityQueue<Request>(requestComparator);
  PriorityQueue<Request> requestE3 = new PriorityQueue<Request>(requestComparator);
  PriorityQueue<Request> requestE4 = new PriorityQueue<Request>(requestComparator);
  public StateController(Cabin e1, Cabin e2, Cabin e3, Cabin e4)
  {
    this.e1 = e1;
    this.e2 = e2;
    this.e3 = e3;
    this.e4 = e4;
  }
  
  public void addToQue (Request r1, int elevator)
  {
    if(elevator == 1)
    {
      cabin = e1;
      requestE1.add(r1);
    }
    if(elevator == 2)
    {
      cabin = e2;
      requestE2.add(r1);
    }
    if(elevator == 3)
    {
      cabin = e3;
      requestE3.add(r1);
    }
    if(elevator == 4)
    {
      cabin = e4;
      requestE4.add(r1);
    }
  }

  public int getElevator(Request request)
  {
    int elevator = 1;
    int distance = 9;
    int e1Distance;
    int e2Distance;
    int e3Distance;
    int e4Distance;
    if(request.getDirection() == Directions.UP)
    {
      e1Distance = request.getDestination() - e1.getCabinLocation();
      e2Distance = request.getDestination() - e2.getCabinLocation();
      e3Distance = request.getDestination() - e3.getCabinLocation();
      e4Distance = request.getDestination() - e4.getCabinLocation();
      if (e1.getDirection() == request.getDirection() && e1Distance >=0)
      {
        if(e1Distance < distance)
        {
          distance = e1Distance;
        }
      }

      if (e2.getDirection() == request.getDirection() && e2Distance >=0)
      {
        if(e2Distance < distance)
        {
          elevator = 2;
          distance = e2Distance;
        }
      }

      if (e3.getDirection() == request.getDirection() && e3Distance >=0)
      {
        if(e3Distance < distance)
        {
          elevator = 3;
          distance = e3Distance;
        }
      }

      if (e4.getDirection() == request.getDirection() && e4Distance >=0)
      {
        if(e4Distance < distance)
        {
          elevator = 4;
        }
      }
    }

    if(request.getDirection() == Directions.DOWN)
    {
      e1Distance = e1.getCabinLocation() - request.getDestination();
      e2Distance = e2.getCabinLocation() - request.getDestination();
      e3Distance = e3.getCabinLocation() - request.getDestination();
      e4Distance = e4.getCabinLocation() - request.getDestination();
      if (e1.getDirection() == request.getDirection() && e1Distance >=0)
      {
        if(e1Distance < distance)
        {
          distance = e1Distance;
        }
      }

      if (e2.getDirection() == request.getDirection() && e2Distance >=0)
      {
        if(e2Distance < distance)
        {
          elevator = 2;
          distance = e2Distance;
        }
      }

      if (e3.getDirection() == request.getDirection() && e3Distance >=0)
      {
        if(e3Distance < distance)
        {
          elevator = 3;
          distance = e3Distance;
        }
      }

      if (e4.getDirection() == request.getDirection() && e4Distance >=0)
      {
        if(e4Distance < distance)
        {
          elevator = 4;
        }
      }
    }
    return elevator;
  }

  public Request getRequest(int elevator)
  {
    if(elevator == 1 && requestE1.peek() != null) {
      return requestE1.remove();
    }

    if(elevator == 2 && requestE2.peek() != null) {
      return requestE2.remove();
    }

    if(elevator == 3 && requestE3.peek() != null) {
      return requestE3.remove();
    }

    if(elevator == 4 && requestE4.peek() != null) {
      return requestE4.remove();
    }

    return null;
  }
  
  
  
  
}
