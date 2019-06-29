package myyuk.exam;

import myyuk.exam.option.Option;
import myyuk.exam.option.OptionBuilder;
import myyuk.exam.stream.Stream;
import myyuk.exam.stream.StreamBuilder;

public class WordApplication {
    private static final int ERROR_CODE = -1;

    public static void main(String[] args) {
        Option option = OptionBuilder.of(args).build();
        if (option == null) {
            System.exit(ERROR_CODE);
        }

        Stream<String> stream = StreamBuilder.of(option).build();
        stream.start();
        stream.shutdown();
    }
}
