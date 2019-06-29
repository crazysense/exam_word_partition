package myyuk.exam.selector;

import java.util.regex.Pattern;

public class RegularExpressionSelector implements Selector<String> {

    private final Pattern pattern;

    public RegularExpressionSelector(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean filter(String value) {
        return this.pattern.matcher(value).matches();
    }
}
