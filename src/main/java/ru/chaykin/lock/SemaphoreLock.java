package ru.chaykin.lock;

import java.util.concurrent.Semaphore;

public class SemaphoreLock implements ILock {
    private final Semaphore readLock = new Semaphore(Integer.MAX_VALUE);
    private final Semaphore writeLock = new Semaphore(1);

    @Override
    public void readLock(Runnable readAction) {
        try {
            writeLock.acquire();
            readLock.acquire();
        } catch (InterruptedException e) {
            readLock.release();
            return;
        } finally {
            writeLock.release();
        }

        try {
            readAction.run();
        } finally {
            readLock.release();
        }
    }

    @Override
    public void writeLock(Runnable writeAction) {
        try {
            writeLock.acquire();
            readLock.acquire(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            readLock.release(Integer.MAX_VALUE);
            writeLock.release();
            return;
        }

        try {
            writeAction.run();
        } finally {
            readLock.release(Integer.MAX_VALUE);
            writeLock.release();
        }
    }
}
