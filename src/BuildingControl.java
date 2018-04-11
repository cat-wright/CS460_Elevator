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

  BuildingControl(Integer currentFloor)
  {
      this.currentFloor = currentFloor;
  }

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

    if(currentRequest != null) {

        cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());
        while (isCabinMoving()) {
        }
        cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());
        cabin.moveCabin(currentRequest.getDestination());
        currentFloor = cabin.getCabinLocation();
        cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());

    }
      //cP.setCurrentFloor(currentFloor);
    
  }

  public static void main(final String[] args)
  {
    BuildingControl bP = new BuildingControl(1);
    cP.start();
    while(true)
    {
      bP.checkRequests();
      bP.sendToFloor();
      
    }
  }
}
