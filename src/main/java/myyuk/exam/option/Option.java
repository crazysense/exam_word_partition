package myyuk.exam.option;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages options in the form of key-values.
 */
public class Option {
    private Map<String, String> options;

    public Option() {
        this(null);
    }

    public Option(Map<String, String> options) {
        this.options = options;
        if (this.options == null) {
            this.options = new HashMap<>();
        }
    }

    /**
     * Add an option.
     *
     * @param key   Option key.
     * @param value Option value.
     */
    public void add(String key, String value) {
        this.options.put(key, value);
    }

    /**
     * Add all the values of the other options to the current option.
     *
     * @param option Options to be copied.
     */
    public void addAll(Option option) {
        this.options.putAll(option.options);
    }

    /**
     * Gets an option value as type of String.
     *
     * @param key Option key.
     * @return Option value. (null, if not exist)
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * Gets an option value as type of String.
     *
     * @param key Option key.
     * @param defaultValue The default value to return if the option does not exist.
     * @return Option value. (defaultValue, if not exist)
     */
    public String getString(String key, String defaultValue) {
        return options.getOrDefault(key, defaultValue);
    }

    /**
     * Gets an option value as type of Integer.
     *
     * @param key Option key.
     * @return Option value. (null, if not exist)
     */
    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    /**
     * Gets an option value as type of Integer.
     *
     * @param key Option key.
     * @param defaultValue The default value to return if the option does not exist.
     * @return Option value. (defaultValue, if not exist)
     */
    public Integer getInteger(String key, Integer defaultValue) {
        try {
            return Integer.valueOf(getString(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
