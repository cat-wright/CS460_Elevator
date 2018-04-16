package Doors;

public class DoorController {
    private DoorMotor doorMotor;

    public void operateDoor(DoorType type){
        doorMotor = new DoorMotor(type);
        doorMotor.run();
    }
}
