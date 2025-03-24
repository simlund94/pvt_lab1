package service;

import db.DbConn;
import repository.DaoFactory;
import service.logger.Logger;

import java.beans.JavaBean;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * An invoker class that runs ServiceCommand classes, while handling the database connection and resource
 * initialization.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public class ServiceRunner {

    private static final Logger logger = Logger.get(ServiceRunner.class);

    public <T> T execute(ServiceCommand<T> service) {
        try {
            DbConn.i().open();
            service.init();
            return service.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new CleaningManagerServiceException("An error occurred performing the operation in the database.");
        } catch (NoSuchElementException e) {
            logger.error(e);
            throw new CleaningManagerServiceException("The element in question could not be located in the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }
}
