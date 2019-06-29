package myyuk.exam.selector;

import myyuk.exam.option.Option;

/**
 * TODO:
 */
@SuppressWarnings("unused")
public class FirstLetterAlphabetSelector extends RegularExpressionSelector {

    @Override
    public void configure(Option option) {
        option.add(REGEX_OPTION_KEY, "^[a-zA-Z].*$");
        super.configure(option);
    }
}
