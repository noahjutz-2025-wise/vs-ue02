public class Student {
    private final String name;
    private final KitchenCounter counter;

    public Student(KitchenCounter counter, String name) {
        this.counter = counter;
        this.name = name;
    }
}
