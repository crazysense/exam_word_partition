package myyuk.exam.types;

import myyuk.exam.channel.Channel;
import myyuk.exam.configuration.Configurables;
import myyuk.exam.consumer.Consumer;
import myyuk.exam.exception.InvalidOptionException;
import myyuk.exam.option.Option;
import myyuk.exam.partitioner.Partitioner;
import myyuk.exam.producer.Producer;
import myyuk.exam.selector.Selector;

import static myyuk.exam.types.ComponentTypes.*;

/**
 * SimpleFactory is a factory class that creates components.
 */
public abstract class SimpleFactory {

    /**
     * Create a Producer.
     * @param type The type of Producer.
     * @param option An option.
     * @return Created new Producer.
     */
    public static <T> Producer<T> createProducer(String type, Option option) {
        ProducerType pt = ProducerType.UNKNOWN.of(type);
        String className = ProducerType.UNKNOWN.equals(pt) ? type : pt.getClassName();
        try {
            return createComponent(className, option);
        } catch (Exception e) {
            throw new InvalidOptionException("Invalid producer type: " + type);
        }
    }

    /**
     * Create a Consumer.
     * @param type The type of Consumer.
     * @param option An option.
     * @return Created new Consumer.
     */
    public static <T> Consumer<T> createConsumer(String type, Option option) {
        ConsumerType ct = ConsumerType.UNKNOWN.of(type);
        String className = ConsumerType.UNKNOWN.equals(ct) ? type : ct.getClassName();
        try {
            return createComponent(className, option);
        } catch (Exception e) {
            throw new InvalidOptionException("Invalid consumer type: " + type);
        }
    }

    /**
     * Create a Channel.
     * @param type The type of Channel.
     * @param option An option.
     * @return Created new Channel.
     */
    public static <T> Channel<T> createChannel(String type, Option option) {
        ChannelType ct = ChannelType.UNKNOWN.of(type);
        String className = ChannelType.UNKNOWN.equals(ct) ? type : ct.getClassName();
        try {
            return createComponent(className, option);
        } catch (Exception e) {
            throw new InvalidOptionException("Invalid channel type: " + type);
        }
    }

    /**
     * Create a Partitioner.
     * @param type The type of Partitioner.
     * @param option An option.
     * @return Created new Partitioner.
     */
    public static <T> Partitioner<T> createPartitioner(String type, Option option) {
        PartitionerType pt = PartitionerType.UNKNOWN.of(type);
        String className = PartitionerType.UNKNOWN.equals(pt) ? type : pt.getClassName();
        try {
            return createComponent(className, option);
        } catch (Exception e) {
            throw new InvalidOptionException("Invalid partitioner type: " + type);
        }
    }

    /**
     * Create a Selector.
     * @param type The type of Selector.
     * @param option An option.
     * @return Created new Selector.
     */
    public static <T> Selector<T> createSelector(String type, Option option) {
        SelectorType st = SelectorType.UNKNOWN.of(type);
        String className = SelectorType.UNKNOWN.equals(st) ? type : st.getClassName();
        try {
            return createComponent(className, option);
        } catch (Exception e) {
            throw new InvalidOptionException("Invalid selector type: " + type);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T createComponent(String className, Option option) throws Exception {
        Class<T> clazz = (Class<T>) Class.forName(className);
        T instance = clazz.newInstance();
        Configurables.configure(instance, option);
        return instance;
    }
}
