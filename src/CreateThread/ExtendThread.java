package CreateThread;

/*
Method 1: extend the Thread class
1. A new class  is created that extends Thread.
2. The run method is overridden to define the code that constitutes the new thread.
3. start method is called to initiate the new thread.
 */
public class ExtendThread extends Thread{

        @Override
        public void run() {
               for(int i=0;i<100;i++){
                       System.out.println("Currently Running "+Thread.currentThread().getName());
               }
        }


        public static void main(String[] args){

                //Both Default and Custom threads will execute in Random Order
                ExtendThread thread=new ExtendThread();
                thread.start();

                for(int i=0;i<100;i++){
                        System.out.println("Currently Running: " + Thread.currentThread().getName());
                }
        }
}
