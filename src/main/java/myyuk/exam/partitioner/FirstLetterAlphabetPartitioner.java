package myyuk.exam.partitioner;

/**
 * TODO:
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
