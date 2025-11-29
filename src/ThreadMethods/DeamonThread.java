package ThreadMethods;

/*
 Thread.setDaemon(boolean): Marks the thread as either a daemon thread or a user thread. When the JVM exits, all daemon threads are terminated.
* */
public class DeamonThread extends Thread {

    /*
     ===========================
     🚀  Daemon Thread Summary
     ===========================
     A Daemon Thread runs in the background and provides support services to User Threads.
     JVM does NOT wait for daemon threads to finish — if only daemon threads are running,
     the JVM terminates immediately.

     Real examples of daemon threads: Garbage Collector, Finalizer, Monitoring threads
     */

        public DeamonThread(String name) {
                super(name);
        }

        @Override
        public void run() {
        /*
          Infinite loop just for demonstration.
          Even though this loop never ends, the JVM will stop it if only
          daemon threads remain running.
        */
                while (true) {
                        System.out.println(Thread.currentThread().getName()+"- Hello World");
                }
        }

        public static void main(String[] args) {
                DeamonThread myThread = new DeamonThread("DeamonThread");

                // 🔹 Mark the thread as Daemon BEFORE start(), otherwise IllegalThreadStateException
                myThread.setDaemon(true);

                // Now the thread is in Runnable state
                myThread.start();

                System.out.println("Main done");

        /*
         ⚠ JVM Behaviour
         ----------------
         After printing "Main done", the main thread (user thread) exits.
         At this point, only a daemon thread is running → JVM will shut down the process.
         Therefore, only a few "Hello World" prints appear before termination.
        */
        }

    /*
     ==================================================================
     🔍 User Thread vs Daemon Thread (Quick Revision)
     ==================================================================
     | User Thread                               | Daemon Thread                        |
     |-------------------------------------------|--------------------------------------|
     | JVM waits for completion                  | JVM does NOT wait                    |
     | Does useful work for user                 | Supports background tasks            |
     | Example: main(), worker threads           | Example: GC, cleanup, monitor        |
     | Default thread type                       | Must be set explicitly               |

     🔹 Important Rule
       setDaemon(true) MUST be called before start().

     🔹 Child thread rule
       If a daemon thread creates a new thread → the new thread also becomes daemon.

     🔹 finally block warning
       Daemon threads are NOT guaranteed to execute finally {} during JVM shutdown.
     ==================================================================
     */

    /*
     =====================================================
     🧠 Daemon Thread — Interview Questions (With Answers)
     =====================================================

     ❓ Does JVM wait for daemon threads?
        ➤ No. JVM terminates once all user threads are finished.

     ❓ What happens if setDaemon(true) is called after start()?
        ➤ Throws IllegalThreadStateException.

     ❓ Is main thread a daemon thread?
        ➤ No. It is always a user thread.

     ❓ Will daemon thread stop immediately when main ends?
        ➤ Not strictly immediately — it stops as soon as JVM shuts down.

     ❓ Will a daemon thread execute finally block?
        ➤ Not guaranteed. It may be terminated abruptly.

     ❓ If a daemon thread creates another thread, what type is it?
        ➤ Daemon. Daemon nature is inherited.

     ❓ Why GC is a daemon thread?
        ➤ Because GC should not prevent JVM shutdown — it is only for background cleanup.
     =====================================================
     */
}
