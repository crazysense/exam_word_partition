package myyuk.exam.logging;

public class ConsoleLogger extends SimpleLogger {

    private static final String LOG_FORMAT = "%s" + System.lineSeparator() + ": %s";

    public ConsoleLogger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void debugImpl(String message, Throwable t) {
        printLog(message, t);
        t.printStackTrace(); // for debugging
    }

    @Override
    protected void infoImpl(String message, Throwable t) {
        printLog(message, t);
    }

    @Override
    protected void warnImpl(String message, Throwable t) {
        printLog(message, t);
    }

    @Override
    protected void errorImpl(String message, Throwable t) {
        printLog(message, t);
    }

    @Override
    protected void fatalImpl(String message, Throwable t) {
        printLog(message, t);
    }

    private void printLog(String message, Throwable t) {
        System.out.println(String.format(LOG_FORMAT, message, t.getMessage()));
    }
}
