package org.example.exerciseTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue2 {

    private final List<String> messages = new ArrayList<>();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public void put(String message) {
        lock.lock();
        try {
            messages.add(message);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (messages.isEmpty()) {
                notEmpty.await();
            }
            return messages.remove(0);
        } finally {
            lock.unlock();
        }
    }
}
