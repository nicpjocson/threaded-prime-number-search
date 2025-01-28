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
        System.out.println("Thread " + threadId + " is running.");
    }

    public void searchInRange(int min, int max) {
        for(int i = min; i <= max; i++) {
            if(isPrimeNumber(i)) {
                System.out.println(i);
            };
        }
    }

    /*
     *
     * prime number definition... :|
     * 
     * a whole number greater than 1 
     * that cannot be exactly divided 
     * by any whole number other than itself and 1
     * 
     */
    public boolean isPrimeNumber(int num) {
        // TODO
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
