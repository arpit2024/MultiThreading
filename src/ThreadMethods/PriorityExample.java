package ThreadMethods;


/*
setPriority(int newPriority): Changes the priority of the thread.
 The priority is a value between Thread.MIN_PRIORITY (1) and Thread.MAX_PRIORITY (10).
 */
public class PriorityExample extends Thread{
        //To give a custom name to thread Call its constructor
        public PriorityExample(String name){
                super(name);
        }
        @Override
        public void run() {
                for(int i=1;i<=5;i++){
                        System.out.println(Thread.currentThread().getName()+ " - Priority: "+Thread.currentThread().getPriority()+ " - count: "+i);
                        try{
                                Thread.sleep(100);
                        }
                        catch (InterruptedException e){

                        }
                }
        }

        public static void main(String[] args){
                PriorityExample t1=new PriorityExample("Low Priority Thread");
                PriorityExample t2=new PriorityExample("Medium Priority Thread");
                PriorityExample t3=new PriorityExample("High Priority Thread");
                t1.setPriority(Thread.MIN_PRIORITY);
                t2.setPriority(Thread.NORM_PRIORITY);
                t3.setPriority(Thread.MAX_PRIORITY);
                t1.start();
                t2.start();
                t3.start();

        }
}
