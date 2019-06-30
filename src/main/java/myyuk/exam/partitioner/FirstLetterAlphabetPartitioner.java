package myyuk.exam.partitioner;

/**
 * Partition according to the first letter of the alphabet.
 * It is not case-sensitive,
 * for example, 'a' and 'A' have the same partition id.
 */
@SuppressWarnings("unused")
public class FirstLetterAlphabetPartitioner implements Partitioner<String> {

    @Override
    public int partition(String value, int bucket) {
        if (value == null || value.isEmpty()) {
            // Discard data.
            return -1;
        }
        char firstAlphabet = Character.toLowerCase(value.charAt(0));
        int partitionNumber = (int) firstAlphabet - 97; // 'a'
        return partitionNumber % bucket;
    }
}
