package service.employee;

import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    EmployeeDao employeeDaoMock;

    @BeforeEach
    void setUp() {
        existingEmployee = new Employee(1, "Simon Lundgren", 1994);
        nonExistantEmployee = new Employee(2, "Kalle Kaka", 1988);
        employeeDaoMock = mock(EmployeeDao.class);
    }

    @AfterEach
    void tearDown() {
        existingEmployee = null;
        nonExistantEmployee = null;
        employeeDaoMock = null;
    }

    @Test
    void deleteExistingEmployee() throws SQLException {
        when(employeeDaoMock.delete(existingEmployee)).thenReturn(true);
        DeleteEmployeeService service = new DeleteEmployeeService(existingEmployee, employeeDaoMock);
        boolean result = service.execute();

        assertTrue(result, "Delete employee should return true");

        verify(employeeDaoMock, times(1)).delete(existingEmployee);
    }

    @Test
    void deleteNonExistentEmployee() throws SQLException {
        when(employeeDaoMock.delete(nonExistantEmployee)).thenReturn(false);
        DeleteEmployeeService service = new DeleteEmployeeService(nonExistantEmployee, employeeDaoMock);
        boolean result = service.execute();

        assertFalse(result, "Delete employee should return false");

        verify(employeeDaoMock, times(1)).delete(nonExistantEmployee);
    }

    @Test
    void deleteNullEmployee_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> new DeleteEmployeeService(null, employeeDaoMock),
                "Trying to delete a null employee should throw an exception"
        );
    }
}