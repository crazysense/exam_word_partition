package myyuk.exam.channel;

import myyuk.exam.stream.DataWrapper;

/**
 * The channel is the buffer of the partition.
 */
public abstract class Channel<T> implements Cloneable {

    /**
     * Add data to the channel.
     * @param value The data to add.
     * @return Success of failure.
     */
    public abstract boolean add(DataWrapper<T> value);

    /**
     * Fetch data from the channel.
     * @return The data.
     */
    public abstract DataWrapper<T> get();

    /**
     * Cleanup resources.
     */
    public abstract void clear();

    @Override
    @SuppressWarnings("unchecked")
    public Channel<T> clone() throws CloneNotSupportedException {
        return (Channel<T>) super.clone();
    }
}
