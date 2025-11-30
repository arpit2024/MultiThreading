package Locks.DeadLocks;

/*
Deadlock
A deadlock occurs in concurrent programming when two or more threads are blocked forever, each waiting for the other to release a resource.
This typically happens when threads hold locks on resources and request additional locks held by other threads. For example,
Thread A holds Lock 1 and waits for Lock 2, while Thread B holds Lock 2 and waits for Lock 1. Since neither thread can proceed, they remain stuck in a deadlock state.
 Deadlocks can severely impact system performance and are challenging to debug and resolve in multi-threaded applications.
 */
public class DeadlockExample {
        public static void main(String[] args) throws InterruptedException {
                Pen pen = new Pen();
                Paper paper = new Paper();

                Thread thread1 = new Thread(new Task1(pen, paper), "Thread-1");
                Thread thread2 = new Thread(new Task2(pen, paper), "Thread-2");

                thread1.start();
                thread2.start();
                thread1.join();
                thread2.join();
                System.out.println("Execution completed");
        }
}
/*
Deadlocks typically occur when four conditions are met simultaneously:

1. Mutual Exclusion: Only one thread can access a resource at a time.

2. Hold and Wait: A thread holding at least one resource is waiting to acquire additional resources held by other threads.

3. No Preemption: Resources cannot be forcibly taken from threads holding them.

4. Circular Wait: A set of threads is waiting for each other in a circular chain.
 */