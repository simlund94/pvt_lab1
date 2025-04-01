package service.logger;


import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;

/**
 * An implementation of the Cleaning Manager Logger Interface using Log4J.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-18
 */
public class Log4J implements Logger {

    private final org.apache.logging.log4j.Logger logger;

    public Log4J(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz.getName());
    }

    public void debug(Supplier<String> messageSupplier) {
        logger.debug(messageSupplier.get());
    }

    @Override
    public void info(Supplier<String> messageSupplier) {
        logger.info(messageSupplier.get());
    }

    @Override
    public void error(Throwable ex) {
        logger.error(ex);
    }
}
