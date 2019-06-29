package myyuk.exam.channel;

import myyuk.exam.stream.DataWrapper;

/**
 * The channel is the buffer of the partition.
 */
public interface Channel<T> {

    /**
     * Add data to the channel.
     * @param value The data to add.
     * @return Success of failure.
     */
    boolean add(DataWrapper<T> value);

    /**
     * Fetch data from the channel.
     * @return The data.
     */
    DataWrapper<T> get();

    /**
     * Cleanup resources.
     */
    void clear();
}
