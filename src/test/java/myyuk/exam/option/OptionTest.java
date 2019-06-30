package myyuk.exam.option;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JUnit4.class)
public class OptionTest {

    @Test
    public void testString() {
        Option option = new Option();
        option.add("string-key", "string-value");
        option.add("number-key", "12345");

        assertEquals("string-value", option.getString("string-key"));
        assertEquals("12345", option.getString("number-key"));
    }

    @Test
    public void testStringDefault() {
        Option option = new Option();
        option.add("string-key", "string-value");

        assertEquals("string-value", option.getString("string-key", "default"));
        assertEquals("default", option.getString("non-exist-key", "default"));
    }

    @Test
    public void testNumber() {
        Option option = new Option();
        option.add("number-key", "12345");
        option.add("string-key", "not-numeric");

        assertNull(option.getInteger("string-key"));
        assertEquals((Integer) 12345, option.getInteger("number-key"));
    }

    @Test
    public void testNumberDefault() {
        Option option = new Option();
        option.add("number-key", "12345");
        option.add("string-key", "not-numeric");

        assertEquals((Integer) 0, option.getInteger("string-key", 0));
        assertEquals((Integer) 12345, option.getInteger("number-key", 0));
    }

    @Test
    public void testNonExist() {
        Option option = new Option();
        assertNull(option.getString("non-exist-key"));
        assertNull(option.getInteger("non-exist-key"));
    }

    @Test
    public void testNonExistDefault() {
        Option option = new Option();
        assertEquals("default", option.getString("non-exist-key", "default"));
        assertEquals((Integer) 0, option.getInteger("non-exist-key", 0));
    }
}