package ThreadCommunication;

public class ThreadCommmunication {

        public static void main(String[] args){
                SharedResource resource=new SharedResource();
                Thread producerThread=new Thread(new Producer(resource));
                Thread consumerThread=new Thread(new Consumer(resource));

                producerThread.start();
                consumerThread.start();
        }
}
/*
Expected Output Pattern:
------------------------
Produced: 0
Consumed: 0
Produced: 1
Consumed: 1
Produced: 2
Consumed: 2
...
Reason:
Threads communicate in turns — producer waits for consumer and consumer waits for producer.

Key Takeaway:
-------------
wait() → release lock + pause thread until notified
notify() → wake one waiting thread
Together they enable safe thread coordination.
*/

/*
===========================================
 THREAD COMMUNICATION — PRODUCER / CONSUMER
 using wait() and notify()
===========================================

This program demonstrates thread communication where:
• Producer generates data
• Consumer consumes data
• Both share one buffer (SharedResource)

Why wait() is needed:
---------------------
Producer must not overwrite existing data → waits when hasData = true
Consumer must not read when no data available → waits when hasData = false

Role of notify():
-----------------
After producing:
    Producer calls notify() → wakes consumer
After consuming:
    Consumer calls notify() → wakes producer

Why synchronized is required:
-----------------------------
wait() and notify() can ONLY be used inside synchronized blocks/methods
because thread must own the intrinsic lock before calling them.

Why 'while' is used instead of 'if':
------------------------------------
Prevents incorrect execution in case of:
• Spurious wakeups
• Multiple waiting threads
The condition is always re-checked after waking up.

Flow of execution:
------------------
Produce → notify → Consumer wakes → Consume → notify → Producer wakes
Repeats in sequence, ensuring no data loss and no overwriting.
*/

