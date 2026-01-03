package ExecutorService.Future;

import java.util.concurrent.*;

public class PracticeFuture2 {
        public static void main(String[] args) throws ExecutionException, InterruptedException {
                ExecutorService executorService= Executors.newSingleThreadExecutor();

                // 1) Callable returning a value → works
                Future<String> future = executorService.submit(() -> "Hello");

                // 2) Runnable → works (no return value)
                Runnable runnable1 = () -> System.out.println("Hello");

                // ❌ NOT allowed: Runnable cannot return a value
                //Runnable runnable2 = () -> "Hello";
                // Reason: Runnable.run() has void return type
                // .submit i see 3 methods, runnable, callable and the other runnable type
                // if you check callable it has callable and will return something

                // 3) Callable explicitly defined → works
                //we can do below to solve the above issue
                Callable<String> callable=()->"Hello";
                Future<String> callfuture=executorService.submit(callable);
                //so previously for the line 10 by default it was using callable

         //now this wont work ,showing error because automatically runnable method is used
                //Future<String> callfuture2=executorService.submit(()-> System.out.println("Hello"));
        //but a question arises, even this method is returning something then why the error
                /*
                ❌ This will NOT compile
                submit(Runnable) returns Future<?>, NOT Future<String>
                Future<String> callFuture2 = executorService.submit(
                             () -> System.out.println("Hello")
                );
                */

                // ✅ Correct way: capture Runnable submission using Future<?>
                //so basically this variable future will have the status of the task
                Future<?> callFuture3 =  executorService.submit(() -> System.out.println("Hello"));

                // get() works but returns null for Runnable / //get wont work here , because if you try to run get then callable will be called
                System.out.println(callFuture3.get()); // prints: null

                // Another example of submit(Runnable, result)
                Future<?> runnableSubmit =
                        executorService.submit(() -> System.out.println("Hello"), "Success");
/*
Another overload of submit() – submit(Runnable task, T result)

- This method submits a Runnable for execution.
- Runnable itself does NOT return any value.
- However, submit() allows you to explicitly provide a result value.

Method signature:
<T> Future<T> submit(Runnable task, T result);

How it works:
- Runnable executes normally (prints "Hello").
- After successful completion of the Runnable,
  the provided result ("Success") is stored in the Future.
- Future.get() will return the given result.

Important points:
- The result is NOT returned by Runnable.
- The result is supplied manually to submit().
- If Runnable throws an exception, Future.get() will throw ExecutionException.

Example:
Future<String> f =
        executorService.submit(() -> System.out.println("Hello"), "Success");

System.out.println(f.get());  // Output: Success
*/
                executorService.shutdown();

        }
}


/*
==============================
RUNNABLE vs CALLABLE – IMPORTANT NOTES
==============================

        1) Runnable
-----------
        - Functional method: run()
- Method signature:
void run();
- Does NOT return any value
- Does NOT allow checked exceptions in method signature
- If submit(Runnable) is used:
        - submit() returns Future<?>
    - Future.get() returns null

Example:
Runnable r = () -> System.out.println("Hello");
Future<?> f = executorService.submit(r);


2) Callable
-----------
        - Functional method: call()
- Method signature:
V call() throws Exception;
- CAN return a value
- CAN throw checked exceptions
- If submit(Callable<T>) is used:
        - submit() returns Future<T>
    - Future.get() returns the computed value

Example:
Callable<String> c = () -> "Hello";
Future<String> f = executorService.submit(c);


3) Why Runnable cannot return a value?
        --------------------------------------
        - Runnable.run() has return type void
- Lambda must match the functional interface exactly
- Therefore:
        () -> "Hello" ❌ invalid for Runnable
        () -> System.out.println("Hello") ✅ valid


4) Why Future<String> fails with Runnable?
        ------------------------------------------
submit(Runnable) returns:
Future<?>

NOT:
Future<String>

Because Runnable produces no result.


5) Checked Exceptions Difference
--------------------------------
Runnable:
        - Cannot declare checked exceptions
- Must handle them using try-catch inside run()

Callable:
        - Can declare checked exceptions
- No need for try-catch inside call()


6) Summary (Interview-ready)
----------------------------
        - Use Runnable → when no result is required
- Use Callable → when a result or checked exception handling is required
- submit(Runnable) → Future<?> → get() returns null
        - submit(Callable<T>) → Future<T> → get() returns T

==============================
        */

