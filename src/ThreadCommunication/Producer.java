package ThreadCommunication;


// Producer continuously generates values and stores them in the shared resource.
// If buffer is full (hasData = true), producer waits until consumer consumes.
class Producer implements Runnable {
        private SharedResource sharedResource;

        public Producer(SharedResource sharedResource) {
                this.sharedResource = sharedResource;
        }

        @Override
        public void run() {
                for (int i = 0; i < 10; i++) {
                        sharedResource.produce(i);
                }
        }
}
