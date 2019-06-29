package myyuk.exam.producer;

import myyuk.exam.configuration.Configurable;
import myyuk.exam.option.Option;
import myyuk.exam.option.OptionConstants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
            // TODO : Logging Error.
            this.endOfStream = true;
        }
        return readLine;
    }

    @Override
    public void open() {
        try {
            this.reader = new BufferedReader(new FileReader(this.readFilePath));
        } catch (FileNotFoundException e) {
            // TODO : Logging Error.
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        System.out.println("close-producer");
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException e) {
                // TODO : Logging Error.
                e.printStackTrace();
            }
        }
    }

}
