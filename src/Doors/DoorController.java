package Doors;

public class DoorController {
    private DoorMotor floorDoorMotor;
    private DoorMotor cabinDoorMotor;

    public void operateDoor(){
        floorDoorMotor = new DoorMotor(DoorType.FLOOR);
        cabinDoorMotor = new DoorMotor(DoorType.CABIN);
        cabinDoorMotor.run();
        floorDoorMotor.run();
    }

    public DoorState getFloorState(){
        return floorDoorMotor.getDoorState();
    }
    public DoorState getCabinState(){
        return floorDoorMotor.getDoorState();
    }
    public void openFloorDoor(boolean canDoorOpen){

    }
    public void closeFloorDoor(boolean canDooClose){

    }
    public void openCabinDoor(boolean canDoorOpen){

    }
    public void closeCabinDoor(boolean canDooClose){

    }

}
