package ExecutorService;

public class FactorialWithThread {

        public static void main(String[] args) {
                long startTime = System.currentTimeMillis();
        /*    Here we create an array of 9 threads because we will calculate factorial
                for values 1 to 9. Keeping track of thread references helps us call join()
                 later to make the main thread wait for their completion.  */
                Thread[] threads = new Thread[9];

                for(int i = 1; i < 10; i++){
                        int finalI = i;  // Must be final/effectively final to be used in lambda

                        threads[i - 1] = new Thread(() -> {
                /*     Each thread independently computes factorial(finalI).
                         All these threads run concurrently (in parallel based on CPU availability).    */
                                long result = factorial(finalI);
                                System.out.println("Factorial of " + finalI + ": " + result);
                        });

                        threads[i - 1].start(); // Launches the worker thread asynchronously
                }
       /*
                IMPORTANT CONCEPT (Interview point):
                    The main thread must wait for all child threads to complete.
                    Otherwise, the program may reach the last line (printing total time)
                    before child threads finish → resulting in incorrect metrics/results.

                    join():
                        - Causes the calling thread (main thread) to wait until the target thread finishes.
                        - Mandatory in multithreaded programs when results of threads are required later.
         */
                for (Thread thread : threads) {
                        try {
                                thread.join(); // Wait until this specific thread completes execution
                        } catch (InterruptedException e) {
           /* Restoring interrupt flag is good practice instead of swallowing the exception.
               This is because InterruptedException indicates the thread was asked to stop.  */
                                Thread.currentThread().interrupt();
                        }
                }
                System.out.println("Total time: " + (System.currentTimeMillis() - startTime));

        /*
            Before multithreading:
                Total time = Sum of execution time for all factorial calculations
                            = 9 × 1000 ms = ~9000 ms (9 seconds)

            After multithreading:
                Total time ≈ 1000 ms (~1 second)
                Because all threads run concurrently and complete around the same time.

            ❗ Real-world takeaway:
               Parallel threads drastically reduce execution time when tasks are independent.
         */
        }

        private static long factorial(int n) {
                try {
            /*
                Thread.sleep() is added only to simulate time-consuming computation
                like DB call, API call, report generation, large mathematical computation etc.
             */
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }

                long result = 1;
                for (int i = 1; i <= n; i++){
                        result *= i;
                }
                return result;
        }
}



/*
------------------------------------------------------------
WHEN Thread[] APPROACH BECOMES PROBLEMATIC
------------------------------------------------------------

Limitation                          Explanation
------------------------------------------------------------
Hard to manage many threads         Creates too many OS threads → performance drops
Manual lifecycle management         You must manually create, start, join each thread
No return values                    Cannot directly get result from thread execution
Not scalable                        For 1000 tasks → creating 1000 threads is inefficient

Real-world note:
Thread[] is useful only for learning or very small use cases.
In industry projects, ExecutorService or ThreadPool is preferred.


------------------------------------------------------------
WHY DO WE USE (i - 1) IN threads[i - 1]
        ------------------------------------------------------------

The 'i' loop runs from 1 to 9:
Iteration       i
1               1
        2               2
        3               3
        ...
        9               9

But arrays use 0-based indexing:
Array index     Position
0               1st thread
1               2nd thread
2               3rd thread
...
        8               9th thread

So mapping values:
i       Correct array index
1       0  → i - 1
        2       1  → i - 1
        3       2  → i - 1
        ...
        9       8  → i - 1

Summary:
i - 1 converts factorial range (1–9) into thread array index range (0–8).

If we used threads[i] instead of threads[i - 1]:
When i = 9 → threads[9] → ArrayIndexOutOfBoundsException
Because last valid index is 8.


        ------------------------------------------------------------
WHY STORE THREADS IN AN ARRAY
------------------------------------------------------------

We could start threads like:
        new Thread(() -> factorial(finalI)).start();

BUT:
This loses the reference of the thread object.
If we lose the reference → we cannot call join() later → main thread cannot wait.

Correct approach:
Store threads in array → call join() later:

        for (Thread thread : threads) {
        thread.join(); // wait for completion
}

If references are not stored → main thread continues without waiting → incorrect results.


        ------------------------------------------------------------
WHY TWO SEPARATE LINES?
        ------------------------------------------------------------

threads[i - 1] = new Thread(...);   // Thread object created and stored
threads[i - 1].start();             // Thread execution begins

Reason:
We first store the thread reference, then start execution.
If we combine into one step:

        new Thread(() -> { ... }).start();

Thread starts immediately but reference is lost → cannot join later.


------------------------------------------------------------
WHY EXECUTORSERVICE IS PREFERRED IN PRACTICE
------------------------------------------------------------

Thread[] vs ExecutorService:

Feature                         Thread[]     ExecutorService
Automatic thread reuse          No           Yes
Scalable for many tasks         No           Yes
Handles return values           No           Yes (Future.get())
Task scheduling                 No           Yes
Recommended for production      No           Yes

Interview memory line:
Thread = low-level multithreading (manual work)
ExecutorService = high-level thread management (automatic, scalable)

*/
