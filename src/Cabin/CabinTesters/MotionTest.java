package Cabin.CabinTesters;

import Cabin.Motion;

public class MotionTest {

    public static void main(String[] args) {
        Motion m = new Motion();
        m.moveCabin(5);
        while (true) {
            //System.out.println("Checker: " + m.getCabinLocation());
            System.out.println( m.isAtFloor());
            sleep(.2);
            if (m.getCabinLocation() == 5) break;
        }

        System.out.println(m.isAtFloor());

        m.moveCabin(2);
        while (true) {
            //System.out.println("Checker2: " + m.getCabinLocation());
            System.out.println( m.isAtFloor());
            sleep(.2);
            if (m.getCabinLocation() == 2) break;
        }
        System.out.println(m.isAtFloor());
    }

    private static void sleep(double sec) {
        try {
            Thread.sleep((int)(1000 * sec));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
