import ControlPanel.ControlPanel;
import Cabin.Cabin;
import Request.Request;
import Request.StateController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    requestedFloor = cP.getRequest();
    if(requestedFloor != null) //&& requestedFloor.getDestination() != currentFloor)
      {
          sC.addToQue(requestedFloor);
      }
  }
  
  private void sendToFloor()
  {
      while (isCabinMoving()) { }
    Request currentRequest = sC.getRequest();

    if(currentRequest != null) {

        //cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());

        //cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());
        cabin.moveCabin(currentRequest.getDestination());
        while (isCabinMoving()) { }
        currentFloor = cabin.getCabinLocation();
        cP.buildElevatorSpecs(isCabinMoving(), currentRequest, currentFloor, currentRequest.getDirection());

    }
      //cP.setCurrentFloor(currentFloor);
    
  }

    private static void testSwingTimer(){
        BuildingControl bP = new BuildingControl(1);
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                10,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bP.checkRequests();
                        bP.sendToFloor();
                    }
                });
        swingTimer.setInitialDelay(5000 );
        swingTimer.start();
    }
  public static void main(final String[] args)
  {
      BuildingControl bP = new BuildingControl(1);
      cP.start();
      testSwingTimer();


    /*while(true)
    {
      bP.checkRequests();
      bP.sendToFloor();
      
    }*/
  }

}
