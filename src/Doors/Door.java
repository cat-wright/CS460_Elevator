package Doors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Door {
    private DoorType doorType;
    private DoorState doorState;

    public Door(DoorType doorType) {
        this.doorType = doorType;
        this.doorState = DoorState.OPENED;
    }

    public void setDoorState(DoorState state){
        doorState = state;
    }


    public DoorState getDoorState() {
        return doorState;
    }

    public void closeDoors(Door door){

        door.doorState = DoorState.CLOSING;
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                2000,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Door is " + doorState.toString());
                        door.doorState = DoorState.CLOSED;

                    }
                });
        swingTimer.setRepeats(false);
        swingTimer.start();
    }

    public void openDoors(Door door){
        door.doorState = DoorState.OPENING;
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                2000,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Door is " + doorState.toString());
                        door.doorState = DoorState.OPENED;

                    }
                });
        swingTimer.setRepeats(false);
        swingTimer.start();
    }

    public boolean shouldDoorClosed(Door door){
        if(door.doorState.equals(DoorState.OPENED))
        {
            System.out.println("Doors can be closed");
            return true;
        }
        return false;
    }

    public boolean shouldDoorOpen(Door door){
        if(door.doorState.equals(DoorState.CLOSED)){
            System.out.println("Doors can be opened");
            return true;
        }
        return false;
    }
}