package Request;
import Cabin.Cabin;
import java.util.*;
/**
 * Created by Dominic on 4/11/2018.
 */
public class StateController
{
  Cabin cabin;
  
  //Comparator for requests anonymous class implementation
  public Comparator<Request> requestComparator = new Comparator<Request>(){
    
    @Override
    public int compare(Request r1, Request r2) {
      
      if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.UP)
      {
        if(r1.getDestination() < r2.getDestination()) {return 1; }
        else if(r1.getDestination() > r2.getDestination()) {return -1;}
        else return 0;
      }
  
      else if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.DOWN)
      {
        if(cabin.getCabinLocation() < r1.getDestination() || cabin.getCabinLocation() == r1.getDestination()) {return 1;}
        else if(cabin.getCabinLocation() > r1.getDestination()) {return -1;}
      }

      else if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.UP)
      {
        if(cabin.getCabinLocation() < r2.getDestination()) {return -1;}
        else if(cabin.getCabinLocation() > r2.getDestination()) {return 1;}
      }

      else if(cabin.getDirection() == Directions.UP && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.DOWN)
      {
        if(r1.getDestination() > r2.getDestination()) {return 1;}
        else if(r1.getDestination() < r2.getDestination()) {return -1;}
        else return 0;
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.DOWN)
      {
        if(r1.getDestination() > r2.getDestination()) {return 1; }
        else if(r1.getDestination() < r2.getDestination()) {return -1;}
        else return 0;
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.DOWN && r2.getDirection() == Directions.UP)
      {
        if(cabin.getCabinLocation() > r1.getDestination() || cabin.getCabinLocation() == r1.getDestination()) {return 1;}
        else if(cabin.getCabinLocation() < r1.getDestination()) {return -1;}
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.DOWN)
      {
        if(cabin.getCabinLocation() > r2.getDestination()) {return -1;}
        else if(cabin.getCabinLocation() < r2.getDestination()) {return 1;}
      }
  
      else if(cabin.getDirection() == Directions.DOWN && r1.getDirection() == Directions.UP && r2.getDirection() == Directions.UP)
      {
        if(r1.getDestination() < r2.getDestination()) {return 1;}
        else if(r1.getDestination() > r2.getDestination()) {return -1;}
        else return 0;
      }
      return 0;
    }
  };
  PriorityQueue<Request> requestQueue = new PriorityQueue<Request>(0,requestComparator);
  public StateController(Cabin cabin)
  {
    this.cabin = cabin;
  }
  
  public void addToQue (Request r1)
  {
    requestQueue.add(r1);
  }
  
  
  
  
  
}
