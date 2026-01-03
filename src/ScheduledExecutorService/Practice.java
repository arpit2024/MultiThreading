package ScheduledExecutorService;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//if any works need to be done on delayed or on regular interval Basis
//Executor-> ExecutorService-> ScheduledExecutorService
public class Practice {
        public static void main(String[] args) {

                ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);
                scheduler.schedule(
                        ()-> System.out.println("Task Executed after 5 Sec Delay ")
                        ,5,
                        TimeUnit.SECONDS
                );//with runnable

                //when runned this didn't print anything because for the first one i.e for a scheduler ,task goes in to queue immediately and scheduler waits till its completion
                scheduler.scheduleAtFixedRate(
                        ()-> System.out.println("Task Executed after every 5 seconds Delay "),
                        5
                        ,5,
                        TimeUnit.SECONDS
                );
                // scheduler.shutdown();
                // but for the fixed rate it does periodic task since we call shutdown immediately there might be no time to get task in  to queue
                //so shut down wont be knowing about the initial delay of 5 seconds and all,so perform one more task for shut down

                scheduler.schedule(()->{
                        System.out.println("Intiating Scheduler Shutdown...");
                        scheduler.shutdown();
                },20,TimeUnit.SECONDS);
                //so basically this task has a scheduler where it prints/does task every 5 seconds and there is one more scheduler which will shutdown after 20 seconds

/*
                *Op/:-
                *Task Executed after 5 Sec Delay //example 1 op
Task Executed after every 5 seconds Delay
Task Executed after every 5 seconds Delay
Task Executed after every 5 seconds Delay
Task Executed after every 5 seconds Delay
Intiating Scheduler Shutdown...*/

/*
 This is a ScheduledExecutorService.
 Here, only ONE thread is used to execute scheduled tasks.

 We are using scheduling methods provided by ScheduledExecutorService.
 Apart from schedule(), there are two periodic scheduling methods:

 1) scheduleAtFixedRate()
    - Tasks are scheduled at a fixed time interval.
    - If a task takes longer than the period,
      the next execution may start immediately after the previous one finishes.
    - This can give the impression of "overlapping" in multi-threaded schedulers.

 2) scheduleWithFixedDelay()
    - A fixed delay is applied AFTER one task completes
      before starting the next execution.
    - No overlap occurs between executions.

 These methods return ScheduledFuture,
 which extends Future and provides additional scheduling-related functionality.
*/


        }
}


/*
=====================================================
SCHEDULEDEXECUTORSERVICE – IMPORTANT NOTES
=====================================================

ScheduledExecutorService is used for delayed and periodic task execution.

-----------------------------------------------------
1) schedule()
-----------------------------------------------------
- Executes a task once after a given delay.
- Returns ScheduledFuture.

Example:
scheduler.schedule(task, 5, TimeUnit.SECONDS);

-----------------------------------------------------
2) scheduleAtFixedRate()
-----------------------------------------------------
- Executes a task periodically at a FIXED RATE.
- Next execution time is calculated from the initial start time.
- If task execution is slow:
    → Next execution may start immediately after previous completes.
- Suitable for:
    - Heartbeats
    - Monitoring tasks
    - Polling at fixed intervals

Example:
scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);

-----------------------------------------------------
3) scheduleWithFixedDelay()
-----------------------------------------------------
- Executes a task periodically with a FIXED DELAY.
- Delay is applied AFTER task completion.
- No execution overlap.
- Suitable for:
    - Cleanup jobs
    - Background maintenance tasks

Example:
scheduler.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);

-----------------------------------------------------
ScheduledFuture
-----------------------------------------------------
- Returned by all scheduling methods.
- Extends Future.
- Supports:
    - cancel()
    - isDone()
    - getDelay()
    - get()

-----------------------------------------------------
Key Difference (Interview Favorite)
-----------------------------------------------------
scheduleAtFixedRate:
- Period is based on start time.

scheduleWithFixedDelay:
- Delay is based on completion time.
=====================================================

🔥 Interview One-Liner
scheduleAtFixedRate() schedules tasks based on start time, while scheduleWithFixedDelay() schedules tasks based on completion time, preventing back-to-back executions.

⚠️ Important Correction to Your Original Thought
❌ “fixedRate has overlapping issue”
✅ Correct explanation:
Overlap happens only if multiple threads exist
With a single-thread scheduler, tasks never truly overlap
They may execute back-to-back if delayed
*/
