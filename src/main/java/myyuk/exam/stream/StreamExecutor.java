package myyuk.exam.stream;

import myyuk.exam.consumer.Consumer;
import myyuk.exam.producer.Producer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StreamExecutor<T> {

    private final ExecutorService executorService;

    private final Producer<T> producer;
    private final List<Consumer<T>> consumers;

    public StreamExecutor(int parallelism, Producer<T> producer, List<Consumer<T>> consumers) {
        this.executorService = Executors.newFixedThreadPool(parallelism + 1);
        this.producer = producer;
        this.consumers = consumers;
    }

    public void start() {
        for (Consumer<T> consumer : this.consumers) {
            this.executorService.submit(consumer);
        }
        this.executorService.submit(this.producer);
    }

    public void shutdown() {
        this.executorService.shutdown();

        // Add shutdown hook for abnormal terminated.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.executorService.shutdown();
            while (true) {
                try {
                    if (this.awaitTermination(5, TimeUnit.SECONDS)) {
                        break;
                    }
                } catch (InterruptedException e) {
                    this.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }));
    }

    private boolean awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return this.executorService.awaitTermination(timeout, timeUnit);
    }

    private void shutdownNow() {
        this.executorService.shutdownNow();
        // Force resource release.
        if (this.producer != null) {
            this.producer.close();
        }
        if (this.consumers != null) {
            for (Consumer<T> consumer : this.consumers) {
                consumer.close();
            }
        }
    }
}