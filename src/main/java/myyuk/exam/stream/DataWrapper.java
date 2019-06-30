package myyuk.exam.stream;

/**
 * DataWrapper is a wrapper class for sending data and EOS signals together.
 * Consumers must be able to receive EOS signals from each message to avoid concurrency issues.
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
