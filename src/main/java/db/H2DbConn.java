package db;

import org.h2.jdbcx.JdbcDataSource;
import service.logger.Logger;

import java.sql.*;

/**
 * Database connection class for connecting to an in-memory H2 database, used for integration testing.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-24
 */
public class H2DbConn implements DatabaseConnector {

    private Connection connection = null;
    private Statement statement = null;

    private byte openRequests;

    private static final String USER = "admin";
    private static final String PASSWORD = "";
    private static final String CONNECTION_URL = "jdbc:h2:mem:;MODE=MYSQL";

    private static final Logger LOGGER = Logger.get(H2DbConn.class);

    @Override
    public void open() throws SQLException {
        openRequests++;
        if (connection == null || connection.isClosed()) {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(CONNECTION_URL);
            ds.setUser(USER);
            ds.setPassword(PASSWORD);
            connection = ds.getConnection();
            LOGGER.debug(() -> "Database connection opened");
        }
    }

    @Override
    public void close() throws SQLException {
        if (openRequests > 0) {
            openRequests--;
        } else if (openRequests == 0) {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
            LOGGER.debug(() -> "Database connection closed");
        }
    }

    @Override
    public ResultSet executeQuery(String sqlString) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String statementString) throws SQLException {
        return this.connection.prepareStatement(statementString, Statement.RETURN_GENERATED_KEYS);
    }

    private Statement getStatement(Connection connection) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return statement;
    }
}
