package co.eci.snake.core;

public final class PauseController {
  private volatile boolean paused = false;
  private int totalRunners = 0;
  private int pausedCount = 0;

  public synchronized void setTotalRunners(int totalRunners) {
    this.totalRunners = totalRunners;
    this.notifyAll();
  }

  public synchronized void pause() {
    paused = true;
  }


  public synchronized void resume() {
    paused = false;
    this.notifyAll();
  }


  public void awaitIfPaused() throws InterruptedException {
    synchronized (this) {
      if (!paused) return;         
      pausedCount++;
      this.notifyAll();
      try {
        while (paused) {
          this.wait();
        }
      } finally {
        pausedCount--;
        this.notifyAll();
      }
    }
  }

  public synchronized void waitForAllPaused() throws InterruptedException {
    while (paused && pausedCount < totalRunners) {
      this.wait();
    }
  }


  public boolean isPaused() {
    return paused;
  }
}
