package Locks.usingLocks;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Using Manual Locks - A realtime Example
public class BankAccount {
        private int balance=100;
        //Lock is an Interface-> we will use the object of it, such as ReEntrantLock
        private final Lock lock = new ReentrantLock();

        public  void withdraw(int amount){
                System.out.println(Thread.currentThread().getName()+ " attempting to withdraw "+ amount);
                try {
                        /*Acquires the lock if it is free within the given waiting time and the current thread has not been interrupted.
                        If the lock is available this method returns immediately with the value true. If the lock is not available then the
                        current thread becomes disabled for thread scheduling purposes and lies dormant*/
                        if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                                if(balance>=amount) {
                                        try{
                                                System.out.println(Thread.currentThread().getName()+ " Proceeding with withdrawal");
                                                Thread.sleep(3000);
                                                balance-=amount;
                                                System.out.println(Thread.currentThread().getName()+" completed withdrawal.  Remaining balance:  "+ balance);
                                        }catch (Exception e){
                                                System.out.println(" Thread is interrupted during Inner sleep");
                                                Thread.currentThread().interrupt();//Reason for this call is explained in below catch block
                                        }
                                        finally {
                                                //Always unlock in finally as we release resources here
                                                lock.unlock();
                                        }
                                }
                                else{
                                        System.out.println(Thread.currentThread().getName()+" insufficient balance ");
                                }
                       }
                        else{
                                System.out.println(Thread.currentThread().getName()+" Could not acquire the Lock will try again Later.  ");
                        }
                } catch(Exception e){//since the thread is waiting here there is a possibility of interruption by other thread so need catch block
               /*
                             ⚠ Important Note: Never leave this catch block empty.

                             If a thread is interrupted while waiting for the lock or during sleep,
                             and we catch the exception without handling it properly, the interruption
                             signal will be lost.

                             Why is this bad?
                             ----------------
                             • Losing the interrupt signal may prevent graceful thread shutdown.
                             • The thread may continue running even though another part of the
                               application requested it to stop.
                             • Simply logging the exception is not enough — it is considered ignoring it.

                             Correct handling:
                             -----------------
                             • Either rethrow the InterruptedException, or
                             • Restore the interrupted status by calling Thread.currentThread().interrupt()
                               so that higher-level code can detect that this thread was interrupted.
                            */
                        Thread.currentThread().interrupt(); // Restores the interrupt status so it is not lost
                }

        }

        public static void main(String[] args){
                BankAccount bankAccount=new BankAccount();
                Runnable task=new Runnable() {
                        @Override
                        public void run() {
                                bankAccount.withdraw(50);
                        }
                };
                Thread t1=new Thread(task,"Thread 1");
                Thread t2=new Thread(task,"Thread 2");
                t1.start();
                t2.start();
        }
}
/*
O/P :-
Thread 2 attempting to withdraw 50
Thread 1 attempting to withdraw 50
Thread 2 Proceeding with withdrawal
Thread 1 Could not acquire the Lock will try again Later.
Thread 2 completed withdrawal.  Remaining balance:  50


==============================================================
🔐 Theory Notes — Why Use ReentrantLock Instead of synchronized
==============================================================

✔ What this program demonstrates
--------------------------------
Two threads (Thread 1 and Thread 2) attempt to withdraw money
from the same shared BankAccount object. To prevent race conditions,
a ReentrantLock is used so only one thread at a time can access
the withdrawal logic.

✔ Why this output happens
-------------------------
• The first thread that succeeds in acquiring the lock proceeds
  with the withdrawal.
• The second thread attempts to acquire the lock using tryLock()
  with a timeout.
• If lock is not available within the given time, it prints
  "Could not acquire the Lock" instead of blocking forever.
• This prevents unnecessary waiting and keeps thread execution efficient.

In this run:
  – Thread 2 acquired the lock first and completed withdrawal.
  – Thread 1 could not acquire the lock within 1 second and exited gracefully
    without blocking.

==============================================================
🔥 Why ReentrantLock is better here vs synchronized
==============================================================
Advantages of ReentrantLock demonstrated in this code:
------------------------------------------------------
1️⃣ tryLock() avoids waiting forever — thread can skip operation if lock unavailable
2️⃣ Allows timeout (1 second here) — synchronized has no timeout concept
3️⃣ Thread is NOT blocked indefinitely — avoids performance bottleneck
4️⃣ unlock() in finally ensures predictable lock release even during exceptions

This improves scalability and responsiveness in high-concurrency applications.

==============================================================
⚠ When NOT to use ReentrantLock
==============================================================
❌ ReentrantLock requires manual lock() and unlock() handling
   → If unlock() is forgotten, deadlock will occur.

❌ Slightly more complex than synchronized
   → Better suited for advanced concurrency needs.

❌ For simple mutual exclusion (single shared resource)
   synchronized is easier and safer to use.

==============================================================
📌 Final takeaway
==============================================================
Use synchronized when:
----------------------
✔ Simpler logic
✔ Only blocking mutual exclusion is needed

Use ReentrantLock when:
-----------------------
✔ You need tryLock() / timeout / interruptible locking
✔ You want more flexible control over thread access
✔ You want to avoid unnecessary blocking and improve performance

==============================================================
*/
