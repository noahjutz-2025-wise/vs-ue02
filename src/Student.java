public class Student implements Runnable {
    private final String name;
    private final KitchenCounter counter;

    public Student(KitchenCounter counter, String name) {
        this.counter = counter;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            counter.take();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
