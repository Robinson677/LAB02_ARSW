package co.eci.snake.Partel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinder[] pft;
    private Pause control;

    private Control() {
        this.pft = new PrimeFinder[NTHREADS];
        this.control = new Pause();

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            pft[i] = new PrimeFinder(i * NDATA, (i + 1) * NDATA, control);
        }
        pft[i] = new PrimeFinder(i * NDATA, MAXVALUE + 1, control);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {

        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                Thread.sleep(TMILISECONDS);

                control.pause();

                System.out.println("\nPaused");
                System.out.println("Found primes: " + control.getCountPrimes());
                System.out.println("Press ENTER to continue...");
                br.readLine();

                control.resume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
