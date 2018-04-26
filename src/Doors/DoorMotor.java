package Doors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoorMotor extends Thread {
    private Door door;
    private boolean canDoorOpen;
    private boolean canDoorClose;

    public DoorMotor (DoorType type){
        this.door = new Door(type);
        this.canDoorOpen = true;
        this.canDoorClose = true;
    }

    @Override
    public void run() {
        if(shouldDoorClosed()&& canDoorClose){
            closeDoors();
            canDoorClose = false;
        } else if(shouldDoorOpen() && canDoorOpen){
            openDoors();
            canDoorOpen = false;
        }else
        sleep(20);
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void closeDoors(){

        door.setDoorState(DoorState.CLOSING);
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                20,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        door.setDoorState(DoorState.CLOSED);

                    }
                });
        swingTimer.setRepeats(false);
        swingTimer.start();
    }

    public void openDoors(){
        door.setDoorState(DoorState.OPENING);
        javax.swing.Timer swingTimer = new javax.swing.Timer(
                20,
                new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        door.setDoorState(DoorState.OPENED);

                    }
                });
        swingTimer.setRepeats(false);
        swingTimer.start();
    }

    public boolean shouldDoorClosed(){
        if(door.getDoorState().equals(DoorState.OPENED))
        {
            // System.out.println("Doors can be closed");
            return true;
        }
        return false;
    }

    public boolean shouldDoorOpen(){
        if(door.getDoorState().equals(DoorState.CLOSED)){
            //System.out.println("Doors can be opened");
            return true;
        }
        return false;
    }

    public DoorState getDoorState(){
        return door.getDoorState();
    }

    public Door getDoor() {return this.door;}

    public void setDoorOpen(boolean canDoorOpen){
        this.canDoorOpen = canDoorOpen;
    }

    public void setDoorClose(boolean canDoorClose){
        this.canDoorClose = canDoorClose;
    }
}
