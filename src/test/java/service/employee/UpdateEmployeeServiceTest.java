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
 * Mocked unit test suite for the UpdateEmployeeService command class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class UpdateEmployeeServiceTest {

    Employee existingEmployee;
    Employee employeeReturned;
    Employee unexistantEmployee;
    EmployeeDao employeeDaoMock;
    DaoFactory daoFactoryMock;

    @BeforeEach
    void setUp() {
        existingEmployee = new Employee(1, "Simon Lundgren", 1994);
        employeeReturned = new Employee(1, "Simon Lundgren", 1994);
        unexistantEmployee = new Employee(2, "Kalle Kaka", 1989);
        employeeDaoMock = mock(EmployeeDao.class);
        daoFactoryMock = mock(DaoFactory.class);
    }

    @AfterEach
    void tearDown() {
        existingEmployee = null;
        employeeReturned = null;
        unexistantEmployee = null;
        employeeDaoMock = null;
        daoFactoryMock = null;
    }

    @Test
    void updateExistingEmployee_ShouldReturnUpdatedEmployee() throws SQLException {
        when(daoFactoryMock.get(DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.update(existingEmployee)).thenReturn(employeeReturned);
        UpdateEmployeeService service = new UpdateEmployeeService(existingEmployee);
        service.init(daoFactoryMock);
        Employee result = service.execute();

        assertEquals(employeeReturned, result, "The employee returned should be equal to the one passed in");

        verify(employeeDaoMock, times(1)).update(existingEmployee);
    }

    @Test
    void updateNonExistantEmployee_ShouldThrowException() throws SQLException {
        when(daoFactoryMock.get(DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.update(unexistantEmployee)).thenThrow(NoSuchElementException.class);
        UpdateEmployeeService service = new UpdateEmployeeService(unexistantEmployee);
        service.init(daoFactoryMock);
        assertThrows(
                NoSuchElementException.class,
                () -> service.execute(),
                "Updating an employee not in the database should throw an exception"
        );
        verify(employeeDaoMock, times(1)).update(unexistantEmployee);
    }

    @Test
    void updateNullEmployee_ShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new UpdateEmployeeService(null),
                "Passing a null employee should throw an exception."
        );
    }
}
