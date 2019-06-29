package myyuk.exam.types;

import myyuk.exam.channel.Channel;
import myyuk.exam.configuration.Configurables;
import myyuk.exam.consumer.Consumer;
import myyuk.exam.option.Option;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.producer.Producer;
import myyuk.exam.selector.Selector;

import static myyuk.exam.types.ComponentTypes.*;

@SuppressWarnings("unchecked")
public abstract class SimpleFactory {

    public static <T> Channel<T> createChannel(String type, Option option) {
        ChannelType ct = ChannelType.UNKNOWN.of(type);
        if (!ChannelType.UNKNOWN.equals(ct)) {
            try {
                Class<Channel<T>> channelClass = (Class<Channel<T>>) Class.forName(ct.getClassName());
                Channel<T> channel = channelClass.newInstance();
                Configurables.configure(channel, option);
                return channel;
            } catch (Exception e) {
                // TODO : Logging Error.
            }
        } else {
            // TODO : Logging Error.
        }
        return null;
    }

    public static <T> Consumer<T> createConsumer(String type, Option option) {
        ConsumerType ct = ConsumerType.UNKNOWN.of(type);
        if (!ConsumerType.UNKNOWN.equals(ct)) {
            try {
                Class<Consumer<T>> consumerClass = (Class<Consumer<T>>) Class.forName(ct.getClassName());
                Consumer<T> consumer = consumerClass.newInstance();
                Configurables.configure(consumer, option);
                return consumer;
            } catch (Exception e) {
                // TODO : Logging Error.
            }
        } else {
            // TODO : Logging Error.
        }
        return null;
    }

    public static <T> Producer<T> createProducer(String type, Option option) {
        ProducerType pt = ProducerType.UNKNOWN.of(type);
        if (!ProducerType.UNKNOWN.equals(pt)) {
            try {
                Class<Producer<T>> producerClass = (Class<Producer<T>>) Class.forName(pt.getClassName());
                Producer<T> producer = producerClass.newInstance();
                Configurables.configure(producer, option);
                return producer;
            } catch (Exception e) {
                // TODO : Logging Error.
            }
        } else {
            // TODO : Logging Error.
        }
        return null;
    }

    public static <T> Partitioner<T> createPartitioner(String type, Option option) {
        PartitionerType pt = PartitionerType.UNKNOWN.of(type);
        if (!PartitionerType.UNKNOWN.equals(pt)) {
            try {
                Class<Partitioner<T>> partitionerClass = (Class<Partitioner<T>>) Class.forName(pt.getClassName());
                Partitioner<T> partitioner = partitionerClass.newInstance();
                Configurables.configure(partitioner, option);
                return partitioner;
            } catch (Exception e) {
                // TODO : Logging Error.
            }
        } else {
            // TODO : Logging Error.
        }
        return null;
    }

    public static <T> Selector<T> createSelector(String type, Option option) {
        SelectorType st = SelectorType.UNKNOWN.of(type);
        if (!SelectorType.UNKNOWN.equals(st)) {
            try {
                Class<Selector<T>> selectorClass = (Class<Selector<T>>) Class.forName(st.getClassName());
                Selector<T> selector = selectorClass.newInstance();
                Configurables.configure(selector, option);
                return selector;
            } catch (Exception e) {
                // TODO : Logging Error.
            }
        } else {
            // TODO : Logging Error.
        }
        return null;
    }
}
