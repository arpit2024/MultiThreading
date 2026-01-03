package ExecutorService.Future;

import java.util.concurrent.*;

public class FutureIntro {

        public static void main(String[] args) {
                ExecutorService executor = Executors.newFixedThreadPool(3);

                for (int i = 1; i < 10; i++) {
                        int finalI = i;

            /*
                execute() vs submit()  [VERY IMPORTANT INTERVIEW TOPIC]

                Executor  --> has only execute(Runnable)      --> returns void (fire-and-forget)
                ExecutorService --> adds submit(Callable/Runnable) --> returns Future<T>

                submit() advantage:
                    - Future object helps track the task
                    - We can query:
                        • whether the task is completed
                        • whether it failed
                        • retrieve return value (when using Callable)
                        • block and wait for completion using future.get()

                NOTE:
                In this example, we are using Runnable in submit(), so Future will not return a result value.
                Future<?> will simply hold the execution state (completed / failed / cancelled).
            */

                        Future<?> future = executor.submit(() -> {
                                long result = factorial(finalI);
                                System.out.println("Factorial of " + finalI + ": " + result);
                        });

            /*
                With this Future object, we can:
                    future.get()  → block & wait until the task finishes
                    future.isDone() → check if the task is finished without blocking
                    future.cancel(true) → try to stop execution

                But here we are not using future.get(), so execution remains asynchronous.
                (This will be improved in future examples when we use Callable.)
            */
                }

                // Stop accepting new tasks. Already submitted tasks will continue.
                executor.shutdown();
        }

        private static long factorial(int n) {
                try {
                        Thread.sleep(1000); // Simulating time-consuming operation
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
