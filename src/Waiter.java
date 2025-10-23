public class Waiter implements Runnable {
    private final String name;
    private final KitchenCounter counter;

    public Waiter(KitchenCounter counter, String name) {
        this.counter = counter;
        this.name = name;
    }

    @Override
    public void run() {
        // TODO
    }
}
