package Cabin;

/**
 * Created by Vincent on 4/4/2018.
 */
public class Cabin {

  private Integer cabinLocation;
  private boolean isMoving;
  private int maxFloor = 16;

  public Cabin (int cabinLocation){
    this.cabinLocation = cabinLocation;
    this.isMoving = false;
  }

  public void setCabinLocation() {
    this.cabinLocation = cabinLocation;
  }

  public void setMoving(boolean moving) {
    isMoving = moving;
  }

  public Integer getCabinLocation() {
    return cabinLocation;
  }

  public boolean isCabinMoving(){
    return isMoving;
  }

  public void moveCabin(int floorToMoveTo){
    if(cabinLocation == floorToMoveTo){
      System.out.println("You are already on this floor.");
      isMoving = false;
      printIsMoving();
      return;
    }
    else if(cabinLocation < floorToMoveTo) {
      System.out.println("Moving to floor " + floorToMoveTo);
      isMoving = true;
      for(int i = cabinLocation; i <= floorToMoveTo; i++){
        printIsMoving();
        cabinLocation = i;
        currentLocation();
      }
    }
    else {
      System.out.println("Moving to floor " + floorToMoveTo);
      isMoving = true;
      for (int i = cabinLocation; i >= floorToMoveTo; i--) {
        printIsMoving();
        cabinLocation = i;
        currentLocation();
      }
    }

    System.out.println("At Destination");
    isMoving = false;
    printIsMoving();
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
