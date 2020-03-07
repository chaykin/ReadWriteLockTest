package ru.chaykin.lock;

public interface ILock {
    void readLock(Runnable readAction);
    void writeLock(Runnable writeAction);
}
