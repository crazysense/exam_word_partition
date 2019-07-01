package myyuk.exam.consumer;

import myyuk.exam.channel.Channel;
import myyuk.exam.stream.DataWrapper;

import java.util.logging.Logger;

/**
 * A Consumer is an executor that receives data from a channel and performs operations.
 */
public abstract class Consumer<T> implements Runnable, Cloneable {
    protected static final Logger logger = Logger.getLogger(Consumer.class.getName());

    private int partitionId; // for logging
    private Channel<T> channel;
    private volatile boolean endOfStream = false;

    public void setChannel(Channel<T> channel) {
        this.channel = channel;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public int getPartitionId() {
        return partitionId;
    }

    @Override
    public final void run() {
        this.open();
        while (!isEndOfStream()) {
            DataWrapper<T> dataWrapper = this.channel.get();
            this.endOfStream = dataWrapper.isEndOfStream();
            if (!isEndOfStream()) {
                execute(dataWrapper.getValue());
            }
        }
        this.channel.clear();
        this.close();
    }

    private boolean isEndOfStream() {
        return endOfStream;
    }

    /**
     * Implement what the actual work will be do through the executor method.
     *
     * @param value The data received from the channel.
     */
    public abstract void execute(T value);


    /**
     * Implement resource prepare if necessary.
     */
    public abstract void open();

    /**
     * Implement resource cleanup if necessary.
     */
    public abstract void close();

    @Override
    @SuppressWarnings("unchecked")
    public Consumer<T> clone() throws CloneNotSupportedException {
        return (Consumer<T>) super.clone();
    }
}
