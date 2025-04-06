package repository;

import db.DatabaseConnector;
import db.DbConn;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Parent class for all DAO classes. Contains the database connector and
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-04-01
 */
public abstract class BaseDao<T> implements Dao<T> {

    protected final DatabaseConnector dbConn;

    public BaseDao() {
        this.dbConn = DbConn.i();
    }

    public BaseDao(DatabaseConnector dbConn) {
        this.dbConn = dbConn;
    }

    /**
     * Maps the ResultSet returned from the database to the domain entity.
     *
     * @param resultSet the ResultSet returned from the database call
     * @return The domain entity constructed from the ResultSet
     * @throws SQLException if a database error occurs
     */
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
