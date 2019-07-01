package myyuk.exam.selector;

import myyuk.exam.option.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.PatternSyntaxException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RegularExpressionSelectorTest {

    private RegularExpressionSelector selector;

    @Before
    public void before() {
        this.selector = new RegularExpressionSelector();
    }

    @Test(expected = PatternSyntaxException.class)
    public void testNonRegex() {
        Option option = new Option();
        option.add(RegularExpressionSelector.REGEX_OPTION_KEY, "][");
        this.selector.configure(option);
        fail();
    }

    @Test
    public void testAll() {
        Option option = new Option();
        option.add(RegularExpressionSelector.REGEX_OPTION_KEY, ".*");
        this.selector.configure(option);

        assertTrue(this.selector.filter("anything~~"));
        assertTrue(this.selector.filter("!!everything!!"));
    }

    @Test
    public void testFirstLetterAlphabetFilter() {
        Option option = new Option();
        option.add(RegularExpressionSelector.REGEX_OPTION_KEY, "^[a-zA-Z].*$");
        this.selector.configure(option);

        assertTrue(this.selector.filter("abc"));
        assertTrue(this.selector.filter("a!b@c"));
        assertTrue(this.selector.filter("abc123"));
        assertTrue(this.selector.filter("a"));
        assertTrue(this.selector.filter("a,"));
        assertFalse(this.selector.filter("!a"));
        assertFalse(this.selector.filter("123abc"));
    }
}