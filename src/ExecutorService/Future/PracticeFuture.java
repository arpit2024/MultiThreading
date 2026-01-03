package ExecutorService.Future;

import java.util.concurrent.*;

//2:57:19
public class PracticeFuture {
        // Part 1- Method
        public static void main(String[] args) throws ExecutionException, InterruptedException {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(() -> {
                        return 42;
                });//can also be written in short
                executorService.submit(() -> 42); //just return 42 ,

                // // Here we capture the Future returned by submit()
                Future<Integer> future = executorService.submit(() -> 42);//so we kept 42the integer value in furture

                //Above line can also be written in general
                Future<?> future1 = executorService.submit(() -> System.out.println("Hello"));//?-> can be anything

                // isDone() checks whether the task has completed
                if (future.isDone()) {
                        //isDone-> Returns true if this task completed. Completion may be due to normal termination, an exception,
                        // or cancellation -- in all of these cases, this method will return true.

                        System.out.println("Task is done");// This usually WON'T print here because the task may not be finished yet
                }

                System.out.println(future.get());//get() blocks the current thread until the task completes then prints the value
                //suppose if i put the isDone() check here , (After get()) then it will be printed here, because get() waits until completion

                executorService.shutdown();
                // Output:
                // Hello
                // 42
        }
}
/*
==============================
IMPORTANT CONCEPTUAL NOTES – FUTURE & EXECUTOR
==============================

1) submit() DOES NOT execute immediately in the current thread
-------------------------------------------------------------
Future<Integer> future = executorService.submit(() -> 42);

- The task is submitted for asynchronous execution.
- The value (42) is NOT stored in Future immediately.
- Future is only a HANDLE to the result.
- Actual execution happens in a separate thread managed by ExecutorService.
- Result becomes available only AFTER task execution completes.


2) isDone() DOES NOT wait (Non-blocking check)
----------------------------------------------
future.isDone();

- Returns true ONLY if the task has completed.
- Completion includes:
  a) Normal completion
  b) Exception thrown
  c) Task cancellation
- isDone() does NOT block the current thread.
- That’s why isDone() often returns false when checked immediately.


3) get() ALWAYS waits (Blocking call)
-------------------------------------
future.get();

- Blocks the calling thread until task completes.
- Possible outcomes:
  a) Returns result if task completes normally
  b) Throws ExecutionException if task fails
  c) Throws InterruptedException if thread is interrupted
- After get() completes, isDone() will ALWAYS return true.


4) Runnable vs Callable with submit()
-------------------------------------
executorService.submit(() -> System.out.println("Hello"));

- Runnable:
  - Does NOT return a value
  - Future.get() returns null

- Callable<T>:
  - Returns a value of type T
  - Future.get() returns the computed result


5) Execution order with single-thread executor
-----------------------------------------------
Executors.newSingleThreadExecutor();

- Tasks execute sequentially (one after another).
- Order of execution = order of submission.
- No parallel execution happens.

==============================
INTERVIEW ONE-LINER
==============================
Future represents the result of an asynchronous computation.
isDone() checks completion without blocking,
get() blocks until the task completes and returns the result.

==============================
*/


