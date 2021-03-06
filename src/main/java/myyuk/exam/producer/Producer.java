package myyuk.exam.producer;

import myyuk.exam.channel.Channel;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.selector.Selector;
import myyuk.exam.stream.DataWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Producer reads the data and sends the data to the channel.
 * Producer has a filter and a partitioner and can transfer data to a specific channel depending on their behavior.
 * When the EOS (End Of Stream) signal is received, broadcasts EOS signal to all channels. (for graceful shutdown)
 */
public abstract class Producer<T> implements Runnable {
    protected static final Logger logger = Logger.getLogger(Producer.class.getName());

    private List<Channel<T>> channels;
    private Partitioner<T> partitioner;
    private Selector<T> selector;
    private int totalPartitionNumber;

    public void setPartitioner(Partitioner<T> partitioner) {
        this.partitioner = partitioner;
    }

    public Selector<T> getSelector() {
        return selector;
    }

    public void setSelector(Selector<T> selector) {
        this.selector = selector;
    }

    public void setTotalPartitionNumber(int totalPartitionNumber) {
        this.totalPartitionNumber = totalPartitionNumber;
    }

    public void addChannel(Channel<T> channel) {
        if (this.channels == null) {
            this.channels = new ArrayList<>();
        }
        this.channels.add(channel);
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
                    int partitionTo = partitioner == null ? 0
                            : partitioner.partition(value, totalPartitionNumber);
                    if (partitionTo < 0 || partitionTo >= this.channels.size()) {
                        // Discard.
                        logger.info("Invalid partition id: " + partitionTo);
                        continue;
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

    /**
     * Check EOS (End Of Stream).
     *
     * @return true or false.
     */
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
