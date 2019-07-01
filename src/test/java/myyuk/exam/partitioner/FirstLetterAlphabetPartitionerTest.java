package myyuk.exam.partitioner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class FirstLetterAlphabetPartitionerTest {

    private FirstLetterAlphabetPartitioner partitioner;

    @Before
    public void before() {
        this.partitioner = new FirstLetterAlphabetPartitioner();
    }

    @Test
    public void testNullValue() {
        int bucket = 3;
        assertEquals(-1, this.partitioner.partition(null, bucket));
    }

    @Test
    public void testPartition() {
        int bucket = 3;
        assertEquals(0, this.partitioner.partition("abc", bucket));
        assertEquals(0, this.partitioner.partition("Abc", bucket));
        assertEquals(1, this.partitioner.partition("bcd", bucket));
        assertEquals(1, this.partitioner.partition("Bcd", bucket));
        assertEquals(2, this.partitioner.partition("cde", bucket));
        assertEquals(2, this.partitioner.partition("Cde", bucket));
        assertEquals(0, this.partitioner.partition("def", bucket));
        assertEquals(0, this.partitioner.partition("Def", bucket));
    }
}