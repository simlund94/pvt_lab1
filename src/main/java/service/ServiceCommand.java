package service;

import db.DbConn;
import repository.DaoFactory;

import java.sql.SQLException;

/**
 * Interface to define the behaviour of a Command pattern class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-24
 */
public interface ServiceCommand<T> {

    /**
     * Executes the implemented operation, and returns the result of the operation.
     *
     * @return The return value of the operation
     */
    T execute() throws SQLException;

    void init(DaoFactory daoFactory, DbConn dbConn);
}
