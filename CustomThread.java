public class CustomThread implements Runnable {
    private final int threadId;

    public CustomThread(int threadId) {
        this.threadId = threadId;
    }

    public int getThreadId() {
        return threadId;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadId + "created.");
        //TODO
        // Search search = new Search(min, max);
    }
}
