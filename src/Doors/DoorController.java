package Doors;

import java.util.ArrayList;
import java.util.List;

public class DoorController {
    private ArrayList<DoorMotor> elevatorShaft1 = new ArrayList<>();
    private ArrayList<DoorMotor> elevatorShaft2 = new ArrayList<>();
    private ArrayList<DoorMotor> elevatorShaft3 = new ArrayList<>();
    private ArrayList<DoorMotor> elevatorShaft4 = new ArrayList<>();
    private ArrayList<DoorMotor> cabinDoors = new ArrayList<>();

    private ArrayList<Door> doorList1 = new ArrayList<>();
    private ArrayList<Door> doorList2 = new ArrayList<>();
    private ArrayList<Door> doorList3 = new ArrayList<>();
    private ArrayList<Door> doorList4 = new ArrayList<>();
    private ArrayList<Door> cabinDoorList = new ArrayList<>();

    public DoorController (){
        genDoors(10, DoorType.FLOOR, elevatorShaft1);
        genDoors(10, DoorType.FLOOR, elevatorShaft2);
        genDoors(10, DoorType.FLOOR, elevatorShaft3);
        genDoors(10, DoorType.FLOOR, elevatorShaft4);
        genDoors(4, DoorType.CABIN, cabinDoors);
        startDoors(elevatorShaft1);
        startDoors(elevatorShaft2);
        startDoors(elevatorShaft3);
        startDoors(elevatorShaft4);
        startDoors(cabinDoors);
        setDoorList(elevatorShaft1, doorList1);
        setDoorList(elevatorShaft2, doorList2);
        setDoorList(elevatorShaft3, doorList3);
        setDoorList(elevatorShaft4, doorList4);
        setCabinDoorList(cabinDoors);
    }

    public void startDoors(List<DoorMotor> doorMotors){
        for(DoorMotor i: doorMotors){
            i.setDoorOpen(true);
            i.setDoorClose(true);
            i.run();
        }
    }

    private void genDoors (int numDoors, DoorType type, List<DoorMotor> listDoors){
        for(int i = 0; i < numDoors; i++){
            listDoors.add(new DoorMotor(type));
        }
    }

    private void setDoorList(ArrayList<DoorMotor> elevatorShaft, ArrayList<Door> doorList)
    {
        for(int i = 0; i< 10; i++)
        {
            doorList.add(elevatorShaft.get(i).getDoor());
        }
    }

    public void setCabinDoorList(ArrayList<DoorMotor> cabins)
    {
        for(int i = 0; i < 4; i++)
        {
            cabinDoorList.add(cabins.get(i).getDoor());
        }
    }

    public ArrayList<Door> getCabinDoorList()
    {
        return cabinDoorList;
    }
    public DoorState checkADoorES(int floorDoor, int es){
        if(floorDoor < 0 || floorDoor > 9){
            return null;
        }
        if(es == 1) {return elevatorShaft1.get(floorDoor).getDoorState();}
        if(es == 2) {return elevatorShaft2.get(floorDoor).getDoorState();}
        if(es == 3) {return elevatorShaft3.get(floorDoor).getDoorState();}
        if(es == 4) {return elevatorShaft4.get(floorDoor).getDoorState();}
        return null;
    }

    public DoorState checkACabinDoor(int cabinDoor){
        if(cabinDoor < 0 || cabinDoor > 3){
            return null;
        }
        return cabinDoors.get(cabinDoor).getDoorState();
    }

    public void openDoorAtES(int floorDoor, int es, boolean shouldOpen){
        if(es == 1) {
            elevatorShaft1.get(floorDoor-1).setDoorOpen(shouldOpen);
            elevatorShaft1.get(floorDoor-1).run();
        }
        if(es == 2) {
            elevatorShaft2.get(floorDoor-1).setDoorOpen(shouldOpen);
            elevatorShaft2.get(floorDoor-1).run();
        }
        if(es == 3) {
            elevatorShaft3.get(floorDoor-1).setDoorOpen(shouldOpen);
            elevatorShaft3.get(floorDoor-1).run();
        }
        if(es == 4) {
            elevatorShaft4.get(floorDoor-1).setDoorOpen(shouldOpen);
            elevatorShaft4.get(floorDoor-1).run();
        }
    }


    public void openDoorAtCabin(int floorDoor, boolean shouldOpen){
        cabinDoors.get(floorDoor-1).setDoorOpen(shouldOpen);
        cabinDoors.get(floorDoor-1).run();
    }

    public void closeDoorAtES(int floorDoor, int es, boolean shouldClose){
        if(es == 1) {
            elevatorShaft1.get(floorDoor).setDoorClose(shouldClose);
            elevatorShaft1.get(floorDoor).run();
        }
        if(es == 2) {
            elevatorShaft2.get(floorDoor).setDoorClose(shouldClose);
            elevatorShaft2.get(floorDoor).run();
        }
        if(es == 3) {
            elevatorShaft3.get(floorDoor).setDoorClose(shouldClose);
            elevatorShaft3.get(floorDoor).run();
        }
        if(es == 4) {
            elevatorShaft4.get(floorDoor).setDoorClose(shouldClose);
            elevatorShaft1.get(floorDoor).run();
        }
    }

    public void closeDoorAtCabin(int floorDoor, boolean shouldClose){
        cabinDoors.get(floorDoor-1).setDoorClose(shouldClose);
        cabinDoors.get(floorDoor-1).run();
    }

    public ArrayList<Door> getDoorList(int elevator)
    {
        //edited doorList numbers - cat
        if(elevator ==1) return doorList1;
        if(elevator ==2) return doorList2;
        if(elevator ==3) return doorList3;
        if(elevator ==4) return doorList4;
        return null;
    }
}
