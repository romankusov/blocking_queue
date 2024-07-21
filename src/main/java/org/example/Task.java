package org.example;

public class Task implements Runnable{
    public void doSomeWork() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is doing some work");
    }

    @Override
    public void run() {
        doSomeWork();
    }
}
