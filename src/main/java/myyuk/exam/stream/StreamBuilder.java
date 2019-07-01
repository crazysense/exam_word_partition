package myyuk.exam.stream;

import myyuk.exam.channel.Channel;
import myyuk.exam.consumer.Consumer;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionConstants;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.producer.Producer;
import myyuk.exam.selector.Selector;
import myyuk.exam.types.SimpleFactory;

/**
 * StreamBuilder helps you generate streams from the option.
 * Rather than change the program code to use another classes,
 * users can change the class by simply changing the option.
 *
 * @see Option
 */
public class StreamBuilder {
    private Option option;

    private StreamBuilder() {
        this(null);
    }

    private StreamBuilder(Option option) {
        this.option = option;
    }

    /**
     * Create new instance of StreamBuilder.
     *
     * @return An instance of StreamBuilder.
     */
    public static StreamBuilder of() {
        return new StreamBuilder();
    }

    /**
     * Create new instance of StreamBuilder.
     *
     * @param option An option.
     * @return An instance of StreamBuilder.
     */
    public static StreamBuilder of(Option option) {
        return new StreamBuilder(option);
    }

    /**
     * Create new instance of StreamBuilder.
     *
     * @param option An option.
     * @return An instance of StreamBuilder.
     */
    public StreamBuilder option(Option option) {
        this.option = option;
        return this;
    }

    /**
     * Verifies the option, and generates StreamExecutor if valid.
     *
     * @return Verified StreamExecutor or null. (invalid)
     * @see StreamExecutor
     */
    public <T> StreamExecutor<T> build() {
        if (this.option == null) {
            return null;
        }

        // Create producer
        Producer<T> producer = SimpleFactory.createProducer(
                this.option.getString(OptionConstants.PRODUCER_TYPE), this.option);

        // Create channel
        Channel<T> channel = SimpleFactory.createChannel(
                this.option.getString(OptionConstants.CHANNEL_TYPE), this.option);

        // Create partitioner
        Partitioner<T> partitioner = SimpleFactory.createPartitioner(
                this.option.getString(OptionConstants.PARTITIONER_TYPE), this.option);

        // Create selector
        Selector<T> selector = SimpleFactory.createSelector(
                this.option.getString(OptionConstants.SELECTOR_TYPE), this.option);

        // Create consumer
        Consumer<T> consumer = SimpleFactory.createConsumer(
                this.option.getString(OptionConstants.CONSUMER_TYPE), this.option);

        // Make StreamExecutor
        int maxPartitionNumber = this.option.getInteger(OptionConstants.PARTITION_NUMBER);
        StreamEnvironment<T> env = StreamEnvironment.of(maxPartitionNumber);
        Stream<T> stream = env.addProducer(producer);
        if (selector != null) stream = stream.filter(selector);
        if (partitioner != null) stream = stream.keyBy(partitioner);
        if (channel != null) stream.addChannel(channel);
        return stream.addConsumer(consumer);
    }
}
