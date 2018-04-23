package Doors;

import java.util.ArrayList;
import java.util.List;

public class DoorController {
    private List<DoorMotor> elevatorShaft1 = new ArrayList<>();
    private List<DoorMotor> elevatorShaft2 = new ArrayList<>();
    private List<DoorMotor> elevatorShaft3 = new ArrayList<>();
    private List<DoorMotor> elevatorShaft4 = new ArrayList<>();
    private List<DoorMotor> cabinDoors = new ArrayList<>();

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

    }

    public void startDoors(List<DoorMotor> doorMotors){
        for(DoorMotor i: doorMotors){
            i.run();
        }
    }

    private void genDoors (int numDoors, DoorType type, List<DoorMotor> listDoors){
        for(int i = 0; i < numDoors; i++){
            listDoors.add(new DoorMotor(type));
        }
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

    public DoorState checkADoorES2(int floorDoor){
        if(floorDoor < 0 || floorDoor > 9){
            return null;
        }
        return elevatorShaft2.get(floorDoor).getDoorState();
    }

    public DoorState checkADoorES3(int floorDoor){
        if(floorDoor < 0 || floorDoor > 9){
            return null;
        }
        return elevatorShaft3.get(floorDoor).getDoorState();
    }

    public DoorState checkADoorES4(int floorDoor){
        if(floorDoor < 0 || floorDoor > 9){
            return null;
        }
        return elevatorShaft4.get(floorDoor).getDoorState();
    }

    public DoorState checkACabinDoor(int cabinDoor){
        if(cabinDoor < 0 || cabinDoor > 3){
            return null;
        }
        return cabinDoors.get(cabinDoor).getDoorState();
    }

    public void openDoorAtES1(int floorDoor, boolean shouldOpen){
        elevatorShaft1.get(floorDoor).setDoorOpen(shouldOpen);
    }

    public void openDoorAtES2(int floorDoor, boolean shouldOpen){
        elevatorShaft2.get(floorDoor).setDoorOpen(shouldOpen);
    }

    public void openDoorAtES3(int floorDoor, boolean shouldOpen){
        elevatorShaft3.get(floorDoor).setDoorOpen(shouldOpen);
    }

    public void openDoorAtES4(int floorDoor, boolean shouldOpen){
        elevatorShaft4.get(floorDoor).setDoorOpen(shouldOpen);
    }

    public void openDoorAtCabin(int floorDoor, boolean shouldOpen){
        cabinDoors.get(floorDoor).setDoorOpen(shouldOpen);
    }

    public void closeDoorAtES(int floorDoor, int es, boolean shouldClose){
        if(es == 1) {elevatorShaft1.get(floorDoor).setDoorOpen(shouldClose);}
        if(es == 2) {elevatorShaft2.get(floorDoor).setDoorOpen(shouldClose);}
        if(es == 3) {elevatorShaft3.get(floorDoor).setDoorOpen(shouldClose);}
        if(es == 4) {elevatorShaft4.get(floorDoor).setDoorOpen(shouldClose);}
    }

    public void closeDoorAtES2(int floorDoor, boolean shouldClose){
        elevatorShaft2.get(floorDoor).setDoorOpen(shouldClose);
    }

    public void closeDoorAtES3(int floorDoor, boolean shouldClose){
        elevatorShaft3.get(floorDoor).setDoorOpen(shouldClose);
    }

    public void closeDoorAtES4(int floorDoor, boolean shouldClose){
        elevatorShaft4.get(floorDoor).setDoorOpen(shouldClose);
    }

    public void closeDoorAtCabin(int floorDoor, boolean shouldClose){
        cabinDoors.get(floorDoor).setDoorOpen(shouldClose);
    }
}
