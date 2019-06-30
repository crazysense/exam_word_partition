package myyuk.exam.consumer;

import myyuk.exam.configuration.Configurable;
import myyuk.exam.exception.ResourceException;
import myyuk.exam.exception.StreamExecutionException;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionConstants;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 */
@SuppressWarnings("unused")
public class WordWriteConsumer extends Consumer<String> implements Configurable {

    private static final String FILE_EXTENSION = ".txt";

    private String writeDirectoryPath;
    private Map<Character, Writer> fileMap;

    @Override
    public void configure(Option option) {
        this.writeDirectoryPath = option.getString(OptionConstants.WRITE_DIRECTORY_PATH);
    }

    @Override
    public void open() {
        logger.entering("WordWriteConsumer[" + getPartitionId() + "]", "open()");
        this.fileMap = new HashMap<>();
    }

    @Override
    public void close() {
        logger.entering("WordWriteConsumer[" + getPartitionId() + "]", "close()");
        for (Writer writer : this.fileMap.values()) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new ResourceException(e.getMessage(), e);
            }
        }
        this.fileMap.clear();
    }

    @Override
    public void execute(String value) {
        Writer writer = getOrCreate(value);
        try {
            writer.write(value);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            throw new StreamExecutionException(e.getMessage(), e);
        }
    }

    private synchronized Writer createWriter(char key) {
        Writer writer = this.fileMap.get(key);
        if (writer == null) {
            try {
                File file = new File(this.writeDirectoryPath, key + FILE_EXTENSION);
                writer = Files.newBufferedWriter(file.toPath(),
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                String error = "Could not create file: " + key + FILE_EXTENSION
                        + System.lineSeparator() + "The key '" + key + "' will not be saved.";
                logger.info(error);
                writer = new DummyWriter();
            }
        }
        return writer;
    }

    private Writer getOrCreate(String value) {
        char key = Character.toLowerCase(value.charAt(0));
        Writer writer = this.fileMap.get(key);
        if (writer == null) {
            writer = createWriter(key);
            this.fileMap.put(key, writer);
        }
        return writer;
    }

    /**
     * If the file can not be created, the Dummy Writer is used.
     * And if the Dummy Writer is called, do not anything.
     */
    class DummyWriter extends Writer {

        @Override
        public void write(char[] cbuf, int off, int len) {
            // do nothing
        }

        @Override
        public void flush() {
            // do nothing
        }

        @Override
        public void close() {
            // do nothing
        }
    }
}
