package ThreadCommunication;


// Consumer continuously removes values from shared resource.
// If buffer is empty (hasData = false), consumer waits until producer produces.
public class Consumer  implements  Runnable{
        private  SharedResource resource;

        public  Consumer(SharedResource resource){
                this.resource=resource;
        }

        @Override
        public void run() {
                for(int i=0;i<10;i++){
                        int value=resource.consume();
                }
        }
}
