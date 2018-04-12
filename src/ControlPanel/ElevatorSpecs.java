package ControlPanel;

import Request.*;

public class ElevatorSpecs {

    private boolean isMoving;
    private Request currentRequest;
    private Integer currentFloor;
    private Directions direction;

    public ElevatorSpecs(boolean isMoving, Request currentRequest, Integer currentFloor, Directions direction)
    {
        this.isMoving = isMoving;
        this.currentRequest = currentRequest;
        this.currentFloor = currentFloor;
        this.direction = direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public Directions getDirection() {
        return direction;
    }
}
