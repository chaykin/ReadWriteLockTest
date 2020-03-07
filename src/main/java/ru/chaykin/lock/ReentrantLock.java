package ru.chaykin.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLock implements ILock {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void readLock(Runnable readAction) {
        try {
            lock.readLock().lock();
            readAction.run();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void writeLock(Runnable writeAction) {
        try {
            lock.writeLock().lock();
            writeAction.run();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
