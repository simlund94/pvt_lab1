package repository;

import db.DbConn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class EmployeeDaoTest {

    EmployeeDao employeeDao;

    DbConn dbConnMock;
    PreparedStatement preparedStatementMock;
    ResultSet resultSetMock;

    @BeforeEach
    void setUp() {
        dbConnMock = mock(DbConn.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);

        employeeDao = new EmployeeDao(dbConnMock);
    }

    @AfterEach
    void tearDown() {
        dbConnMock = null;
        preparedStatementMock = null;
        resultSetMock = null;
    }

    @Test
    void getAllEmployees() throws SQLException {
        String query = "SELECT (id, name, birthYear) FROM employees";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);


    }
}