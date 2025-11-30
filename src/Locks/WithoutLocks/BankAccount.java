package Locks.WithoutLocks;


//using Synchronized (WithOut Manual Locks)- A realtime Example
public class BankAccount {
        private int balance=100;

        public synchronized void withdraw(int amount){
                System.out.println(Thread.currentThread().getName()+ " attempting to withdraw "+ amount);
                if(balance>=amount){
                        System.out.println(Thread.currentThread().getName()+ " Proceeding with withdrawl");
                        try{
                                Thread.sleep(3000);
                        }
                        catch (InterruptedException e){

                        }
                        balance-=amount;
                        System.out.println(Thread.currentThread().getName()+" completed withdrawal.  Remaining balance:  "+ balance);
                }
                else{
                        System.out.println(Thread.currentThread().getName()+" insufficient balance ");
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
/*
📝 Output Explanation (Why this sequence occurs)

Thread 1 attempting to withdraw 50
Thread 1 proceeding with withdrawal
Thread 2 attempting to withdraw 50
Thread 2 proceeding with withdrawal
Thread 1 completed withdrawal. Remaining balance: 50
Thread 2 completed withdrawal. Remaining balance: 0

Explanation:
------------
• Both threads (Thread 1 and Thread 2) attempt to withdraw 50 from the same shared BankAccount.
• Because withdraw() is synchronized, only one thread can execute this method at a time (intrinsic lock).
• Thread 1 acquires the lock first → prints "attempting" and "proceeding", then sleeps for 3 seconds(might be
   more like 60 sec or whatever-Other thread need to wait indefinitely).
• After Thread 1 releases the lock, Thread 2 acquires it and continues with its own withdrawal.
• The operations happen one after another — preventing race conditions and maintaining correct balance.
• Final balance logic:
      100 - 50 = 50   (Thread 1)
       50 - 50 = 0   (Thread 2)

Key takeaway:
-------------
Synchronization ensures thread safety because shared data is accessed one at a time.

❗ Drawback:
-------------
Since only one thread can run the synchronized method at a time, other threads must wait unnecessarily —
 this blocking limitation is why more flexible mechanisms like ReentrantLock are sometimes preferred.
 */


