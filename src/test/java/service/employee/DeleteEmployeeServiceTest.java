package service.employee;

import db.DbConn;
import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DaoFactory;
import repository.EmployeeDao;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mocked unit test suite for the DeleteEmployeeService command class.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
class DeleteEmployeeServiceTest {

    Employee existingEmployee;
    Employee nonExistantEmployee;

    DaoFactory daoFactoryMock;
    EmployeeDao employeeDaoMock;

    @BeforeEach
    void setUp() {
        existingEmployee = new Employee(1, "Simon Lundgren", 1994);
        nonExistantEmployee = new Employee(2, "Kalle Kaka", 1988);
        daoFactoryMock = mock(DaoFactory.class);
        employeeDaoMock = mock(EmployeeDao.class);
    }

    @AfterEach
    void tearDown() {
        existingEmployee = null;
        nonExistantEmployee = null;
        daoFactoryMock = null;
        employeeDaoMock = null;
    }

    @Test
    void deleteExistingEmployee() throws SQLException {
        when(daoFactoryMock.get(DaoFactory.DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.delete(existingEmployee)).thenReturn(true);
        DeleteEmployeeService service = new DeleteEmployeeService(existingEmployee);
        service.init(daoFactoryMock);
        boolean result = service.execute();

        assertTrue(result, "Deleting an existing employee should return true");
        verify(employeeDaoMock, times(1)).delete(existingEmployee);
    }

    @Test
    void deleteNonExistentEmployee() throws SQLException {
        when(daoFactoryMock.get(DaoFactory.DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.delete(nonExistantEmployee)).thenReturn(false);
        DeleteEmployeeService service = new DeleteEmployeeService(nonExistantEmployee);
        service.init(daoFactoryMock);
        boolean result = service.execute();

        assertFalse(result, "Deleting a nonexistant employee should return false");
        verify(employeeDaoMock, times(1)).delete(nonExistantEmployee);
    }

    @Test
    void deleteNullEmployee_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> new DeleteEmployeeService(null),
                "Trying to delete a null employee should throw an exception"
        );
    }
}