import java.util.Random;

public class Waiter implements Runnable {
    private final String name;
    private final KitchenCounter counter;
    private Random random = new Random();

    public Waiter(KitchenCounter counter, String name) {
        this.counter = counter;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            counter.put();
            IO.println("[" + name + "] put (load=" + counter.getLoad() + ")");
            try {
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {
            }
        }
    }
}
