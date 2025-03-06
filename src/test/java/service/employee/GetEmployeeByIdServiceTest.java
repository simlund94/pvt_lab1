package service.employee;

import db.DbConn;
import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DaoFactory;
import repository.DaoFactory.*;
import repository.EmployeeDao;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mocked unit test suite for the GetEmployeeByIdService command class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class GetEmployeeByIdServiceTest {

    Employee employeeToTest;
    Employee employeeReturned;

    EmployeeDao employeeDaoMock;
    DbConn dbConnMock;
    DaoFactory daoFactoryMock;

    @BeforeEach
    void setUp() {
        employeeToTest = new Employee(1, "Simon Lundgren", 1994);
        employeeReturned = new Employee(1, "Simon Lundgren", 1994);
        employeeDaoMock = mock(EmployeeDao.class);
        daoFactoryMock = mock(DaoFactory.class);
        dbConnMock = mock(DbConn.class);
    }

    @AfterEach
    void tearDown() {
        employeeToTest = null;
        employeeReturned = null;
        employeeDaoMock = null;
        daoFactoryMock = null;
        dbConnMock = null;
    }

    @Test
    void getEmployeeByValidId() throws SQLException {
        when(daoFactoryMock.get(FactoryType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.get(1)).thenReturn(employeeReturned);
        GetEmployeeByIdService service = new GetEmployeeByIdService(1);
        service.init(daoFactoryMock, dbConnMock);
        Employee result = service.execute();

        assertNotNull(result, "The method should not return null");
        assertEquals(employeeToTest, result, "The employee should be the same");

        verify(employeeDaoMock, times(1)).get(1);
    }

    @Test
    void getNonExistingEmployee_ShouldThrowException() throws SQLException {
        when(daoFactoryMock.get(FactoryType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.get(2)).thenThrow(NoSuchElementException.class);
        GetEmployeeByIdService service = new GetEmployeeByIdService(2);
        service.init(daoFactoryMock, dbConnMock);

        assertThrows(
                NoSuchElementException.class,
                () -> service.execute(),
                "Trying to get a non existing employee should throw an exception"
        );

        verify(employeeDaoMock, times(1)).get(2);
    }

    @Test
    void getEmployeeWithNonValidId_ShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GetEmployeeByIdService(-1),
                "Trying to pass a negative id should throw an exception"
        );
    }
}