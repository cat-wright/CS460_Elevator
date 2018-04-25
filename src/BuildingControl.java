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
  static ControlPanel cP = new ControlPanel(10,4);
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
//      setCabinButtons();
//      setLobbies();
//      setDoors();
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

      requestedFloor = cP.getRequest(e.getCabinNumer());
      if (requestedFloor != null) //&& requestedFloor.getDestination() != currentFloor)
      {
          sC.addToQue(requestedFloor, sC.getElevator(requestedFloor));
      }
  }
  private void checkFloorRequests()
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
            cP.buildElevatorSpecs(isCabinMoving(e), e.getCabinLocation(), currentRequest.getDirection());
        }
        currentFloor = e.getCabinLocation();
        //while(!e.isAtFloor()){}
        dC.openDoorAtES(e.getCabinLocation(),e.getCabinNumer(),true);
        dC.openDoorAtCabin(e.getCabinNumer(), true);
        cP.buildElevatorSpecs(isCabinMoving(e), e.getCabinLocation(), currentRequest.getDirection());

    }

  }

  void setCabinButtons()
  {
      cP.setCabinList(e1.getAllButtons(),1);
      cP.setCabinList(e2.getAllButtons(),2);
      cP.setCabinList(e3.getAllButtons(),3);
      cP.setCabinList(e4.getAllButtons(),4);
  }

  void setDoors()
  {
      cP.setDoorList(dC.getDoorList(1),1);
      cP.setDoorList(dC.getDoorList(2),2);
      cP.setDoorList(dC.getDoorList(3),3);
      cP.setDoorList(dC.getDoorList(4),4);
  }

  void setLobbies()
  {
      cP.setLobbyList(fR.getAllFloors());
  }

    private static void testSwingTimer(){
        BuildingControl bP = new BuildingControl(1);
        bP.setCabinButtons();
        bP.setDoors();
        bP.setLobbies();
        cP.start();
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                1,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bP.checkFloorRequests();
                        bP.checkCabbinRequests(bP.e1);
                        bP.sendToFloor(bP.e1);
                        bP.checkCabbinRequests(bP.e2);
                        bP.sendToFloor(bP.e2);
                        bP.checkCabbinRequests(bP.e3);
                        bP.sendToFloor(bP.e3);
                        bP.checkCabbinRequests(bP.e4);
                        bP.sendToFloor(bP.e4);
                    }
                });
        swingTimer.setInitialDelay(5000);
        swingTimer.start();
    }
  public static void main(final String[] args)
  {
      //BuildingControl bP = new BuildingControl(1);
      //cP.start();
      testSwingTimer();

  }

}
