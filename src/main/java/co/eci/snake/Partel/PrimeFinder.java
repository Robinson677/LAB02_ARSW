package co.eci.snake.Partel;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinder extends Thread {

    int a, b; 
    private List<Integer> primes;
    private Pause controlPause;

    public PrimeFinder(int a, int b, Pause controlPause) {
        super();
        this.a = a;
        this.b = b;
        this.controlPause = controlPause;
        this.primes = new LinkedList<>();
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {
            controlPause.checkPaused();
            if (isPrime(i)) {
                primes.add(i);
                controlPause.incrementCountPrimes();
                System.out.println(i);
            }
        }
    }

    boolean isPrime(int n) {
        boolean ans;

        if (n > 2) {
            ans = n%2 != 0;
            for (int i = 3; ans && i*i <= n; i+=2) {
                ans = n%i != 0;
            }
        } else {
            ans = n == 2;
        }
        return ans;
    }

    public List<Integer> getPrimes() {
        return primes;
    }
}
