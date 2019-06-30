package myyuk.exam.option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Check program arguments and generate option.
 *
 * @see myyuk.exam.option.Option
 */
public class OptionBuilder {
    private String[] arguments;
    private String error;

    private OptionBuilder() {
        // Avoid constructor calls from outside.
    }

    public String getError() {
        return error;
    }

    /**
     * Create new instance of OptionBuilder.
     *
     * @return An instance of OptionBuilder.
     */
    public static OptionBuilder of() {
        return new OptionBuilder();
    }

    /**
     * Create new instance of OptionBuilder with arguments.
     *
     * @param arguments The program arguments.
     * @return An instance of OptionBuilder.
     */
    public static OptionBuilder of(String[] arguments) {
        OptionBuilder parser = new OptionBuilder();
        parser.arguments = arguments;
        return parser;
    }

    /**
     * Sets the program arguments.
     *
     * @param arguments The program arguments.
     * @return An instance of OptionBuilder that arguments is set.
     */
    public OptionBuilder arguments(String[] arguments) {
        this.arguments = arguments;
        return this;
    }

    /**
     * Verifies the program arguments, and generates options if valid.
     *
     * @return Verified option or null. (invalid)
     */
    public Option build() {
        if (arguments == null || arguments.length != 3) {
            throw new IllegalArgumentException("Invalid Usage");
        }

        Option option = new Option();
        option.add(OptionConstants.READ_FILE_PATH, arguments[0]);
        option.add(OptionConstants.WRITE_DIRECTORY_PATH, arguments[1]);
        option.add(OptionConstants.PARTITION_NUMBER, arguments[2]);

        return validate(option) ? option : null;
    }

    private boolean validate(Option option) {
        // Check the path of the file to be processed.
        Path filePathToRead = Paths.get(option.getString(OptionConstants.READ_FILE_PATH, ""));
        if (!Files.exists(filePathToRead)) {
            this.error = String.format("The file to read does not exist in the specified path: %s", filePathToRead);
            return false;
        }

        // Check the path of the directory to save results.
        Path directoryPathToWrite = Paths.get(option.getString(OptionConstants.WRITE_DIRECTORY_PATH, ""));
        if (!Files.exists(directoryPathToWrite)) {
            try {
                Files.createDirectories(directoryPathToWrite);
            } catch (IOException e) {
                this.error = String.format("Could not create a directory to save the results: %s", directoryPathToWrite);
                return false;
            }
        } else if (!Files.isDirectory(directoryPathToWrite)) {
            this.error = String.format("Could not create a directory to save the results: %s", directoryPathToWrite);
            return false;
        }
        try {
            option.add(OptionConstants.WRITE_DIRECTORY_PATH, directoryPathToWrite.toFile().getCanonicalPath());
        } catch (IOException ignore) {
            // Previously checked. Ignore it.
        }

        // Check partition number. (1 < N < 27)
        int partitionNumber = option.getInteger(OptionConstants.PARTITION_NUMBER, OptionConstants.DEFAULT_PARTITION_NUMBER);
        if (!(1 < partitionNumber && partitionNumber < 27)) {
            this.error = String.format("The number of partitions can not exceed the range of N. (1 < N < 27): %s",
                    option.getString(OptionConstants.PARTITION_NUMBER));
            return false;
        }

        // Succeed
        return true;
    }
}
