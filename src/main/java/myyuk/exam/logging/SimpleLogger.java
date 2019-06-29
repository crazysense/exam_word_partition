package myyuk.exam.logging;

public abstract class SimpleLogger {
    private static final String DEBUG_PREFIX = "[DEBUG]";
    private static final String INFO_PREFIX = "[INFO]";
    private static final String WARN_PREFIX = "[WARN]";
    private static final String ERROR_PREFIX = "[ERROR]";
    private static final String FATAL_PREFIX = "[FATAL]";

    private Class<?> clazz;

    SimpleLogger(Class<?> clazz) {
        this.clazz = clazz;
    }

    private String formatPrefix(String level, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.clazz.getSimpleName()).append(' ')
                .append(level).append(' ')
                .append(message);
        return sb.toString();
    }

    public final void debug(String message, Throwable t) {
        debugImpl(DEBUG_PREFIX + message, t);
    }

    public final void info(String message, Throwable t) {
        infoImpl(INFO_PREFIX + message, t);
    }

    public final void warn(String message, Throwable t) {
        warnImpl(WARN_PREFIX + message, t);
    }

    public final void error(String message, Throwable t) {
        errorImpl(ERROR_PREFIX + message, t);
    }

    public final void fatal(String message, Throwable t) {
        fatalImpl(FATAL_PREFIX + message, t);
    }


    protected abstract void debugImpl(String message, Throwable t);

    protected abstract void infoImpl(String message, Throwable t);

    protected abstract void warnImpl(String message, Throwable t);

    protected abstract void errorImpl(String message, Throwable t);

    protected abstract void fatalImpl(String message, Throwable t);
}
