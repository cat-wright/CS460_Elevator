package Cabin;

public class Motion extends Thread{

    private Integer cabinLocation = 1;
    private Motor motor;


    public void moveCabin(int floorToMoveTo) {
        motor = new Motor(floorToMoveTo, cabinLocation);
        motor.start();
        cabinLocation = floorToMoveTo;
    }

    public boolean isAtFloor() {
        return motor.getAtFloor();
    }


    public int getCabinLocation() {
        return motor.getCurrentFloor();
    }




}
