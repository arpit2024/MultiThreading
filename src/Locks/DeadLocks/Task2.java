package Locks.DeadLocks;

class Task2 implements Runnable {
        private Pen pen;
        private Paper paper;

        public Task2(Pen pen, Paper paper) {
                this.pen = pen;
                this.paper = paper;
        }

        @Override
        public void run() {
                paper.writeWithPaperAndPen(pen); // thread2 locks paper and tries to lock pen

                //uncomment this to remove resolve deadlock issue
//                synchronized (pen){
//                        paper.writeWithPaperAndPen(pen); // thread2 locks paper and tries to lock pen
//                }
        }
}
