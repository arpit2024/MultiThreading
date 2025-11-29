package ThreadMethods;

/*
 yield(): Thread.yield() is a static method that suggests the current thread temporarily pause its execution to allow other threads of the same or higher priority to execute.
  It’s important to note that yield() is just a hint to the thread scheduler, and the actual behavior may vary depending on the JVM and OS.
* */

public class ThreadYield extends Thread{

        public ThreadYield(String name){
                super(name);
        }
        @Override
        public void run() {
                for(int i=0;i<5;i++){
                        System.out.println(Thread.currentThread().getName()+" is running. ");
                        Thread.yield();//A hint to the scheduler that the current thread is willing to yield its current use of a processor. give chance to other thread too
                }

        }
        public static void main(String[] args) throws InterruptedException{
                ThreadYield t1=new ThreadYield("t1");
                ThreadYield t2=new ThreadYield("t2");
                t1.start();
                t2.start();


        }

}
