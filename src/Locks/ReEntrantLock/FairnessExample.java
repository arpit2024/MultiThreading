package Locks.ReEntrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
Fairness of Locks
Fairness in the context of locks refers to the order in which threads acquire a lock. A fair lock ensures that threads acquire the lock in the order they requested it,
 preventing thread starvation. With a fair lock, if multiple threads are waiting, the longest-waiting thread is granted the lock next. However, fairness can lead to
 lower throughput due to the overhead of maintaining the order. Non-fair locks, in contrast, allow threads to “cut in line,” potentially offering better performance
 but at the risk of some threads waiting indefinitely if others frequently acquire the lock.
 */
public class FairnessExample {

        // true → enables fairness (threads served in FIFO order)
        private static final Lock lock = new ReentrantLock(true);

        public static void worker() {
                String name = Thread.currentThread().getName();
                for (int i = 1; i <= 3; i++) {
                        lock.lock();
                        try {
                                System.out.println(name + " acquired lock, iteration: " + i);
                                Thread.sleep(500);
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        } finally {
                                lock.unlock();
                        }
                }
        }

        public static void main(String[] args) {
                Thread t1 = new Thread(FairnessExample::worker, "Thread-1");
                Thread t2 = new Thread(FairnessExample::worker, "Thread-2");
                Thread t3 = new Thread(FairnessExample::worker, "Thread-3");

                t1.start();
                t2.start();
                t3.start();
        }
}

/*
🔐 Fairness in ReentrantLock — Important Concept

WHAT IS FAIRNESS IN REENTRANTLOCK?

ReentrantLock supports an optional "fairness" policy that controls how threads acquire a lock.

• If fairness = false (default)
The lock may be granted to whichever thread requests it first or repeatedly.
This produces higher performance but may cause starvation.

• If fairness = true
Threads acquire the lock in FIFO order (first-come-first-served).
The thread waiting the longest gets the highest priority.

Example declaration:
Lock lock = new ReentrantLock(true); // Fair lock
Lock lock = new ReentrantLock(false); // Unfair lock (default)

WHY FAIRNESS IS NEEDED

When many threads try to access a shared resource, an unfair lock may allow
one thread to keep reacquiring the lock multiple times while others keep waiting.
In certain critical systems, this unequal scheduling cannot be tolerated.
Fair lock ensures equal opportunity to all waiting threads.

HOW FIFO SCHEDULING WORKS

• Threads form an internal waiting queue inside the lock structure
• The lock is given to the thread at the front of the queue
• When the thread releases the lock, the next waiting thread gets it

FAIR LOCK — ADVANTAGES

• Prevents starvation
• Predictable scheduling — fairness guaranteed
• Ensures every thread gets a turn eventually
• Best for financial, scheduling, and priority-critical systems

FAIR LOCK — DISADVANTAGES

• Slight reduction in performance
• Overhead for maintaining fairness queue
• Not required when all threads do equal work and starvation is unlikely

UNFAIR LOCK (DEFAULT) — WHY IT EXISTS

Unfair locking improves performance because:
• A thread that already has the lock may reacquire it quickly
• Less scheduling overhead
• Better CPU throughput

Best for:
• Games, animations, UI, real-time rendering
• Systems prioritizing speed over guaranteed fairness

WHEN TO USE FAIR VS UNFAIR LOCK

Use FAIR lock when:
• Starvation must be prevented
• Tasks have different priorities
• Long-running transactions exist
• Predictability is more important than performance

Use UNFAIR lock when:
• Highest performance is required
• Starvation is unlikely
• All threads perform equal-weight tasks

=============================================================
        🎯 Key Takeaway

Fair lock focuses on correctness and equal opportunity → predictable but slower.
Unfair lock focuses on performance → faster but may lead to starvation.

        =============================================================
Expected Console Behaviour (Fair Lock)

Example pattern of execution://Order may vary slightly, but FIFO pattern is always maintained.
Thread-1 acquired lock, iteration 1
Thread-2 acquired lock, iteration 1
Thread-3 acquired lock, iteration 1
Thread-1 acquired lock, iteration 2
Thread-2 acquired lock, iteration 2
Thread-3 acquired lock, iteration 2
        ...
        (FIFO order — no jumping or skipping)

With unfair lock (default), the order is unpredictable and a thread can re-acquire repeatedly before others get a chance.


INTERVIEW QUESTIONS AND ANSWERS

--- Basic Level ---
Q1) What is fairness in ReentrantLock?
A) Fairness ensures the longest-waiting thread gets the lock first (FIFO).

Q2) How do you create a fair lock?
A) Lock lock = new ReentrantLock(true);

Q3) What is the default mode?
A) Unfair lock (new ReentrantLock(false))

--- Medium Level ---
Q4) Why is fairness not the default?
A) Fair locks reduce throughput because of scheduling overhead.

Q5) What is starvation?
A) When a thread waits indefinitely because other threads repeatedly acquire the lock.

Q6) Does fairness guarantee best performance?
A) No. It improves fairness but slows execution due to extra queue management.

--- Advanced Level ---
Q7) Can fair locks still be unfair sometimes?
A) Yes. The lock is fair only when threads are queued. A thread arriving at an idle moment can immediately acquire the lock, skipping others.

Q8) Does fairness apply in reentrancy?
A) No. If a thread already holds the lock, it can reacquire it immediately regardless of queue order (reentrancy has higher priority than fairness).

Q9) In which systems should fair lock definitely be used?
A) Banking transactions, schedulers, reservation systems, high-latency tasks, and real-time concurrency-sensitive platforms.

--- Expert / System Design ---
Q10) Is fairness guaranteed in synchronized?
A) No. JVM does not support fairness with intrinsic (monitor) locks.

Q11) Can fairness eliminate deadlocks?
A) No. Deadlocks occur from cyclic waiting. Fairness avoids starvation, not deadlock.

Q12) Is fairness required in thread pools?
A) No. ThreadPoolExecutor already manages scheduling separately from lock fairness.
*/