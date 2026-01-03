package ExecutorService.Future;

import java.util.*;
import java.util.concurrent.*;

public class InvokeAllExample {

        public static void main(String[] args)
                throws InterruptedException, ExecutionException {

                ExecutorService executorService = Executors.newFixedThreadPool(3);
//                Callable<Integer> callable=()->1;
//                Callable<Integer> callable2=()->2;
//                Callable<Integer> callable3=()->3;
//
//                List<Callable<Integer>> list=Arrays.asList(callable,callable2,callable3);
//                executorService.invokeAll(list);//it will start all
                //or can also write like below

                // Create multiple Callable tasks
                List<Callable<String>> tasks = Arrays.asList(
                        () -> {
                                Thread.sleep(1000);
                                return "Task 1 completed";
                        },
                        () -> {
                                Thread.sleep(2000);
                                return "Task 2 completed";
                        },
                        () -> {
                                Thread.sleep(1500);
                                return "Task 3 completed";
                        }
                );

                // invokeAll blocks until ALL tasks are completed
                List<Future<String>> futures = executorService.invokeAll(tasks);

                // Fetch results
                for (Future<String> future : futures) {
                        System.out.println(future.get());
                }
                System.out.println("Hello world");//will execute only once all task are completed

                executorService.invokeAll(tasks,1,TimeUnit.SECONDS);//so whatever tasks completed within 1 second will complete the other wont
                //executorService.invokeAny()/Executes the given tasks, returning the result of one that has completed successfully
               // executorService.invokeAll(Collection<? extends Callable<T>> tasks)//Executes the given tasks, returning a list of Futures holding their status and results when all complete or the timeout expires,
                // whichever happens first. Future.isDone is true for each element of the returned list.

                //Also we have future.get(timeout, time val in sec /min) where if the task doesnt complete in given time it will throw exception
                //future.cancel()-> if the taks need to be cancelled forcefully can be done and can be checked using future.isCancelled()

                executorService.shutdown();
        }
}
/*
=====================================================
EXECUTORSERVICE.invokeAll() – COMPLETE NOTES
=====================================================

Method signature:
<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;

What invokeAll() does:
- Submits ALL given Callable tasks at once.
- BLOCKS the calling thread until ALL tasks finish.
- Returns a List of Future<T>, one for each task.
- Order of Futures = order of Callables in the collection.

Important points:
- invokeAll() works ONLY with Callable (not Runnable).
- If a task throws an exception:
    → its corresponding Future.get() throws ExecutionException.
- If the thread is interrupted:
    → invokeAll() throws InterruptedException.

When to use:
- When you need ALL results before proceeding.
- Batch processing
- Parallel computations
- Fan-out / fan-in pattern

// Total execution time ≈ max(task execution time)
// NOT sum of all times

With 3 threads:
Task1 → 1 sec
Task2 → 2 sec
Task3 → 1.5 sec
👉 Total ≈ 2 seconds
=====================================================


// ❌ invokeAll with Runnable - NOT allowed
List<Runnable> tasks = ...;
executorService.invokeAll(tasks); // Compilation error

Interview One-Liners

invokeAll() submits multiple Callable tasks and blocks until all of them finish, returning a list of Futures in the same order as submission.
Use submit() for independent tasks, invokeAll() when all results are required before continuing.
*/
