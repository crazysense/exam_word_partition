package myyuk.exam.types;

public class ComponentTypes {

    public interface ComponentType<E extends Enum<E>> {
        String getClassName();

        Class<E> getDeclaringClass();

        default E of(String type) {
            for (E e : getDeclaringClass().getEnumConstants()) {
                if (e instanceof ComponentType && ((ComponentType) e).getClassName().equalsIgnoreCase(type)) {
                    return e;
                } else if (e.name().equalsIgnoreCase(type)) {
                    return e;
                }
            }
            return Enum.valueOf(getDeclaringClass(), "UNKNOWN");
        }
    }

    // Producer Type
    public enum ProducerType implements ComponentType<ProducerType> {
        UNKNOWN("null"),
        WORD_READER("myyuk.exam.producer.WordsFromFileProducer");

        private String className;

        ProducerType(String className) {
            this.className = className;
        }

        @Override
        public String getClassName() {
            return className;
        }
    }

    // Consumer Type
    public enum ConsumerType implements ComponentType<ConsumerType> {
        UNKNOWN("null"),
        WORD_WRITER("myyuk.exam.consumer.WordWriteConsumer");

        private String className;

        ConsumerType(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }

    // Channel Type
    public enum ChannelType implements ComponentType<ChannelType> {
        UNKNOWN("null"),
        MEMORY_FIFO_CHANNEL("myyuk.exam.channel.MemoryFifoChannel");

        private String className;

        ChannelType(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }

    // Partitioner Type
    public enum PartitionerType implements ComponentType<PartitionerType> {
        UNKNOWN("null"),
        FIRST_LETTER_ALPHABET("myyuk.exam.partitioner.FirstLetterAlphabetPartitioner");

        private String className;

        PartitionerType(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }

    // Selector Type
    public enum SelectorType implements ComponentType<SelectorType> {
        UNKNOWN("null"),
        GENERAL_REGEX("myyuk.exam.selector.RegularExpressionSelector"),
        FIRSTLETTER_ALPHABET("myyuk.exam.selector.FirstLetterAlphabetSelector");

        private String className;

        SelectorType(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }
}
