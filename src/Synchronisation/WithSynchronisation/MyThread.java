package Synchronisation.WithSynchronisation;

import Synchronisation.WithSynchronisation.Counter;

public class MyThread extends Thread {
        private Counter counter;

        public MyThread(Counter counter){
                this.counter=counter;
        }

        @Override
        public void run() {
                for(int i=0;i<50;i++){
                        counter.increment();
                }
        }

        public static void main(String[] args){
               Counter counter=new Counter();
                MyThread t1=new MyThread(counter);
               MyThread t2=new MyThread(counter);
                t1.start();
                t2.start();
                try{

                        t1.join();
                        t2.join();
                }catch(Exception e){

                }
                //reason to call join method is to make sure both thread execution is completed and then the next line is called
                //there might be chance that any 1 thread is completed and the next line got executed which gives wrong ans.
                System.out.println(counter.getCount());
        }
}
/*
O/p:- 100

By synchronizing the increment method, you ensure that only one thread can execute this method at a time, which prevents the race condition.
With this change, the output will consistently be 100.
So Race condition can be avoided by using Synchronized keyword,
Mutual Exclusion-it makes sure that multiple threads should not access the critical section simultaneously
*/
