package Cabin;

public class CabinAlignment{

    private double elevatorPosition;
    private int targetFloor;
    private boolean upOrDown;
    private long currentTime;
    private double elevatorSpeed = 0.15/1000.0; //metres/second, 10% of the elevator speed

    public CabinAlignment(boolean upOrDown, int targetFloor, double elevatorPosition) {
        this.upOrDown = upOrDown;
        this.targetFloor = targetFloor;
        this.elevatorPosition = elevatorPosition;
    }

    public void align() {
        while (true) {
            if (upOrDown)
                elevatorPosition += move();
            else
                elevatorPosition -= move();
            setCurrentTime();

            if (targetFloor <= elevatorPosition + .001) //within 1 cm of floor
                break;
            else if (targetFloor >= elevatorPosition + .001) //within 1 cm of floor
                break;
        }
        System.out.println("Aligned");
    }

    public double move() {
        return elevatorSpeed*(System.currentTimeMillis() - currentTime);
    }

    public void setCurrentTime() {
        currentTime = System.currentTimeMillis();
    }
}
