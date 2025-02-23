package repository;

import db.DbConn;
import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mocked test suite for the EmployeeDao class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class EmployeeDaoTest {

    EmployeeDao employeeDao;

    DbConn dbConnMock;
    PreparedStatement preparedStatementMock;
    ResultSet resultSetMock;

    Employee employee1;
    Employee employee2;
    Employee employee1WithoutId;

    final int negativeId = -1;

    @BeforeEach
    void setUp() {
        dbConnMock = mock(DbConn.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);
        employeeDao = new EmployeeDao(dbConnMock);

        employee1 = new Employee(1, "Simon Lundgren", 1994);
        employee2 = new Employee(2, "Anders Andersson", 1991);
        employee1WithoutId = new Employee("Simon Lundgren", 1994);
    }

    @AfterEach
    void tearDown() {
        dbConnMock = null;
        preparedStatementMock = null;
        resultSetMock = null;
        employeeDao = null;

        employee1 = null;
        employee2 = null;
        employee1WithoutId = null;
    }

    @Test
    void getEmployeeSuccessfully() throws SQLException {
        String query = "SELECT id, name, birth_year FROM lab_employees WHERE id = ?";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true); // simulating employee found
        when(resultSetMock.getInt("id")).thenReturn(employee1.getId());
        when(resultSetMock.getString("name")).thenReturn(employee1.getName());
        when(resultSetMock.getInt("birth_year")).thenReturn(employee1.getBirthYear());

        Employee result = employeeDao.get(employee1.getId());

        assertNotNull(result, "The result should not be null");
        assertEquals(employee1, result, "The employee should be equal to " + employee1);
        verify(preparedStatementMock, times(1)).getResultSet();
        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(resultSetMock, times(1)).next();
        verify(resultSetMock, times(1)).getInt("id");
        verify(resultSetMock, times(1)).getString("name");
        verify(resultSetMock, times(1)).getInt("birth_year");
    }

    @Test
    void getEmployeeWithNegativeId_ShouldThrowException() throws SQLException {
        assertThrows(
                IllegalArgumentException.class,
                () -> employeeDao.get(negativeId),
                "Passing a negative id to the employeeDao should throw an exception"
        );
    }

    @Test
    void getEmployeeNotInDatabase_ShouldThrowException() throws SQLException {
        String query = "SELECT id, name, birth_year FROM lab_employees WHERE id = ?";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        assertThrows(
                NoSuchElementException.class,
                () -> employeeDao.get(3),
                "Retrieving an element not in the database should throw an exception"
        );
        verify(preparedStatementMock, times(1)).getResultSet();
        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(resultSetMock, times(1)).next();
    }

    @Test
    void updateEmployeeSuccessfully() throws SQLException {
        String query = "UPDATE lab_employees SET name = ? WHERE id = ?";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        employee1.setName("Anders Lundgren");
        boolean result = employeeDao.update(employee1);

        assertTrue(result, "Should return true for successful update.");
        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(preparedStatementMock, times(1)).setString(1, employee1.getName());
        verify(preparedStatementMock, times(1)).setInt(2, employee1.getId());
        verify(preparedStatementMock, times(1)).executeUpdate();
    }

    @Test
    void updateEmployeeUnsuccessfully() throws SQLException {
        String query = "UPDATE lab_employees SET name = ? WHERE id = ?";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(0);

        employee1.setName("Anders Lundgren");
        boolean result = employeeDao.update(employee1);

        assertFalse(result, "Should return true for successful update.");
        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(preparedStatementMock, times(1)).setString(1, employee1.getName());
        verify(preparedStatementMock, times(1)).setInt(2, employee1.getId());
        verify(preparedStatementMock, times(1)).executeUpdate();
    }

    @Test
    void updateNullEmployee_ShouldThrowException() throws SQLException {
        assertThrows(
                IllegalArgumentException.class,
                () -> employeeDao.update(null),
                "Trying to update a null employee should throw an exception"
        );
    }

    @Test
    void deleteEmployeeSuccessfully() throws SQLException {
        String query = "DELETE FROM lab_employees WHERE id = ?";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1); // one row affected

        boolean result = employeeDao.delete(employee1);

        assertTrue(result, "Should return true for successful deletion.");
        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(preparedStatementMock, times(1)).setInt(1, employee1.getId());
        verify(preparedStatementMock, times(1)).executeUpdate();
    }

    @Test
    void deleteEmployeeUnsuccessfully() throws SQLException {
        String query = "DELETE FROM lab_employees WHERE id = ?";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(0); // one row affected

        boolean result = employeeDao.delete(employee1);

        assertFalse(result, "Should return true for successful deletion.");
        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(preparedStatementMock, times(1)).setInt(1, employee1.getId());
        verify(preparedStatementMock, times(1)).executeUpdate();
    }

    @Test
    void deleteNullEmployee_ShouldThrowException() throws SQLException {
        assertThrows(
                IllegalArgumentException.class,
                () -> employeeDao.delete(null),
                "Trying to delete a null employee should throw an exception"
        );
    }

    @Test
    void saveNullEmployee_ShouldThrowException() throws SQLException {
        assertThrows(
                IllegalArgumentException.class,
                () -> employeeDao.save(null),
                "Trying to save null should throw an exception"
        );
    }

    @Test
    void saveEmployeeSuccessfully() throws SQLException {
        String query = "INSERT INTO lab_employees(name, birth_year) VALUES(?, ?)";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(1);

        Employee result = employeeDao.save(employee1WithoutId);

        assertNotNull(result, "The result should not be null");
        assertEquals(employee1, result, "The returned employee should be " + employee1);

        verify(dbConnMock, times(1)).prepareStatement(query);
        verify(preparedStatementMock, times(1)).getGeneratedKeys();
        verify(resultSetMock, times(1)).next();
        verify(resultSetMock, times(1)).getInt(1);
    }

    @Test
    void getAllEmployeesSuccessfully() throws SQLException {
        String query = "SELECT id, name, birth_year FROM lab_employees";

        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, true, false); // simulating two rows found
        when(resultSetMock.getInt("id")).thenReturn(employee1.getId(), employee2.getId());
        when(resultSetMock.getString("name")).thenReturn(employee1.getName(), employee2.getName());
        when(resultSetMock.getInt("birth_year")).thenReturn(employee1.getBirthYear(), employee2.getBirthYear());

        List<Employee> employees = employeeDao.getAll();

        assertEquals(2, employees.size(), "The List returned should be of size 2");
        assertEquals(employee1, employees.get(0), "The first employee should be " + employee1);
        assertEquals(employee2, employees.get(1), "The second employee should be " + employee2);

        verify(dbConnMock).prepareStatement(query);
        verify(preparedStatementMock, times(1)).getResultSet();
        verify(resultSetMock, times(3)).next();
        verify(resultSetMock, times(2)).getInt("id");
        verify(resultSetMock, times(2)).getString("name");
        verify(resultSetMock, times(2)).getInt("birth_year");
    }

    @Test
    void getAllEmployees_NoEmployeesInDatabase() throws SQLException {
        String query = "SELECT id, name, birth_year FROM lab_employees";
        when(dbConnMock.prepareStatement(query)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false); // no rows found

        List<Employee> employees = employeeDao.getAll();

        assertNotNull(employees, "An empty database should return an empty list.");
        assertEquals(0, employees.size());

        verify(dbConnMock).prepareStatement(query);
        verify(preparedStatementMock, times(1)).getResultSet();
        verify(resultSetMock, atMostOnce()).next();
        verify(resultSetMock, never()).getInt("id");
        verify(resultSetMock, never()).getString("name");
        verify(resultSetMock, never()).getInt("birth_year");
    }

}