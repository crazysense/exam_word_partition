package myyuk.exam.selector;

import myyuk.exam.configuration.Configurable;
import myyuk.exam.option.Option;

import java.util.regex.Pattern;

/**
 * RegularExpressionSelector filters strings corresponding to the specified regular expression.
 * Returns 'true' if the data matches the specified regular expression, 'false' otherwise.
 */
@SuppressWarnings("unused")
public class RegularExpressionSelector implements Selector<String>, Configurable {

    public static final String REGEX_OPTION_KEY = "option.selector.regex";
    private static final String REGEX_OPTION_DEFAULT = ".*";

    private Pattern pattern;

    @Override
    public boolean filter(String value) {
        return this.pattern.matcher(value).matches();
    }

    @Override
    public void configure(Option option) {
        this.pattern = Pattern.compile(option.getString(REGEX_OPTION_KEY, REGEX_OPTION_DEFAULT));
    }
}
