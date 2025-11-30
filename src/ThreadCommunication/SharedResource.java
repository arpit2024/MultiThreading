package ThreadCommunication;

class SharedResource {
        private int data;
        private boolean hasData;

    /*
     We are using the SharedResource object as the lock (via synchronized methods).
     Only ONE thread (producer or consumer) can enter produce() or consume() at a time.

     Producer logic:
       → If data already exists (hasData = true), do NOT produce again → wait()
     Consumer logic:
       → If data does NOT exist (hasData = false), do NOT consume → wait()

     Important:
     • wait() releases the intrinsic lock and pauses the thread
     • notify() wakes one of the waiting threads so it can re-acquire the lock
     • wait() and notify() MUST be called inside synchronized blocks/methods

     Why 'while' instead of 'if'?
     -----------------------------------
     Using 'while' prevents incorrect execution if the waiting thread wakes up
     due to spurious wakeups or if multiple threads are waiting for the condition.
     Thread re-checks the condition before continuing execution.
    */

        public synchronized void produce(int value) {
                while (hasData) {  // Buffer full → wait for consumer to consume
                        try {
                                wait();   // Release lock + wait for consumer
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        }
                }

                data = value;
                hasData = true;
                System.out.println("Produced: " + value);
                notify();   // Wake up consumer waiting to consume
        }

        public synchronized int consume() {
                while (!hasData) { // Buffer empty → wait for producer to produce
                        try {
                                wait();    // Release lock + wait for producer
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        }
                }

                hasData = false;   // Mark buffer as empty
                System.out.println("Consumed: " + data);
                notify();          // Wake up producer waiting to produce
                return data;
        }
}
