package Synchronisation.WithSynchronisation;

public class Counter {

        private int count=0;

        //Synchronized method- Critical Section
        public synchronized void  increment(){
                count++;
                /*if we dont need to synchronize the whole method instead we can just synchronize part of code called Synchronized block
                synchronized(this){ //this refers that we are synchronising 1 instance,for the one object for which we are incrementing count;
                        count++;
                }
                 */
        }
        public int getCount(){
                return count;
        }
}
