package service;

import db.DbConn;
import repository.DaoFactory;

import java.sql.SQLException;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public class ServiceRunner2 {

    public <T> T execute(ServiceCommand<T> service) {
        try {
            DbConn.i().open();
            service.init(new DaoFactory(), DbConn.i());
            return service.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred performing the operation in the database.");
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
