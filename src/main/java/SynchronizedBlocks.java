import java.util.*;
public class SynchronizedBlocks {
    private int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8 ,9, 10};
    private int[] b = {0, 11, 12, 13, 14, 15, 16, 17, 18 ,19, 20};

    private List<Integer> intList1 = new ArrayList<>();
    private List<Integer> intList2 = new ArrayList<>();

    final Object  lock1 = new Object();
    final Object  lock2 = new Object();


    public static void main(String[] args) {
        SynchronizedBlocks sb = new SynchronizedBlocks();
        sb.copy();
    }

    private void copy(){
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(new RunnerA());
        Thread thread2 = new Thread(new RunnerB());

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        System.out.println("List has taken " + (end-start) + " milliseconds");
    }

    private void copyArrayToList(int[] array, List<Integer> list, Object lock){
        synchronized (lock){
            for (int element : array) {
                list.add(element);
                System.out.println(list);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void copyArrayA() {
        copyArrayToList(a, intList1, lock1);

    }

    private void copyArrayB() {
        copyArrayToList(b, intList2, lock2);
    }

    private class RunnerA implements Runnable{

        @Override
        public void run() {
            copyArrayA();
        }
    }

    private class RunnerB implements Runnable{

        @Override
        public void run() {
            copyArrayB();
        }
    }
}
