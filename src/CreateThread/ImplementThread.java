package CreateThread;


/*
Method 2: Implement Runnable interface
1. A new class World is created that implements Runnable.
2. The run method is overridden to define the code that constitutes the new thread.
3. A Thread object is created by passing an instance of test.
4. start method is called on the Thread object to initiate the new thread.
*/
public class ImplementThread implements Runnable {
        @Override
        public void run() {
                for(int i=0;i<100;i++){
                        System.out.println("Current Thread: "+Thread.currentThread().getName());
                }
        }

        public static void main(String[] args){
                ImplementThread test=new ImplementThread();
                Thread t1=new Thread(test);
                t1.start();

                for(int i=0;i<100;i++){
                        System.out.println("Current Thread: "+Thread.currentThread().getName());
                }
        }
}


