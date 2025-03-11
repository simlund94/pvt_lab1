package service;

import db.DbConn;
import repository.DaoFactory;

import java.sql.SQLException;

/**
 * A base class to centralise common fields and operations for the Service Command classes.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public abstract class BaseService<T> implements ServiceCommand<T> {

    protected DaoFactory daoFactory;

    @Override
    public void init(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public final T execute() throws SQLException {
        checkResources();
        return executeImplementation();
    }

    /**
     * The implementation of the command operation, to be implemented by all inheriting  classes.
     * This method is in turn called in the body of the {@link #execute()} method with checks
     * that the proper resources have been initialized.
     *
     * @return The result of the operation
     * @throws SQLException if a database error occurs
     */
    protected abstract T executeImplementation() throws SQLException;

    private void checkResources() {
        if (daoFactory == null) {
            throw new IllegalStateException("DaoFactory was null. The init() method must be called correctly before calling execute()");
        }
    }

}
