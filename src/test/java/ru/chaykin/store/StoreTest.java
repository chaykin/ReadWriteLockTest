package ru.chaykin.store;

import org.junit.jupiter.api.Test;
import ru.chaykin.lock.ILock;
import ru.chaykin.lock.ReentrantLock;
import ru.chaykin.lock.SemaphoreLock;

import java.util.stream.IntStream;

public class StoreTest {

    @Test
    public void semaphoreLockTest() {
        System.out.println("semaphoreLockTest started");
        storeTest(new SemaphoreLock());
        System.out.println("semaphoreLockTest ended\n");
    }

    @Test
    public void reentrantLockTest() {
        System.out.println("reentrantLockTest started");
        storeTest(new ReentrantLock());
        System.out.println("reentrantLockTest ended\n");
    }

    private void storeTest(ILock lock) {
        Store store = new Store(lock);

        IntStream.range(0, 10).forEach(i -> {
            runGetThread(store, i);
            sleep(200);
            if (i % 3 == 0) {
                runPutThread(store, i);
            }
        });

        sleep(45000);
    }

    private void runGetThread(Store store, int i) {
        new Thread(store::get).start();
    }

    private void runPutThread(Store store, int i) {
        new Thread(store::put).start();
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
