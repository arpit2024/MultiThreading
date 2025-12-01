package ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
    Earlier (without ExecutorService), we manually created 9 separate threads to compute factorials:
        - Created thread objects
        - Started each thread
        - Stored them in an array
        - Called join() one by one to make the main thread wait

    This is manual, error-prone and not scalable.

    ExecutorService simplifies multithreading by:
        ✓ Managing thread creation internally
        ✓ Reusing threads (instead of creating new ones repeatedly)
        ✓ Maintaining a queue of waiting tasks
        ✓ Allowing us to focus only on "tasks", not "threads"

    Key Interview Line:
        "ExecutorService decouples task submission from thread management."
*/
public class FactorialUsingExecutors {

        public static void main(String[] args) {
                long startTime = System.currentTimeMillis();

        /*
            Executors.newFixedThreadPool(3):
                - Creates a thread pool with exactly 3 worker threads.
                - At most 3 tasks will run in parallel at any time.
                - Remaining tasks are placed in an internal queue until a thread becomes free.

            Why a fixed thread pool?
                - Prevents creating an unlimited number of threads
                - Useful when the workload is predictable

            Real-world analogy:
                9 tasks arrive but only 3 workers are available.
                Workers pick tasks one by one until all tasks finish.
        */
                ExecutorService executor = Executors.newFixedThreadPool(3);

                for (int i = 1; i < 10; i++) {
                        int finalI = i; // Variable must be final/effectively final to be used inside lambda

            /*
                submit():
                    - Places the task into the executor's queue.
                    - Executor decides which thread will execute it.
                    - If all threads are busy, the task waits in the queue.

                IMPORTANT DIFFERENCE vs new Thread():
                    new Thread(task).start() → creates a NEW thread every time
                    executor.submit(task)     → reuses existing threads
            */
                        executor.submit(() -> {
                                long result = factorial(finalI);
                                System.out.println("Factorial of " + finalI + ": " + result);
                        });
                }

        /*
            shutdown():
                - Tells the executor to stop accepting new tasks.
                - Already submitted tasks will continue executing.
                - If shutdown() is called inside the loop, remaining submit() calls get rejected.

            shutdown() ≠ stop threads immediately
            shutdown() = allow running tasks to finish, but do not accept new tasks.
        */
                executor.shutdown();

        /*
            Why awaitTermination() is necessary?
                - shutdown() does NOT wait for tasks to finish.
                - The main thread would immediately continue and print total time (incorrect result).
                - awaitTermination() makes the main thread wait for all executor tasks to complete.

            awaitTermination(timeout, unit):
                - Waits until either:
                    a) All tasks finish, OR
                    b) Timeout occurs, OR
                    c) Current thread is interrupted

            If timeout is too short and tasks are still pending, awaitTermination returns false.
        */
                try {
                        executor.awaitTermination(100, TimeUnit.SECONDS);

            /*
            OPTIONAL (infinite wait) — use while loop if you want to wait until ALL tasks finish:

                while(!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.out.println("Waiting...");
                }
                Sample o/p if while loop used
                O/p-if we use while loop wait scenario:
                         2
                        1
                        6
                        Waiting...
                        24
                        120
                        720
                        Waiting...
                        Waiting...
                        5040
                        40320
                        362880

            Explanation:
                executor.awaitTermination(...) returns true ONLY when all tasks finish.
                Using ! (negation) keeps looping until that moment.
            */
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                }

                System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
        }

        private static long factorial(int n) {
                try {
                        // Sleep added to simulate a heavy computation (DB call, API call, report generation, etc.)
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

//commented this code to understand the difference of old code and implementing Executors
//                        threads[i - 1] = new Thread(() -> {
//
//                                long result = factorial(finalI);
//                                System.out.println("Factorial of " + finalI + ": " + result);
//                        });

//                        threads[i - 1].start(); // Launches the worker thread asynchronously
//                }
//                for (Thread thread : threads) {
//                        try {
//                                thread.join(); // Wait until this specific thread completes execution
//                        } catch (InterruptedException e) {
//                                Thread.currentThread().interrupt();
//                        }
//                }
