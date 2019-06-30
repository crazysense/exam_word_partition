package myyuk.exam.stream;

import myyuk.exam.channel.Channel;
import myyuk.exam.channel.MemoryFifoChannel;
import myyuk.exam.consumer.Consumer;
import myyuk.exam.exception.StreamExecutionException;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.producer.Producer;
import myyuk.exam.selector.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Stream is inspired by other streaming engines.
 * Users can registering producer, consumer (and others) to Stream instead of create threads directly.
 */
public class Stream<T> {

    private final int parallelism;
    private final Producer<T> producer;

    private Channel<T> channel;
    private List<Consumer<T>> consumers;

    Stream(int parallelism, Producer<T> producer) {
        this.parallelism = parallelism;
        this.producer = producer;
    }

    /**
     * Add filter to stream.
     *
     * @param selector An instance of Selector.
     * @return The Stream.
     * @see Selector
     */
    public Stream<T> filter(Selector<T> selector) {
        this.check(this.producer != null
                && this.producer.getSelector() == null, "Empty producer!");
        this.producer.setSelector(selector);
        return this;
    }

    /**
     * Add partitioner to stream.
     *
     * @param partitioner An instance of Partitioner.
     * @return The Stream.
     * @see Partitioner
     */
    public Stream<T> keyBy(Partitioner<T> partitioner) {
        this.check(this.producer != null, "Empty producer!");
        this.producer.setPartitioner(partitioner);
        return this;
    }

    /**
     * Add channel to stream.
     *
     * @param channel An instance of Channel.
     * @return The Stream.
     * @see Channel
     */
    public Stream<T> addChannel(Channel<T> channel) {
        this.check(this.channel == null, "Channel already exist!");
        this.channel = channel;
        return this;
    }

    /**
     * Add consumer to stream.
     *
     * @param consumer An instance of Channel.
     * @return The StreamExecutor
     * @see Consumer
     * @see StreamExecutor
     */
    public StreamExecutor<T> addConsumer(Consumer consumer) {
        this.check(this.consumers == null
                || this.consumers.size() == 0, "Consumer already exist!");

        this.consumers = new ArrayList<>();
        for (int i = 0; i < this.parallelism; i++) {
            try {
                //noinspection unchecked
                Consumer<T> consumerCopy = consumer.clone();
                consumerCopy.setPartitionId(i);

                Channel<T> channelCopy = this.channel != null
                        ? this.channel.clone() : new MemoryFifoChannel<>();
                consumerCopy.setChannel(channelCopy);

                this.producer.addChannel(channelCopy);
                this.consumers.add(consumerCopy);
            } catch (CloneNotSupportedException e) {
                throw new StreamExecutionException("Cannot replicate channels and sinks.");
            }
        }
        return new StreamExecutor<>(this.parallelism, this.producer, this.consumers);
    }

    private void check(boolean condition, String error) throws StreamExecutionException {
        if (!condition) {
            throw new StreamExecutionException(error);
        }
    }
}
