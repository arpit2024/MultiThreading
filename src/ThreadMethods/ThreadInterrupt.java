package ThreadMethods;


/*
 interrupt(): Interrupts the thread. If the thread is blocked in a call to wait(), sleep(), or join(), it will throw an InterruptedException.
*/
public class ThreadInterrupt extends Thread{
        @Override
        public void run() {
                try {
                        Thread.sleep(1000);
                        System.out.println("Thread is Running. . . . ");
                }catch (InterruptedException e){
                        System.out.println("Thread Interrupted. . . "+e);
                }

        }
        public static void main(String[] args) throws InterruptedException{
                ThreadInterrupt t1=new ThreadInterrupt();
                t1.start();
                t1.interrupt();

        }
}
