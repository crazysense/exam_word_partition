package myyuk.exam.stream;

import myyuk.exam.channel.Channel;
import myyuk.exam.consumer.Consumer;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionConstants;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.producer.Producer;
import myyuk.exam.selector.Selector;
import myyuk.exam.types.ComponentTypes;
import myyuk.exam.types.SimpleFactory;

import java.util.ArrayList;
import java.util.List;

public class StreamBuilder {
    private Option option;

    private StreamBuilder() {
        this(null);
    }

    private StreamBuilder(Option option) {
        this.option = option;
    }

    public static StreamBuilder of() {
        return new StreamBuilder();
    }

    public static StreamBuilder of(Option option) {
        return new StreamBuilder(option);
    }

    public StreamBuilder option(Option option) {
        this.option = option;
        return this;
    }

    public <T> Stream<T> build() {
        if (this.option == null) {
            return null;
        }

        List<Channel<T>> channelList = new ArrayList<>();
        List<Consumer<T>> consumers = new ArrayList<>();
        int maxPartitionNumber = this.option.getInteger(OptionConstants.PARTITION_NUMBER);
        for (int i = 0; i < maxPartitionNumber; i++) {
            // Create channel
            Channel<T> channel = SimpleFactory.createChannel(
                    ComponentTypes.ChannelType.MEMORY_FIFO_CHANNEL.name(), this.option);
            channelList.add(channel);

            // Create consumer
            Consumer<T> consumer = SimpleFactory.createConsumer(
                    ComponentTypes.ConsumerType.WORD_WRITER.name(), this.option);
            if (channel == null || consumer == null) {
                return null;
            }
            consumer.setChannel(channel);
            consumer.setPartitionId(i);

            consumers.add(consumer);
        }

        // Create partitioner
        Partitioner<T> partitioner = SimpleFactory.createPartitioner(
                ComponentTypes.PartitionerType.FIRST_LETTER_ALPHABET.name(), this.option);
        // Create selector
        Selector<T> selector = SimpleFactory.createSelector(
                ComponentTypes.SelectorType.FIRSTLETTER_ALPHABET.name(), this.option);

        // Create producer
        Producer<T> producer = SimpleFactory.createProducer(
                ComponentTypes.ProducerType.WORD_READER.name(), this.option);
        producer.setChannels(channelList);
        producer.setPartitioner(partitioner);
        producer.setSelector(selector);
        producer.setTotalPartitionNumber(maxPartitionNumber);

        return new Stream<>(producer, consumers);
    }
}
