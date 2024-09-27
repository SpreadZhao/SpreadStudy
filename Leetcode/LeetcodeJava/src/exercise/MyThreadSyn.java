package exercise;

public class MyThreadSyn {
    private static int order = 0;

    private static void print(int num, int order) {
        System.out.printf("%d%c", num, 'A' + order);
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new printOne());
        Thread t2 = new Thread(new printTwo());
        Thread t3 = new Thread(new printThree());
        t1.start();
        t2.start();
        t3.start();
        while (true) ;
    }

    public static class printOne implements Runnable {
        @Override
        public void run() {
            while (order < 26) {
                synchronized (this) {
                    while (0 != order % 3) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    print(1, order);
                    order++;
                    notifyAll();
                    notify();
                }
            }
        }
    }

    public static class printTwo implements Runnable {

        @Override
        public void run() {
            while (order < 26) {
                synchronized (this) {
                    while (1 != order % 3) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    print(2, order);
                    order++;
                }
                notifyAll();
            }
        }
    }

    public static class printThree implements Runnable {

        @Override
        public void run() {
            while (order < 26) {
                synchronized (this) {
                    while (2 != order % 3) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    print(3, order);
                    order++;
                }
                notifyAll();
            }
        }
    }
}
