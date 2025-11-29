package ThreadMethods;

/*
Thread methods
1. start( ): Begins the execution of the thread. The Java Virtual Machine (JVM) calls the run() method of the thread.
2. run( ): The entry point for the thread. When the thread is started, the run() method is invoked. If the thread was created using a class that implements Runnable, the run() method will execute the run() method of that Runnable object.
3. sleep(long millis): Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
4. join( ): Waits for this thread to die. When one thread calls the join() method of another thread, it pauses the execution of the current thread until the thread being joined has completed its execution.
*/

public class ThreadSleep extends Thread {
        @Override
        public void run() {
                for(int i=1;i<=5;i++){
                        try {
                                Thread.sleep(1000);
                        }catch (InterruptedException e){
                                throw new RuntimeException();
                        }
                        System.out.println(i);
                }
                System.out.println("Custom Thread Execution completed");
        }
        public static void main(String[] args) throws InterruptedException{
                ThreadSleep t1=new ThreadSleep();
                t1.start();
                t1.join();//this line will be called by main method, but it is waiting for t1 to complete
                // so that main method can process for the newt line execution
                System.out.println("Executing after t1.join method, this line is executed by: "+Thread.currentThread().getName());
        }
}
