package Doors;

public class DoorMotor extends Thread {
    private Door door;

    public DoorMotor (DoorType type){
        this.door = new Door(type);
    }

    @Override
    public void run() {
        if(door.shouldDoorClosed(door)){
            door.closeDoors(door);
            sleep(2000);
        }
        else if(door.shouldDoorOpen(door)){
            door.shouldDoorOpen(door);
            sleep(2000);
        }
        sleep(2000);
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
