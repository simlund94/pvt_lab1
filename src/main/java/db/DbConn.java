package db;

import java.sql.*;

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

    public ResultSet executeQuery(String sqlString) throws SQLException {
        return this.getStatement(this.getConnection()).executeQuery(sqlString);
    }

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