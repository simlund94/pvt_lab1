package service.logger;


import java.util.function.Supplier;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-18
 */
public class Log4J implements Logger {

    private static Logger logger;

    public static Logger i() {
        if (logger == null) {
            logger = new Log4J();
        }
        return logger;
    }

    @Override
    public void info(Supplier<String> messageSupplier) {
    }

    @Override
    public void error(Throwable ex) {

    }
}
