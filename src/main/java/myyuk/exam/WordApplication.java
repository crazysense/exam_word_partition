package myyuk.exam;

import myyuk.exam.exception.InvalidOptionException;
import myyuk.exam.exception.StreamExecutionException;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionBuilder;
import myyuk.exam.stream.Stream;
import myyuk.exam.stream.StreamBuilder;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordApplication {
    private final static Level logLevel = Level.INFO;
    private final static Logger logger = Logger.getGlobal();
    private static final String USAGE = "Usage: WordApplication " +
            "[READ_FILE_PATH] [WRITE_DIRECTORY_PATH] [PARTITION_NUMBER]" + System.lineSeparator() +
            "==============================================================================" + System.lineSeparator() +
            "READ_FILE_PATH       : Specifies the absolute path of the file to read." + System.lineSeparator() +
            "WRITE_DIRECTORY_PATH : Specifies the directory where the result files will be stored." + System.lineSeparator() +
            "PARTITION_NUMBER     : Specifies the number of parallel partitions. (1 < PARTITION_NUMBER < 27)";

    static {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        logger.addHandler(consoleHandler);
        logger.setLevel(logLevel);
    }

    public static void main(String[] args) {
        Stream<String> stream = null;

        try {
            OptionBuilder optionBuilder = OptionBuilder.of(args);
            Option option = optionBuilder.build();
            if (option == null) {
                throw new InvalidOptionException(optionBuilder.getError());
            }

            stream = StreamBuilder.of(option).build();
            stream.start();
        } catch (IllegalArgumentException e) {
            logger.info(USAGE);
        } catch (InvalidOptionException e) {
            logger.info(e.getMessage());
        } catch (StreamExecutionException e) {
            logger.severe(e.getMessage());
        } finally {
            if (stream != null) {
                stream.shutdown();
            }
        }
    }
}
