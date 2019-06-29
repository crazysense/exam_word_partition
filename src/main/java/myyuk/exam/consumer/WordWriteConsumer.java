package myyuk.exam.consumer;

import myyuk.exam.configuration.Configurable;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionConstants;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

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
        this.fileMap = new HashMap<>();
    }

    @Override
    public void close() {
        System.out.println("close-consumer");
        for (Writer writer : this.fileMap.values()) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // TODO : Logging Error.
            }
        }
    }

    @Override
    public void execute(String value) {
        Writer writer = getOrCreate(value);
        try {
            writer.write(value);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            // TODO : Logging Error.
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
                // TODO : Dummy or Exception ?
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

    class DummyWriter extends Writer {

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            // do nothing
        }

        @Override
        public void flush() throws IOException {
            // do nothing
        }

        @Override
        public void close() throws IOException {
            // do nothing
        }
    }
}
