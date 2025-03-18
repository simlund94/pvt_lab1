package db;

import jakarta.annotation.Resource;

import java.sql.*;

/**
 * Database connection singleton, modified from Åke Wallins version.
 *
 * @author Simon Lundgren
 */
public class DbConn {

    private static DbConn instance;

    private Connection connection = null;
    private Statement statement = null;

    /**
     * Counter to track how many consecutive times the database has been opened, so that
     * the same number of closes are required to close it.
     */
    private byte openRequests;

    private static final String DB_NAME = "25simonl";
    private static final String USER = "25simonl";
    private static final String PASSWORD = "simonlpwd";
    private static final String CONNECTION_URL = "jdbc:mysql://node96052-mysql.jls-sto3.elastx.net:11107/"
            + DB_NAME;

    private DbConn() {
    }

    public static DbConn i() {
        if (instance == null) {
            instance = new DbConn();
        }
        return instance;
    }

    /**
     * Returns the connection to the database.
     *
     * @return A Connection object to the database specified by the static credential fields in the singleton.
     */
    private Connection getConnection() {
        return connection;
    }

    private Statement getStatement(Connection connection) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Could not create Statement");
            System.err.println(e.getMessage());
        }
        return statement;
    }

    /**
     * Executes the SQL query towards the singletons database, and returns a ResultSet.
     *
     * @param sqlString the query to be executed
     * @return ResultSet containing the result from the database
     * @throws SQLException if a database error occurs
     */
    public ResultSet executeQuery(String sqlString) throws SQLException {
        return this.getStatement(this.connection).executeQuery(sqlString);
    }

    /**
     * A wrapper for the prepareStatement method set to always return auto-generated keys. Returns
     * a PreparedStatement object for interacting with the database from the passed SQL string.
     *
     * @param statementString
     * @return A PreparedStatement object
     * @throws SQLException if a database error occurs
     */
    public PreparedStatement prepareStatement(String statementString)
            throws SQLException {
        return this.getConnection().prepareStatement(statementString, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Opens the database connection. The amount of calls to open is tracked, and an
     * equal number of calls to close is needed to actually close the connection.
     *
     * @throws SQLException if a database error occurs
     */
    public void open() throws SQLException {
        openRequests++;
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        }
    }

    /**
     * Closes the database connection. The amount of calls to open is tracked, and an
     * equal number of calls to close is needed to actually close the connection.
     *
     * @throws SQLException if a database error occurs
     */
    public void close() throws SQLException {
        if (openRequests > 0) {
            openRequests--;
        }

        if (openRequests == 0) {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }
    }

}