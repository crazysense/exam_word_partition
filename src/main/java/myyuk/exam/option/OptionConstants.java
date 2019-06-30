package myyuk.exam.option;

/**
 * TODO:
 */
public abstract class OptionConstants {
    public static final String READ_FILE_PATH = "option.read.file";
    public static final String WRITE_DIRECTORY_PATH = "option.write.directory";
    public static final String PARTITION_NUMBER = "option.partition.max";

    public static final int DEFAULT_PARTITION_NUMBER = 0;

    // Stream
    public static final String PRODUCER_TYPE = "option.producer.type";
    public static final String CHANNEL_TYPE = "option.channel.type";
    public static final String CONSUMER_TYPE = "option.consumer.type";
    public static final String PARTITIONER_TYPE = "option.partitioner.type";
    public static final String SELECTOR_TYPE = "option.selector.type";
}
