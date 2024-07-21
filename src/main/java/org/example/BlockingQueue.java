package org.example;

public class BlockingQueue {
    private final Task [] queue;
    private int head;
    private int tail;
    private int count;

    public BlockingQueue(int size) {
        queue = new Task[size];
        head = 0;
        tail = 0;
        count = 0;
    }

    public void enqueue(Task task) throws InterruptedException {
        synchronized (this) {
            while (count == queue.length) {
                wait();
            }
            queue[tail] = task;
            tail = (tail + 1) % queue.length;
            count++;
            notify();
        }
    }

    public Task dequeue() throws InterruptedException {
        synchronized (this) {
            while (count == 0) {
                wait();
            }
            Task task = queue[0];
            queue[0] = null;
            head = (head + 1) % queue.length;
            count--;
            notify();
            return task;
        }
    }

    public synchronized int size() {
        return count;
    }
}
