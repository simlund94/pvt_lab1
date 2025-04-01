package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An interface for connecting with a database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-24
 */
public interface DatabaseConnector {

    /**
     * Opens the database connection.
     *
     * @throws SQLException
     */
    void open() throws SQLException;

    /**
     * Closes the database connection.
     *
     * @throws SQLException
     */
    void close() throws SQLException;

    ResultSet executeQuery(String sqlString) throws SQLException;

    PreparedStatement prepareStatement(String statementString) throws SQLException;

}
