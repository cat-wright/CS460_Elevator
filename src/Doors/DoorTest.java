package Doors;

public class DoorTest {
    public static void main(String[] args){
        DoorController dc = new DoorController();
        while (true){
            if (dc.checkADoorES(0, 1).equals(DoorState.CLOSED)) {
                dc.openDoorAtES(1,1,true);
                sleep(25);
                System.out.println(dc.checkADoorES(0,1));
            } else if(dc.checkADoorES(0,1).equals(DoorState.OPENED)){
                dc.closeDoorAtES(0,1, true);
                sleep(25);
                System.out.println(dc.checkADoorES(0,1));
            }

        }




    }
    private static void sleep(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
