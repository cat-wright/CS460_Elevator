import ControlPanel.ControlPanel;
import Cabin.Cabin;

/**
 * Created by Dominic on 4/4/2018.
 */
public class BuildingControl
{
  final static ControlPanel cP = new ControlPanel();
  Cabin cabin = new Cabin(1);
  private Integer requestedFloor;
  private Integer currentFloor;

  private boolean isCabinMoving()
  {
    if(cabin.isCabinMoving())
    {
      return true;
    }
    else return false;
  }

  private void setCurrentFloor(Integer floor) { this.currentFloor = floor; }

  private void sendToFloor()
  {
    requestedFloor = cP.getRequestedFloor().getDestination();
    if(requestedFloor != null && requestedFloor != currentFloor)
    {
      while(isCabinMoving()) {}
      
      cabin.moveCabin(requestedFloor);
      currentFloor = cabin.getCabinLocation();
      cP.setCurrentFloor(currentFloor);
    }
  }
  
  public static void main(final String[] args)
  {
    final BuildingControl bP = new BuildingControl();
    cP.start();
    while(true)
    {
      bP.sendToFloor();
      
    }
  }
}
