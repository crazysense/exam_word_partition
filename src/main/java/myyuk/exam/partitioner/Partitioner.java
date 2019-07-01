package myyuk.exam.partitioner;

/**
 * The Partitioner defines which partitions the data will be sent to.
 */
public interface Partitioner<T> {

    /**
     * Partition
     * @param value The data.
     * @param bucket The maximum number of partitions.
     * @return The partition number to which data will be sent. (Zero-based)
     */
    int partition(T value, int bucket);
}
