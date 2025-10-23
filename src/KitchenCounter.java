import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenCounter {
    private final int capacity;
    private int load;

    private final Lock loadLock = new ReentrantLock();
    private final Condition putCondition = loadLock.newCondition();
    private final Condition takeCondition = loadLock.newCondition();

    public KitchenCounter(int capacity) {
        this.capacity = capacity;
    }

    public void put() {
        loadLock.lock();
        while (load >= capacity) {
            try {
                putCondition.await();
            } catch (InterruptedException e) {
            }
        }
        load++;
        takeCondition.signal();
        loadLock.unlock();
    }

    public void take() {
        loadLock.lock();
        while (load <= 0) {
            try {
                takeCondition.await();
            } catch (InterruptedException e) {
            }
        }
        load--;
        putCondition.signal();
        loadLock.unlock();
    }

    public int getLoad() {
        return load;
    }
}
