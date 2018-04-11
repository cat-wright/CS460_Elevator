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

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
}
