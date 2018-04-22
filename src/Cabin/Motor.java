package Cabin;

public class Motor extends Thread{

    private Integer currentFloor;
    private int requestFloor;
    private boolean atFloor = false;
    private double elevatorPosition;
    MotorPhysics motorPhysics = new MotorPhysics();

    public Motor(int floorDestination, Integer startingFloor) {
        currentFloor = startingFloor;
        elevatorPosition = (double)startingFloor;
        requestFloor = floorDestination;
    }

    @Override
    public void run() {

        atFloor = false;
        if (requestFloor < currentFloor) {
            for (Integer i = currentFloor; i >= requestFloor; i--) {
                while (true) { //need to add functionality for checking if motor should move

                    elevatorPosition -= motorPhysics.move();
                    motorPhysics.setCurrentTime();

                    if (i >= (elevatorPosition + .01))
                        break;
                }
                if (i == requestFloor) {
                    System.out.println("Aligning");
                    CabinAlignment ca = new CabinAlignment(false, i, elevatorPosition);
                    ca.align();
                }
                currentFloor = i;
                System.out.println("Current Floor: " + i);
            }
        } else {
            for (Integer i = currentFloor; i <= requestFloor; i++) {
                while (true) {

                    elevatorPosition += motorPhysics.move();
                    motorPhysics.setCurrentTime();

                    if (i <= (elevatorPosition + .01))
                        break;
                }
                if (i == requestFloor) {
                    System.out.println("Aligning");
                    CabinAlignment ca = new CabinAlignment(true, i, elevatorPosition);
                    ca.align();
                }
                currentFloor = i;
                System.out.println("Current Floor: " + i);
            }
        }
        atFloor = true;
    }

    public boolean getAtFloor() {
        return atFloor;
    }


    public Integer getCurrentFloor() {
        return currentFloor;
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
