package Cabin;

public class Motor extends Thread{

    private Integer currentFloor;
    private boolean atFloor = false;
    private double elevatorPosition;
    private int floorToMoveTo;
    private boolean canRun = true;
    private boolean torf = false;
    MotorPhysics motorPhysics = new MotorPhysics();

    public Motor(Integer startingFloor) {
        currentFloor = startingFloor;
        elevatorPosition = (double)startingFloor;
    }

    public void setMove(int floorToMoveTo) {
        torf = true;
        this.floorToMoveTo = floorToMoveTo;
    }

    public void move() {
        atFloor = false;
        if (floorToMoveTo < currentFloor) {
            for (Integer i = currentFloor; i >= floorToMoveTo; i--) {
                while (true) { //need to add functionality for checking if motor should move

                    elevatorPosition -= motorPhysics.move();
                    motorPhysics.setCurrentTime();

                    if (i >= (elevatorPosition + .01))
                        break;
                }
                if (i == floorToMoveTo) {
                    System.out.println("Aligning");
                    CabinAlignment ca = new CabinAlignment(false, i, elevatorPosition);
                    ca.align();
                }
                currentFloor = i;
                System.out.println("Current Floor: " + i);
            }
        } else {
            for (Integer i = currentFloor; i <= floorToMoveTo; i++) {
                while (true) {

                    elevatorPosition += motorPhysics.move();
                    motorPhysics.setCurrentTime();

                    if (i <= (elevatorPosition + .01))
                        break;
                }
                if (i == floorToMoveTo) {
                    System.out.println("Aligning");
                    CabinAlignment ca = new CabinAlignment(true, i, elevatorPosition);
                    ca.align();
                }
                currentFloor = i;
                System.out.println("Current Floor: " + i);
            }
        }
        atFloor = true;
        torf = false;
    }

    @Override
    public void run() {
        while (canRun) {
            if (torf) {
                move();
            } else {
                sleep(1);
            }
        }
    }

    public boolean getAtFloor() {
        return atFloor;
    }


    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCanRun(boolean g) {
        canRun = g;
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
