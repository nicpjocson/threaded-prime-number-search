public class CustomThread extends Thread {
    private int threadId;

    public CustomThread(int threadId) {
        this.threadId = threadId;
    }

    public int getThreadId() {
        return threadId;
    }

    @Override
    public void run() {
        System.out.println("Thread ID: " + threadId + " is running.");
    }

    public void searchInRange(int min, int max) {
        for(int i = min; i <= max; i++) {
            // TODO: search
        }
    }

    public void testDivisibility(int num) {
        // TODO
    }
}
