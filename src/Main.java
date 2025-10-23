//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    final var counter = new KitchenCounter(4);
    for (int i = 0; i < 2; i++) {
        new Thread(new Waiter(counter, "Waiter-"+i)).start();
    }
    for (int i = 0; i < 8; i++) {
        new Thread(new Student(counter, "student-"+i)).start();
    }
}
