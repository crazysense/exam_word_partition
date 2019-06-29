package myyuk.exam.channel;

import myyuk.exam.stream.DataWrapper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MemoryFifoChannel<T> implements Channel<T> {

    private BlockingQueue<DataWrapper<T>> queue;

    public MemoryFifoChannel() {
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public boolean add(DataWrapper<T> value) {
        return this.queue.add(value);
    }

    @Override
    public DataWrapper<T> get() {
        try {
            return this.queue.take();
        } catch (InterruptedException e) {
            // TODO: Logging Error.
            return null;
        }
    }

    @Override
    public void clear() {
        if (this.queue != null) {
            this.queue.clear();
        }
    }
}
