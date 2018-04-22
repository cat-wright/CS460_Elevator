package Cabin;

public class MotorPhysics {

    private long currentTime;
    private double elevatorSpeed = 1.5/1000.0; //metres/second

    public MotorPhysics() {
        System.out.println(System.currentTimeMillis());
        currentTime = System.currentTimeMillis();
    }

    public double move() {
        return elevatorSpeed*(System.currentTimeMillis() - currentTime);
    }

    public void setCurrentTime() {
        currentTime = System.currentTimeMillis();
    }

}
