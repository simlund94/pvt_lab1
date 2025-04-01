package service.logger;


import java.util.function.Supplier;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-18
 */
public interface Logger {

    /**
     * Logs a message at the info level with the given Supplier.
     *
     * @param messageSupplier The Supplier, carrying a String
     */
    void info(Supplier<String> messageSupplier);

    /**
     * Logs a message at the debug level with the given Supplier.
     *
     * @param messageSupplier The Supplier, carrying a string
     */
    void debug(Supplier<String> messageSupplier);

    /**
     * Logs an exception at the error level.
     *
     * @param ex The exception that was thrown
     */
    void error(Throwable ex);

    /**
     * The current implementation of Logger in use in the application.
     *
     * @param clazz The class where the logger is situated, to provide
     * @return
     */
    static Logger get(Class<?> clazz) {
        return new Log4J(clazz);
    }
}

