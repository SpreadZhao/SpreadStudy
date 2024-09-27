package questions;

/**
 * Link: <a href="https://leetcode.com/problems/print-in-order/">Print in Order</a>
 */
public class PrintInOrder {

    private boolean firstDone;
    private boolean secondDone;

    public PrintInOrder() {
        firstDone = false;
        secondDone = false;
    }

    public synchronized void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        firstDone = true;
        notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        // printSecond.run() outputs "second". Do not change or remove this line.
        while (!firstDone) wait();
        printSecond.run();
        secondDone = true;
        notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while (!secondDone) wait();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
