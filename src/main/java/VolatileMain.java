

public class VolatileMain {
    private static volatile int counter;
    public static void main(String[] args) {
        Thread writer = new SimpleWriter();
        Thread reader = new SimpleReader();
        writer.start();
        reader.start();
    }

    private static class SimpleWriter extends Thread{
        @Override
        public void run() {
            int localCounter = counter;
            for(int i = 0; i < 10; i++){
                System.out.println(ColorScheme.BLUE+"Writer increments counter " + (localCounter+1));
                counter = ++localCounter;
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static class SimpleReader extends Thread{
        @Override
        public void run() {
            int localCounter = counter;
            while(localCounter < 10){
                if(localCounter != counter){
                    System.out.println(ColorScheme.YELLOW+"Reader reads counter");
                    localCounter = counter;
                }
            }
        }
    }
}
