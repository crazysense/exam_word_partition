package myyuk.exam.stream;

import myyuk.exam.consumer.Consumer;
import myyuk.exam.producer.Producer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * StreamExecutor executes the stream.
 *
 * When start a stream through 'start' method, the registered Producer and Consumers run in the thread pool.
 * If want to register Shutdown-Hook for graceful shutdown, just calling 'shutdown' method.
 */
public class StreamExecutor<T> {

    private final ExecutorService executorService;

    private final Producer<T> producer;
    private final List<Consumer<T>> consumers;

    StreamExecutor(int parallelism, Producer<T> producer, List<Consumer<T>> consumers) {
        this.executorService = Executors.newFixedThreadPool(parallelism + 1);
        this.producer = producer;
        this.consumers = consumers;
    }

    /**
     * Start the stream.
     */
    public void start() {
        for (Consumer<T> consumer : this.consumers) {
            this.executorService.submit(consumer);
        }
        this.executorService.submit(this.producer);
        // shutdown.
        this.executorService.shutdown();
    }

    /**
     * Register the shutdown hook.
     */
    public void shutdown() {
        // Add shutdown hook for abnormal terminated.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.executorService.shutdown();
            while (true) {
                try {
                    if (this.executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                        break;
                    }
                } catch (InterruptedException e) {
                    this.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }));
    }

    /**
     * Stop the stream immediately.
     */
    public void shutdownNow() {
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