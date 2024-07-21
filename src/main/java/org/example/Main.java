package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        BlockingQueue taskQueue = new BlockingQueue(10);

        //Эмуляция создания задач для пула потоков в бесконечном цикле
        Runnable taskProducer = () -> {
            try {
                for (;;) {
                    Task task = new Task();
                    taskQueue.enqueue(task);
                    System.out.println("Task enqueued");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        //Эмуляция передачи задач из очереди в бесконечном цикле
        Runnable taskConsumer = () -> {
            try {
                while (true) {
                    Task task = taskQueue.dequeue();
                    if (task == null) {
                        continue;
                    }
                    executorService.execute(task);
                    System.out.println("Task run");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread producerThread = new Thread(taskProducer);
        Thread consumerThread1 = new Thread(taskConsumer);
        Thread consumerThread2 = new Thread(taskConsumer);
        Thread consumerThread3 = new Thread(taskConsumer);

        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();
        consumerThread3.start();
    }
}