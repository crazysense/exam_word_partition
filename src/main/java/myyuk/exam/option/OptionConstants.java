package myyuk.exam.option;

/**
 * Reserved keyword for the options
 */
public abstract class OptionConstants {
    // Program Argument
    public static final String READ_FILE_PATH = "option.read.file";
    public static final String WRITE_DIRECTORY_PATH = "option.write.directory";
    public static final String PARTITION_NUMBER = "option.partition.max";


    // Stream
    public static final String PRODUCER_TYPE = "option.producer.type";
    public static final String CHANNEL_TYPE = "option.channel.type";
    public static final String CONSUMER_TYPE = "option.consumer.type";
    public static final String PARTITIONER_TYPE = "option.partitioner.type";
    public static final String SELECTOR_TYPE = "option.selector.type";

    // Defaults
    public static final int DEFAULT_PARTITION_NUMBER = 0;
}
