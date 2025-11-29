package CreateThread;


/*
Thread Lifecycle
The lifecycle of a thread in Java consists of several states, which a thread can move through during its execution.

1. New: A thread is in this state when it is created but not yet started.
2. Runnable: After the start method is called, the thread becomes runnable. It’s ready to run and is waiting for CPU time.
3. Running: The thread is in this state when it is executing.
4. Blocked/Waiting: A thread is in this state when it is waiting for a resource or for another thread to perform an action.
5. Terminated: A thread is in this state when it has finished executing.
* */
public class MyThread extends Thread{
        @Override
        public void run() {
                System.out.println("RUNNING");
                try{
                        Thread.sleep(2000);
                }catch (InterruptedException e){
                        System.out.println(e)  ;
                }
        }

        public static void main(String[] args) throws InterruptedException {
                MyThread t1 = new MyThread();
                System.out.println(t1.getState()); // NEW
                t1.start();
                System.out.println(t1.getState()); // RUNNABLE
                Thread.sleep(100);//pausing main method so that out threads start running
                System.out.println(t1.getState()); // TIMED_WAITING
                t1.join();//main method will wait for t1 to finish
                System.out.println(t1.getState()); // TERMINATED


        }
}
