package Synchronisation.WithoutSynchronisation;

public class MyThread extends Thread {
        private  Counter counter;

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
        /*
       Based on code we are running a loop 50 times, so each thread incrementing it 50 and 50, ideally
        the counter ans should be 100;

        But O/p we got:- 67
        * */
}
/*
Reason:-
The output of the code is not 2000 because the increment method in the Counter class is not synchronized. This results in a race condition when both
threads try to increment the count variable concurrently.
Without synchronization, one thread might read the value of count before the other thread has finished writing its incremented value.
This can lead to both threads reading the same value, incrementing it, and writing it back, effectively losing one of the increments.
basically Outcome was dependent on Threads relative time- which becomes unpredictable/Race condition
We can fix this by using synchronized keyword.

 */
