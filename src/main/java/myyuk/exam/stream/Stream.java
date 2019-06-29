package myyuk.exam.stream;

import myyuk.exam.consumer.Consumer;
import myyuk.exam.producer.Producer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Stream<T> {

    private final ExecutorService executorService;

    private Producer<T> producer;
    private List<Consumer<T>> consumers;

    private Stream(int nThreads) {
        this.executorService = Executors.newFixedThreadPool(nThreads);
    }

    Stream(Producer<T> producer, List<Consumer<T>> consumers) {
        this(consumers.size() + 1); // consumers (N) + producer (1)
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

    public boolean awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return this.executorService.awaitTermination(timeout, timeUnit);
    }

    public void shutdownNow() {
        this.executorService.shutdownNow();
        // Force resource release.
        this.producer.close();
        for (Consumer<T> consumer : this.consumers) {
            consumer.close();
        }
    }
}
