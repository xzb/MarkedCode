package test;

/**
 * Created by xiezebin on 10/22/15.
 *
 * Readme: Three different printers (Objects), each printer has 1 thread, print the value in order.
 *         Similar to buyer-seller scenario.
 */
public class MultiThreadTest {

    static Object sig1 = new Object();
    static Object sig2 = new Object();
    static Object sig3 = new Object();
//    static Semaphore sig1to2 = new Semaphore(1);
//    static Semaphore sig2to3 = new Semaphore(1);
//    static Semaphore sig3to1 = new Semaphore(1);

    static class Printer1 implements Runnable {
        public void run() {
            try {
                while (true) {
                    synchronized (sig1) {
                        sig1.wait();
                    }
                    System.out.println("1");
                    synchronized (sig2) {
                        sig2.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class Printer2 implements Runnable {
        public void run() {
            try {
                while (true) {
                    synchronized (sig2) {
                        sig2.wait();
                    }
                    System.out.println("22");
                    synchronized (sig3) {
                        sig3.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class Printer3 implements Runnable {
        public void run() {
            try {
                while (true) {
                    synchronized (sig3) {
                        sig3.wait();
                    }
                    System.out.println("333");
                    synchronized (sig1) {
                        sig1.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Printer1 printer1 = new Printer1();
        Thread t1 = new Thread(printer1);
        Thread t11 = new Thread(printer1);
        t1.start();
        t11.start();

        Printer2 printer2 = new Printer2();
        Thread t2 = new Thread(printer2);
        t2.start();

        Printer3 printer3 = new Printer3();
        Thread t3 = new Thread(printer3);
        t3.start();

        synchronized (sig1) {
            sig1.notify();
        }
    }
}


