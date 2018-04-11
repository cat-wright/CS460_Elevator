import ControlPanel.ControlPanel;
import Cabin.Cabin;
import Request.Request;
import Request.StateController;

/**
 * Created by Dominic on 4/4/2018.
 */
public class BuildingControl
{
  final static ControlPanel cP = new ControlPanel(10,1);
  Cabin cabin = new Cabin(1);
  StateController sC = new StateController(cabin);
  private Request requestedFloor;
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
  
  private void checkRequests()
  {
    requestedFloor = cabin.cabinRequest();
  
    if(requestedFloor != null) //&& requestedFloor.getDestination() != currentFloor)
    {
      sC.addToQue(requestedFloor);
    }
  }
  
  private void sendToFloor()
  {
    Request currentRequest = sC.getRequest();
    currentFloor = cabin.getCabinLocation();
    cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());
    while(isCabinMoving()) {}
    cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());
    cabin.moveCabin(currentRequest.getDestination());
    cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());
    currentFloor = cabin.getCabinLocation();
      //cP.setCurrentFloor(currentFloor);
    
  }
  public void updateElevator()
  {
    
  }
  public static void main(final String[] args)
  {
    final BuildingControl bP = new BuildingControl();
    cP.start();
    while(true)
    {
      bP.checkRequests();
      bP.sendToFloor();
      
    }
  }
}
