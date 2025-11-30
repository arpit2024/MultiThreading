package Locks.ReEntrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
        private final Lock lock=new ReentrantLock();

        public void outerMethod(){
                lock.lock();
                try{
                        System.out.println("Outer Method");
                        innerMethod();
                }finally{
                        lock.unlock();
                }
        }

        public void innerMethod(){
                lock.lock();
                try{
                        System.out.println("Inner Method");
                }finally {
                        lock.unlock();
                }
        }

        public static void main(String[] args){
                ReentrantExample example=new ReentrantExample();
                example.outerMethod();
        }
}

/*
==============================================================
📝 Explanation — Why this does NOT cause deadlock
==============================================================
In this example, outerMethod() acquires the lock first and then
calls innerMethod(). Inside innerMethod(), we call lock() again
on the SAME lock object.

Logically, it looks like the thread is trying to acquire a lock
that it already holds, which normally should result in a deadlock
because the thread would wait for itself forever.

However, ReentrantLock is *re-entrant*, meaning:
------------------------------------------------
• The same thread can acquire the same lock multiple times.
• The lock internally maintains a hold count.
• Each successful lock() call increments the count.
• Each unlock() call decrements the count.
• The lock is fully released only when the count reaches zero.

Therefore, no deadlock occurs here because:
-------------------------------------------
The same thread is allowed to re-lock without blocking, and the
nested call to lock() succeeds immediately.

==============================================================
❗ Important Considerations
==============================================================
• If unlock() is NOT called the same number of times as lock(),
  the lock will NOT be released, causing a deadlock for other threads.

• Reentrant behavior works ONLY when the same thread calls lock()
  multiple times. If a different thread attempts lock() before all
  unlock() calls, it must wait.

==============================================================
🎯 Takeaway
==============================================================
ReentrantLock allows a thread to acquire the same lock more than once
while preventing self-deadlock. But we must always ensure proper
unlock() usage (ideally using try-finally) to avoid deadlock for
other threads.
*/


