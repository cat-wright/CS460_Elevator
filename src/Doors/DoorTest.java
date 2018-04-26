package Doors;

public class DoorTest {
    public static void main(String[] args){
        DoorController dc = new DoorController();
        while (true){
            if (dc.checkADoorES(1, 1).equals(DoorState.CLOSED)) {
                dc.openDoorAtES(1,1,true);
                sleep(25);
                System.out.println(dc.checkADoorES(1,0));
            } else if(dc.checkADoorES(1,1).equals(DoorState.OPENED)){
                dc.closeDoorAtES(1,0, true);
                sleep(25
                );
                System.out.println(dc.checkADoorES(1,0));
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
