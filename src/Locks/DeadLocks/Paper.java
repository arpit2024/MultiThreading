package Locks.DeadLocks;

class Paper {
        public synchronized void writeWithPaperAndPen(Pen pen) {
                System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to use pen " + pen);
                pen.finishWriting();
        }

        public synchronized void finishWriting() {
                System.out.println(Thread.currentThread().getName() + " finished using paper " + this);
        }
}
