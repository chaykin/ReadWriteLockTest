package ru.chaykin.store;

import ru.chaykin.lock.ILock;

public class Store {
    private final ILock lock;

    public Store(ILock lock) {
        this.lock = lock;
    }

    public void get() {
        lock.readLock(() -> {
            System.out.println("Start reading data on " + Thread.currentThread().getName());
            sleep(2000);
            System.out.println("End reading data on " + Thread.currentThread().getName());
        });
    }

    public void put() {
        lock.writeLock(() -> {
            System.out.println("Start writing data on " + Thread.currentThread().getName());
            sleep(5000);
            System.out.println("End writing data on " + Thread.currentThread().getName());
        });
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            //ignore
        }
    }
}
