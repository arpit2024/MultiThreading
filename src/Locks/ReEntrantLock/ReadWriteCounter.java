package Locks.ReEntrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
Read Write Lock
A Read-Write Lock is a concurrency control mechanism that allows multiple threads to read shared data simultaneously while restricting write access to a single thread at a time.
 This lock type, provided by the ReentrantReadWriteLock class in Java, optimizes performance in scenarios with frequent read operations and infrequent writes. Multiple readers
  can acquire the read lock without blocking each other, but when a thread needs to write, it must acquire the write lock, ensuring exclusive access. This prevents data inconsistency
  while improving read efficiency compared to traditional locks, which block all access during write operations.
 */
public class ReadWriteCounter {

        private int count=0;

        private final ReadWriteLock lock=new ReentrantReadWriteLock();
        private final Lock readLock=lock.readLock();
        private final Lock writeLock=lock.writeLock();

        public void increment() {
                writeLock.lock();
                try {
                        count++;
                        Thread.sleep(50);
                } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                } finally {
                        writeLock.unlock();
                }
        }

        public int getCount() {
                readLock.lock();
                try {
                        return count;
                } finally {
                        readLock.unlock();
                }
        }

        public static void main(String[] args) throws InterruptedException{
                ReadWriteCounter counter=new ReadWriteCounter();

                Runnable readTask=new Runnable() {
                        @Override
                        public void run() {
                                for(int i=0;i<10;i++){
                                        System.out.println(Thread.currentThread().getName()+" read: "+counter.getCount());
                                }
                        }
                };

                Runnable writeTask=new Runnable() {
                        @Override
                        public void run() {
                                for(int i=0;i<10;i++){
                                        counter.increment();
                                        System.out.println(Thread.currentThread().getName()+" incremented");
                                }
                        }
                };

                Thread writerThread=new Thread(writeTask);
                Thread readerThread1=new Thread(readTask);
                Thread readerThread2=new Thread(readTask);

                writerThread.start();
                readerThread1.start();
                readerThread2.start();

                writerThread.join();;
                readerThread1.join();
                readerThread2.join();

                System.out.println("Final count: " + counter.getCount());
        }
}
/*
READ-WRITE LOCK / REENTRANTREADWRITELOCK
WHAT IS A READ-WRITE LOCK?

A Read-Write Lock allows:
• Multiple threads to read shared data at the same time
• Only one thread to write at a time (exclusive access)

This improves performance when read operations are frequent
and write operations are less frequent.

ReentrantReadWriteLock (from java.util.concurrent.locks) provides:
• readLock() → multiple readers allowed
• writeLock() → only one writer allowed

WHY IT IS BETTER THAN NORMAL LOCK

With a normal lock (or synchronized keyword):
• Both read and write operations block each other
• Even when many threads only need to read, they must wait

With ReadWriteLock:
• Multiple reads do NOT block each other
• Write requires exclusive lock to ensure data consistency

Result:
Better performance and higher throughput in read-heavy systems.

HOW IT WORKS

State of lock:
• When NO thread is writing → many threads can hold readLock
• When a thread wants to write → it waits until all readers release the lock
• While writing → no other reader or writer is allowed

REAL-LIFE ANALOGY

Imagine a library:
• Many members can read books at the same time (multiple readers)
• But only one librarian can update the catalog (single writer)
• While librarian is updating, no one can read the catalog to avoid inconsistency

ADVANTAGES

• Highly scalable in read-dominant systems
• Readers do not block each other
• Ensures data safety during write operations
• Produces better performance than traditional locks in many cases

DISADVANTAGES

• Slightly more complex than a normal lock
• If too many readers keep entering, a writer might starve
• Not ideal for write-heavy systems
• More overhead than a simple synchronized block

WHEN TO USE READ-WRITE LOCK

Use it when:
• Reads happen frequently and writes rarely
• Data changes occasionally but must always remain thread-safe
• Examples: cache, analytics dashboard, reporting system, product catalog, stock market price viewer

Do NOT use it when:
• Writes happen frequently
• Critical section is small and locking overhead matters more than concurrency
• Simpler locking (synchronized) is enough

EXAMPLE CODE (same as your implementation)

• readLock is used inside getCount()
• writeLock is used inside increment()
• Many reader threads can read simultaneously
• Writer thread waits until readers finish

EXPECTED OUTPUT PATTERN

• Reader threads print values frequently
• Writer thread increments count 10 times
• Readers might read same value multiple times (normal in concurrency)
• Final output shows the final count after all threads complete

INTERVIEW QUESTIONS AND ANSWERS

--- Basic Level ---
Q1) What is ReadWriteLock?
A) A lock that allows multiple threads to read simultaneously but allows only one thread to write at a time.

Q2) What are the two locks inside ReentrantReadWriteLock?
A) readLock() and writeLock().

Q3) Why do read operations not block each other?
A) Because readLock allows shared access for parallel reading.

--- Medium Level ---
Q4) Can a reader acquire the lock while a writer is active?
A) No. Writer has exclusive access.

Q5) Can a writer acquire the lock when readers are active?
A) No. Writer waits until all readers release the lock.

Q6) What problem might occur with too many readers?
A) Writer starvation — readers keep getting priority and writer waits too long.

--- Advanced Level ---
Q7) Is ReentrantReadWriteLock reentrant?
A) Yes. Same thread can acquire readLock or writeLock multiple times without deadlocking itself.

Q8) Can a lock be upgraded (read → write) automatically?
A) No. Upgrading directly from readLock to writeLock can cause deadlock if not planned. It is not supported automatically.

Q9) Can a lock be downgraded (write → read)?
A) Yes. After obtaining writeLock, a thread can acquire readLock before releasing writeLock.

Q10) Is ReadWriteLock always better than simple ReentrantLock?
A) No. It is beneficial only when reads are far more frequent than writes. Otherwise overhead reduces performance.

SUMMARY

• Multiple readers allowed → high concurrency
• Only one writer allowed → ensures correctness
• Great for read-heavy applications
• Not ideal when writes are frequent
• May cause writer starvation in some cases

ONE-LINE MEMORY TIP

"Many readers at once, but only one writer at a time."
 */