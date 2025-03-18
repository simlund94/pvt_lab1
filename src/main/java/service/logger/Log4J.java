package service.logger;


import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-18
 */
public class Log4J implements Logger {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("default");

    @Override
    public void info(Supplier<String> messageSupplier) {
        logger.info(messageSupplier);
    }

    @Override
    public void error(Throwable ex) {
        logger.error(ex);
    }
}
