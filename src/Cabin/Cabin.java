package Cabin;
import Request.*;

/**
 * Created by Vincent on 4/4/2018.
 */
public class Cabin {

  private Integer cabinLocation;
  private boolean isMoving;
  private int maxFloor = 16;
  private Motion motion;
  private Directions direction;

  public Cabin (int cabinLocation){
    this.cabinLocation = cabinLocation;
    this.isMoving = false;
    motion = new Motion();
    direction = Directions.UP;
  }

  public void setCabinLocation() {
    this.cabinLocation = cabinLocation;
  }

  public void setMoving(boolean moving) {
    isMoving = moving;
  }

  public Integer getCabinLocation() {
      cabinLocation = motion.getCabinLocation();
      return cabinLocation;
  }

  public boolean isCabinMoving(){
    return isMoving;
  }
  
  public Directions getDirection() {return direction;}
  
  public void changeDirection(Directions d) {direction = d;}

  public void moveCabin(int floorToMoveTo){
    if(cabinLocation == floorToMoveTo){
      System.out.println("You are already on this floor.");
      isMoving = false;
      printIsMoving();
      return;
    } else {
      System.out.println("Moving to floor " + floorToMoveTo);
      isMoving = true;
      if(cabinLocation < floorToMoveTo)
      {
        direction = Directions.UP;
      }
      else
      {
        direction = Directions.DOWN;
      }
      motion.moveCabin(floorToMoveTo);
    }

    System.out.println("At Destination");
    isMoving = false;
    printIsMoving();
  }

  public Request cabinRequest(){
    CabinButton buttonRequest = new CabinButton();
    Request newRequest = buttonRequest.getRandomFloor();
    if(newRequest.getDestination() > cabinLocation){
      newRequest.setDirection(Directions.UP);
    }
    else if(newRequest.getDestination() < cabinLocation){
      newRequest.setDirection(Directions.DOWN);
    }
    else{
      newRequest = null;
    }
    return newRequest;
  }

  public void currentLocation(){

    System.out.println("Current Location Is Floor " + cabinLocation);
  }

  public void printIsMoving(){
    System.out.println("Status of Cabin = " + isMoving );
  }

  /*public static void main(String[] args){
    Cabin cabin = new Cabin(1);

    cabin.moveCabin(5);
    cabin.moveCabin(3);
    cabin.moveCabin(3);

  }*/


}
