package Doors;

public class DoorMotor extends Thread {
    private Door door;
    private boolean canDoorOpen;
    private boolean canDoorClose;

    public DoorMotor (DoorType type){
        this.door = new Door(type);
        this.canDoorOpen = false;
        this.canDoorClose = false;
    }

    @Override
    public void run() {
        if(door.shouldDoorClosed(door) && canDoorClose){
            door.closeDoors(door);
            sleep(2000);
        }
        else if(door.shouldDoorOpen(door) && canDoorOpen){
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

    public DoorState getDoorState(){
        return door.getDoorState();
    }

    public void setDoorOpen(boolean canDoorOpen){
        this.canDoorOpen = canDoorOpen;
    }

    public void setDoorClose(boolean canDoorClose){
        this.canDoorClose = canDoorClose;
    }
}
