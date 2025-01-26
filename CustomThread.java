import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomThread implements Runnable {
    private final int threadId;
    private final String timeCreated;

    public CustomThread(int threadId) {
        this.threadId = threadId;
        this.timeCreated = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public int getThreadId() {
        return threadId;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadId + " started at " + timeCreated);
        //TODO
        // Search search = new Search(min, max);
    }
}
