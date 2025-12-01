package ExecutorService;

public class FactorialExample {

        // Code without using Threads (sequential execution)
        // In this approach, factorial for numbers 0–9 is calculated one by one on the main thread.
        // Hence, total execution time = sum of time taken for all factorial calculations.
        //
//    public static void main(String[] args){
//        long startTime = System.currentTimeMillis();
//        for(int i = 0; i < 10; i++){
//            System.out.println(factorial(i));
//        }
//        // This prints total time after the loop completes
//        System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
//    }

        public static void main(String[] args){
                long startTime = System.currentTimeMillis();

                for(int i = 0; i < 10; i++){
                        int finalI = i; // i must be final or effectively final to be used inside lambda

                        Thread thread = new Thread(() -> {
                /*
                   Why variable must be final/effectively final in lambda:
                   - The thread may start later (asynchronously).
                   - If 'i' keeps changing in the loop, the thread might capture the wrong value.
                   - Making it final/effectively final ensures every thread works with the correct value
                     that existed at the moment of thread creation.
                 */
                                long result = factorial(finalI);
                                System.out.println(result);
                        });

                        thread.start(); // starting thread — execution begins independently of the main thread
                }

        /*
            ❗ Problem:
            - The following print executes in the main thread immediately after starting all threads.
            - The main thread does NOT wait for the worker threads to complete.
            - So it prints time before factorial threads finish → Incorrect timing in real scenarios.
            - If the next line depended on results from threads, it would produce wrong behavior.
            - We need thread join() or ExecutorService to ensure the main thread waits for completion.
        */
                System.out.println("Total time: " + (System.currentTimeMillis() - startTime));

        /*
            Example Output (shows why this is wrong)
            Total time: 11     ← printed instantly (main thread never waited)
            1
            6
            2
            1
            120
            24
            362880
            40320
            5040
            720
         */
        }

        private static long factorial(int n){
                try {
                        // Adding delay to simulate time-consuming calculation (e.g., DB call, API call, etc.)
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }

                long result = 1;
                for(int i = 1; i <= n; i++){
                        result *= i;
                }
                return result;
        }
}
