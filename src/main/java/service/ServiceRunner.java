package service;

import db.DbConn;
import repository.DaoFactory;

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

    public <T> T execute(ServiceCommand<T> service) {
        try {
            DbConn.i().open();
            service.init(new DaoFactory());
            return service.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred performing the operation in the database.");
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("The element in question could not be located in the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred trying to close the database connection");
                System.err.println(e.getMessage());
            }
        }
    }
}
