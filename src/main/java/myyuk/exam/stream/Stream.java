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

public class Stream<T> {

    private final int parallelism;
    private final Producer<T> producer;

    private Channel<T> channel;
    private List<Consumer<T>> consumers;

    public Stream(int parallelism, Producer<T> producer) {
        this.parallelism = parallelism;
        this.producer = producer;
    }

    public Stream<T> addChannel(Channel<T> channel) {
        this.check(this.channel == null, "");
        this.channel = channel;
        return this;
    }

    public Stream<T> filter(Selector<T> selector) {
        this.check(this.producer != null && this.producer.getSelector() == null, "");
        this.producer.setSelector(selector);
        return this;
    }

    public Stream<T> keyBy(Partitioner<T> partitioner) {
        this.check(this.producer != null, "");
        this.producer.setPartitioner(partitioner);
        return this;
    }

    public StreamExecutor<T> addSink(Consumer consumer) {
        this.check(this.consumers == null || this.consumers.size() == 0, "");

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
                throw new StreamExecutionException("");
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
