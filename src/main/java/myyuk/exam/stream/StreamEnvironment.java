package myyuk.exam.stream;

import myyuk.exam.producer.Producer;

/**
 * TODO:
 */
public class StreamEnvironment<T> {

    private final int parallelism;

    public static <T> StreamEnvironment<T> of(int parallelism) {
        return new StreamEnvironment<>(parallelism);
    }

    private StreamEnvironment(int parallelism) {
        this.parallelism = parallelism;
    }

    public Stream<T> addSource(Producer<T> producer) {
        producer.setTotalPartitionNumber(this.parallelism);
        return new Stream<>(this.parallelism, producer);
    }
}
