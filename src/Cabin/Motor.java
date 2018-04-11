package Cabin;

public class Motor extends Thread{

    private int currentFloor;
    private int requestFloor;
    private boolean atFloor = false;

    public Motor(int floorDestination, int startingFloor) {
        currentFloor = startingFloor;
        requestFloor = floorDestination;

    }

    @Override
    public void run() {
        atFloor = false;
        if (requestFloor < currentFloor) {
            for (int i = currentFloor; i >= requestFloor; i--) {
                sleep(1);
                currentFloor = i;
                System.out.println("Current Floor: " + i);
            }
        } else {
            for (int i = currentFloor; i <= requestFloor; i++) {
                sleep(1);
                currentFloor = i;
                System.out.println("Current Floor: " + i);
            }
        }
        atFloor = true;
        System.out.println("Done");
    }

    public boolean getAtFloor() {
        return atFloor;
    }


    public int getCurrentFloor() {
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
