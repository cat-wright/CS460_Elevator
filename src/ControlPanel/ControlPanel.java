package ControlPanel;

import Cabin.CabinButtons;
import Doors.Door;
import Floors.Floor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import Request.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ControlPanel extends Thread
{
    private volatile boolean finished = false;
    private ControlGUI controller;
    private int floors, elevators;
    private ElevatorSpecs specs = new ElevatorSpecs(false,  1, null);

    private boolean fireAlarm = false;
    private Boolean[] lockedElevators = {false, false, false, false};
    private Boolean[] maintenanceKeys = {false, false, false, false};

    public ControlPanel(int floors, int elevators)
    {
        this.floors = floors;
        this.elevators = elevators;
        new JFXPanel();
        Platform.runLater(() -> {
            try
            {
                controller = new ControlGUI(this, floors, elevators);
                controller.start(new Stage());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void run()
    {
        while(!finished)
        {
            if(controller != null) {
                for(int i = 0; i < elevators; i++)
                {
                    controller.getElevator(i).setSpecs(specs);
                }
                fireAlarm = controller.getFireAlarm();
            }
        }
    }

    public void setCabinList(ArrayList<Boolean> cabinList, int elevatorNumber)
    {
        if(controller != null)
        {
            controller.setCabinList(cabinList, elevatorNumber);
        }
    }

    public void setLobbyList(ArrayList<Floor> lobbyList)
    {
        if(controller != null)
        {
            controller.setLobbyList(lobbyList);
        }
    }

    public void setDoorList(ArrayList<Door> doorList, int elevatorNumber)
    {
        if(controller != null)
        {
            controller.setDoorList(doorList, elevatorNumber);
        }
    }

    public Boolean[] getLockedElevators()
    {
        if(controller != null) controller.updateLockedElevators();
        return lockedElevators;
    }

    public void lockElevator(int elevatorNumber)
    {
        if(controller != null)
        {
            controller.lockElevator(elevatorNumber);
            controller.updateLockedElevators();
        }
    }

    public void changeLocks(boolean lockStatus)
    {
        if(controller != null)
        {
            controller.setAllLocks(lockStatus);
            controller.updateLockedElevators();
        }
    }

    //INFORMATION PASSED FROM CONTROL PANEL TO BUILDING CONTROL
    /**
     * returns whether the fire alarm has been pressed on the interface
     * @return true if fire alarm was pressed
     */
    public boolean isFireAlarm()
    {
        return fireAlarm;
    }

    /**
     * returns whether the maintenance key is currently in the elevator
     * @return true if maintenance key is in the elevator
     */
    public Boolean[] getMaintenanceKeys()
    {
        if(controller != null) controller.updateMaintenanceKeys();
        return maintenanceKeys;
    }

    /**
     * returns the current request from the interface to be added to the queue in BuildingControl
     * @return a request to travel to a floor
     */
    public Request getRequest(int elevatorNumber)
    {
        return controller.getRequest(elevatorNumber);
    }
    //

    //INFORMATION PASSED FROM BUILDING CONTROL TO CONTROL PANEL
    /**
     * called by building control with all current information about the elevator (currently only one elevator)
     * @param isMoving true if the elevator is currently in motion
     * @param currentFloor the current floor the cabin is on
     * @param direction the current direction of the cabin
     */
    public void buildElevatorSpecs(boolean isMoving, Integer currentFloor, Directions direction)
    {
        specs = new ElevatorSpecs(isMoving, currentFloor, direction);
    }

    void shutdown()
    {
        finished = true;
    }
}
