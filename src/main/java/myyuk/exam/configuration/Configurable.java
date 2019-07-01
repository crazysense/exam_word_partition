package myyuk.exam.configuration;

import myyuk.exam.option.Option;

/**
 * Implement a Configurable component that requires configuration.
 */
public interface Configurable {

    /**
     * An object that created by the factory, call configure.
     * @param option The option.
     */
    void configure(Option option);
}
