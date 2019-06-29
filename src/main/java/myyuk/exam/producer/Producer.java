package myyuk.exam.producer;

import myyuk.exam.channel.Channel;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.selector.Selector;
import myyuk.exam.stream.DataWrapper;

import java.util.List;

public abstract class Producer<T> implements Runnable {

    private List<Channel<T>> channels;
    private Partitioner<T> partitioner;
    private Selector<T> selector;
    private int totalPartitionNumber;

    public void setChannels(List<Channel<T>> channels) {
        this.channels = channels;
    }

    public void setPartitioner(Partitioner<T> partitioner) {
        this.partitioner = partitioner;
    }

    public void setSelector(Selector<T> selector) {
        this.selector = selector;
    }

    public void setTotalPartitionNumber(int totalPartitionNumber) {
        this.totalPartitionNumber = totalPartitionNumber;
    }

    @Override
    public final void run() {
        this.open();
        while (!isEndOfStream()) {
            T value = execute();
            if (!isEndOfStream()) {
                boolean sendNext = true;
                if (selector != null) {
                    sendNext = selector.filter(value);
                }
                if (sendNext) {
                    int partitionTo = 0;
                    if (partitioner != null) {
                        partitionTo = partitioner.partition(value, totalPartitionNumber);
                        if (partitionTo < 0 || partitionTo >= this.channels.size()) {
                            // Discard.
                            // TODO : Logging Info.
                            continue;
                        }
                    }
                    Channel<T> channel = this.channels.get(partitionTo);
                    channel.add(new DataWrapper<>(value, false));
                }
            }
        }
        if (isEndOfStream()) {
            // Broadcast end of stream signal
            for (Channel<T> channel : this.channels) {
                channel.add(new DataWrapper<>(null, true));
            }
        }
        this.close();
    }

    public boolean isEndOfStream() {
        return false;
    }

    /**
     * Implement what the actual work will be do through the executor method.
     *
     * @return Generated value.
     */
    public abstract T execute();

    /**
     * Implement resource prepare if necessary.
     */
    public abstract void open();

    /**
     * Implement resource cleanup if necessary.
     */
    public abstract void close();
}
