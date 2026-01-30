package co.eci.snake.Partel;

public class Pause {

    private boolean paused = false;
    private int CountPrimes = 0;


    public synchronized void checkPaused() {
        while(paused) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized int getCountPrimes() {
        return CountPrimes;
    }

    public synchronized void incrementCountPrimes() {
        CountPrimes++;
    }
}
