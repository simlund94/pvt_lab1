package service.logger;


import java.util.function.Supplier;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-18
 */
public interface Logger {

    void info(Supplier<String> messageSupplier);
    void error(Throwable ex);

    static Logger get() {
        return new Log4J();
    }
}

