package myyuk.exam.configuration;

import myyuk.exam.option.Option;

/**
 * Utilities for Configurable interface
 */
public abstract class Configurables {

    /**
     * Initializes the component that inherits Configurable.
     *
     * @param obj Any object.
     * @param opt Options to be passed as argument to Configurable
     */
    public static void configure(Object obj, Option opt) {
        if (obj instanceof Configurable) {
            ((Configurable) obj).configure(opt);
        }
    }
}
