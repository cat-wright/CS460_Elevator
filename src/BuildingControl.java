import ControlPanel.ControlPanel;
import Cabin.Cabin;
import Doors.DoorController;
import Doors.DoorState;
import Floors.FloorRequests;
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
  Cabin cabin ;
  Cabin e1 = new Cabin(1, 1);
  Cabin e2 = new Cabin(1, 2);
  Cabin e3 = new Cabin(1, 3);
  Cabin e4 = new Cabin(1, 4);
  StateController sC = new StateController(e1, e2, e3, e4);
  DoorController dC = new DoorController();
  FloorRequests fR = new FloorRequests(10);
  private Request requestedFloor;
  private Integer currentFloor;

  BuildingControl(Integer currentFloor)
  {
      this.currentFloor = currentFloor;
  }

  private boolean isCabinMoving(Cabin e)
  {
    if(e.isCabinMoving())
    {
      return true;
    }
    else return false;
  }

  private void setCurrentFloor(Integer floor) { this.currentFloor = floor; }
  
  private void checkCabbinRequests(Cabin e) {

      requestedFloor = e.cabinRequest();

      if (requestedFloor != null) //&& requestedFloor.getDestination() != currentFloor)
      {
          sC.addToQue(requestedFloor, e.getCabinNumer());
      }

      requestedFloor = cP.getRequest();
      if (requestedFloor != null) //&& requestedFloor.getDestination() != currentFloor)
      {
          sC.addToQue(requestedFloor, sC.getElevator(requestedFloor));
      }
  }
  void getFloorRequests()
  {
      requestedFloor = fR.getFloorRequest();
      if(requestedFloor != null) //&& requestedFloor.getDestination() != currentFloor)
      {
          sC.addToQue(requestedFloor,sC.getElevator(requestedFloor));
      }
  }
  
  private void sendToFloor(Cabin e)
  {
      while (isCabinMoving(e)) { }
      if(dC.checkACabinDoor(e.getCabinNumer()) != DoorState.CLOSED){dC.closeDoorAtCabin(e.getCabinNumer(), true);}
      if(dC.checkADoorES((e.getCabinLocation()-1), e.getCabinNumer()) != DoorState.CLOSED)
      {
          dC.closeDoorAtES((e.getCabinLocation()-1),e.getCabinNumer(), true);
      }
      while(dC.checkACabinDoor(e.getCabinNumer()) != DoorState.CLOSED && dC.checkADoorES((e.getCabinLocation()-1), e.getCabinNumer()) != DoorState.CLOSED)
      {

      }
      Request currentRequest = sC.getRequest(e.getCabinNumer());

    if(currentRequest != null) {


        e.moveCabin(currentRequest.getDestination());
        while (isCabinMoving(e)) {
            cP.buildElevatorSpecs(isCabinMoving(e), currentRequest, currentFloor, currentRequest.getDirection());
        }
        currentFloor = e.getCabinLocation();
        cP.buildElevatorSpecs(isCabinMoving(e), currentRequest, currentFloor, currentRequest.getDirection());

    }

  }



    private static void testSwingTimer(){
        BuildingControl bP = new BuildingControl(1);
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                10,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bP.checkCabbinRequests(bP.e1);
                        bP.sendToFloor(bP.e1);
                    }
                });
        swingTimer.setInitialDelay(5000);
        swingTimer.start();
    }
  public static void main(final String[] args)
  {
      //BuildingControl bP = new BuildingControl(1);
      cP.start();
      testSwingTimer();

  }

}
