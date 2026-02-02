package co.eci.snake.core;

import java.util.ArrayDeque;
import java.util.Deque;

public final class Snake {
  private final Deque<Position> body = new ArrayDeque<>();
  private volatile Direction direction;
  private int maxLength = 5;
  private volatile boolean alive = true;
  private volatile long deathTime = 0L;

  private Snake(Position start, Direction dir) {
    body.addFirst(start);
    this.direction = dir;
  }

  public static Snake of(int x, int y, Direction dir) {
    return new Snake(new Position(x, y), dir);
  }

  public Direction direction() { return direction; }

  public void turn(Direction dir) {
    if ((direction == Direction.UP && dir == Direction.DOWN) ||
        (direction == Direction.DOWN && dir == Direction.UP) ||
        (direction == Direction.LEFT && dir == Direction.RIGHT) ||
        (direction == Direction.RIGHT && dir == Direction.LEFT)) {
      return;
    }
    this.direction = dir;
  }

  public Position head() { synchronized (body) { return body.peekFirst(); } }

  public Deque<Position> snapshot() { synchronized (body) { return new ArrayDeque<>(body); } }

  public boolean isAlive() { return alive; }

  public long getDeathTime() { return deathTime; }

  public void advance(Position newHead, boolean grow) {
    synchronized (body) {
      if (!alive) return; 
      boolean selfCollision = body.contains(newHead);
      body.addFirst(newHead);
      if (grow) maxLength++;
      while (body.size() > maxLength) body.removeLast();
      if (selfCollision && alive) {
        alive = false;
        deathTime = System.currentTimeMillis();
      }
    }
  }
}
