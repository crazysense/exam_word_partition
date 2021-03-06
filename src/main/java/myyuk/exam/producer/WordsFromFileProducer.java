package myyuk.exam.producer;

import myyuk.exam.configuration.Configurable;
import myyuk.exam.exception.ResourceException;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionConstants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * WordsFromFileProducer reads from the file as texts.
 * When reading to the end of the file, the EOS signal will be set to 'true' and close file stream.
 */
@SuppressWarnings("unused")
public class WordsFromFileProducer extends Producer<String> implements Configurable {

    private String readFilePath;
    private BufferedReader reader;
    private volatile boolean endOfStream = false;

    @Override
    public void configure(Option option) {
        this.readFilePath = option.getString(OptionConstants.READ_FILE_PATH);
    }

    @Override
    public boolean isEndOfStream() {
        return endOfStream;
    }

    @Override
    public String execute() {
        String readLine = null;
        try {
            readLine = this.reader.readLine();
            this.endOfStream = readLine == null;
        } catch (IOException e) {
            logger.severe(e.getMessage());
            this.endOfStream = true;
        }
        return readLine;
    }

    @Override
    public void open() {
        logger.entering("WordsFromFileProducer", "open()");
        try {
            this.reader = new BufferedReader(new FileReader(this.readFilePath));
        } catch (FileNotFoundException e) {
            throw new ResourceException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        logger.entering("WordsFromFileProducer", "close()");
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException e) {
                throw new ResourceException(e.getMessage(), e);
            }
        }
    }

}
