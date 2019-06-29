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

    private OptionBuilder() {
        // Avoid constructor calls from outside.
    }

    public static OptionBuilder of() {
        return new OptionBuilder();
    }

    public static OptionBuilder of(String[] arguments) {
        OptionBuilder parser = new OptionBuilder();
        parser.arguments = arguments;
        return parser;
    }

    public OptionBuilder arguments(String[] arguments) {
        this.arguments = arguments;
        return this;
    }

    public Option build() {
        if (arguments == null || arguments.length != 3) {
            // TODO : Logging Usage.
            throw new IllegalArgumentException("Usage : ...");
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
            // TODO : Logging Error.
            // FileNotFoundException("Argument 1");
            return false;
        }
        // Check the path of the directory to save results.
        Path directoryPathToWrite = Paths.get(option.getString(OptionConstants.WRITE_DIRECTORY_PATH, ""));
        if (!Files.exists(directoryPathToWrite)) {
            try {
                Files.createDirectories(directoryPathToWrite);
            } catch (IOException e) {
                // TODO : Logging Error.
                return false;
            }
        } else if (!Files.isDirectory(directoryPathToWrite)) {
            // TODO : Logging Error.
            // FileAlreadyExistsException("Argument 2");
            return false;
        }
        // Check partition number. (1 < N < 27)
        int partitionNumber = option.getInteger(OptionConstants.PARTITION_NUMBER, OptionConstants.DEFAULT_PARTITION_NUMBER);
        if (!(1 < partitionNumber && partitionNumber < 27)) {
            return false;
        }

        // Succeed
        return true;
    }
}
