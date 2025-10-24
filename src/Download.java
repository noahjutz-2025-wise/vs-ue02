import javax.swing.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Download implements Runnable {
    private final Random random = new Random();
    private final JProgressBar jProgressBar;
    private final CountDownLatch startLatch;
    private final CountDownLatch stopLatch;

    public Download(JProgressBar jProgressBar, CountDownLatch startLatch, CountDownLatch stopLatch) {
        this.jProgressBar = jProgressBar;
        this.startLatch = startLatch;
        this.stopLatch = stopLatch;
    }

    @Override
    public void run() {
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i <= 100; i++) {
            jProgressBar.setValue(i);
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
            }
        }
        stopLatch.countDown();
    }
}
