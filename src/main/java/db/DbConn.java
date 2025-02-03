package db;

import java.sql.*;

/**
 * Database connection singleton, modified from Åke Wallin version.
 */
public class DbConn {

    private static DbConn instance;

    private Connection connection = null;
    private Statement statement = null;

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
     * Returns a connection to the database, will automatically re-establish connection if
     * the connection hasn't yet been opened, or if it has been closed/timed-out since last time.
     *
     * @return A Connection object to the database specified by the static credential fields in the singleton.
     */
    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
                System.out.format("Connected to %s database successfully!\n", DB_NAME);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        return this.getStatement(this.getConnection()).executeQuery(sqlString);
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

    public void close() {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
            System.out.println("DB Connection closed");
        } catch (SQLException e) {
            System.err.println("Could not close the statement or the connection");
            System.err.println(e.getMessage());
        }
    }

}