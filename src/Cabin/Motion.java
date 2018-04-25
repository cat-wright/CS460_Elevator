package Cabin;

public class Motion extends Thread {

    private Integer cabinLocation = 1;
    private Motor motor;

    public Motion() {
        motor = new Motor(cabinLocation);
        motor.start();
    }

    public void moveCabin(int floorToMoveTo) {
        motor.setMove(floorToMoveTo);

        //cabinLocation = floorToMoveTo;
    }

    public boolean isAtFloor() {
        return motor.getAtFloor();
    }

    public int getCabinLocation() {
        return motor.getCurrentFloor();
    }




}
