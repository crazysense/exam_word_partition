package myyuk.exam.stream;

/**
 * TODO:
 */
public class DataWrapper<T> {
    private final T value;
    private final boolean endOfStream;

    public DataWrapper(T value, boolean endOfStream) {
        this.value = value;
        this.endOfStream = endOfStream;
    }

    public T getValue() {
        return value;
    }

    public boolean isEndOfStream() {
        return endOfStream;
    }
}
