package myyuk.exam.selector;

/**
 * The selector filters the data.
 */
public interface Selector<T> {

    /**
     * Filter the data.
     * @param value The data to filter.
     * @return If the return value is false, the data is discarded.
     */
    boolean filter(T value);
}
