package myyuk.exam.selector;

import java.util.regex.Pattern;

public class FirstLetterAlphabetSelector extends RegularExpressionSelector {

    public FirstLetterAlphabetSelector() {
        super(Pattern.compile("^[a-zA-Z].*$"));
    }
}
