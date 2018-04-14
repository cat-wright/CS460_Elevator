package ControlPanel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import Request.*;
import javafx.stage.Stage;

import java.util.LinkedList;

public class ControlPanel extends Thread
{
    private volatile boolean finished = false;
    private ControlGUI controller;
    private int floors, elevators;
    private Integer currentFloor = 1;
    private ElevatorSpecs specs = new ElevatorSpecs(false, null, 1, null);
    private LinkedList<Request> disabledButtons = new LinkedList<>();
    //private LinkedList<Request> currentRequests = new LinkedList<>(); //will be implemented in a later version
    private Request currentTakenRequest;

    private boolean maintenanceKey = false;
    private boolean fireAlarm = false;

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
                controller.getElevator().setSpecs(specs);
                disabledButtons = controller.getDisabledButtons();
            }
        }
    }

    //INFORMATION PASSED FROM CONTROL PANEL TO BUILDING CONTROL
    /**
     * returns whether the fire alarm has been pressed on the interface
     * @return true if fire alarm was pressed
     */
    boolean isFireAlarm()
    {
        return fireAlarm;
    }

    /**
     * returns whether the maintenance key is currently in the elevator
     * @return true if maintenance key is in the elevator
     */
    boolean isMaintenanceKey() { return maintenanceKey; }

    /**
     * returns a list of all buttons that are currently disabled/not able to be pressed.  All are
     * expressed as requests, with the type, destination, and direction signifying which button
     * @return Linked List of Requests
     */
    public LinkedList<Request> getDisabledButtons()
    {
        return disabledButtons;
    }

    /**
     * returns the current request from the interface to be added to the queue in BuildingControl
     * @return a request to travel to a floor
     */
    public synchronized Request getRequest()
    {
        return controller.getRequest();
    }
    //

    //INFORMATION PASSED FROM BUILDING CONTROL TO CONTROL PANEL
    /**
     * called by building control with all current information about the elevator (currently only one elevator)
     * @param isMoving true if the elevator is currently in motion
     * @param currentTakenRequest the request currently being handled/where the cabin is going
     * @param currentFloor the current floor the cabin is on
     * @param direction the current direction of the cabin
     */
    public void buildElevatorSpecs(boolean isMoving, Request currentTakenRequest, Integer currentFloor, Directions direction)
    {
        specs = new ElevatorSpecs(isMoving, currentTakenRequest, currentFloor, direction);
    }

    //used in testing currently
    public void setCurrentRequest(Request request)
    {
        this.currentTakenRequest = request;
    }

    void shutdown()
    {
        finished = true;
    }

    //used in testing
//    public static void main(String[] args)
//    {
//        ControlPanel controlPanel = new ControlPanel(10, 4);
//        controlPanel.start();
//    }
}
