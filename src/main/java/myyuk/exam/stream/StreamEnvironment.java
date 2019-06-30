package myyuk.exam.stream;

import myyuk.exam.producer.Producer;

/**
 * StreamEnvironment is the environment for creating streams.
 * The number of parallel partitions must be specified.
 * <p>
 * Users can create a stream by registering Producer in the StreamEnvironment.
 */
public class StreamEnvironment<T> {

    private final int parallelism;

    private StreamEnvironment(int parallelism) {
        this.parallelism = parallelism;
    }

    /**
     * Create new instance of StreamEnvironment.
     *
     * @param parallelism The number of parallel partitions.
     * @return An instance of StreamEnvironment.
     */
    public static <T> StreamEnvironment<T> of(int parallelism) {
        return new StreamEnvironment<>(parallelism);
    }

    /**
     * Register the Producer to generate a stream.
     *
     * @param producer An instance of Producer.
     * @return A generated stream.
     * @see Producer
     * @see Stream
     */
    public Stream<T> addProducer(Producer<T> producer) {
        producer.setTotalPartitionNumber(this.parallelism);
        return new Stream<>(this.parallelism, producer);
    }
}
