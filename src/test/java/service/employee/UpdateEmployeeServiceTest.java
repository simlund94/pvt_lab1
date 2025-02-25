package service.employee;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EmployeeDao;
import service.CleaningManagerServiceException;

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
    Employee unexistantEmployee;
    EmployeeDao employeeDaoMock;

    @BeforeEach
    void setUp() {
        existingEmployee = new Employee(1, "Simon Lundgren", 1994);
        unexistantEmployee = new Employee(2, "Kalle Kaka", 1989);
        employeeDaoMock = mock(EmployeeDao.class);
    }

    @AfterEach
    void tearDown() {
        existingEmployee = null;
        unexistantEmployee = null;
        employeeDaoMock = null;
    }

    @Test
    void updateExistingEmployee_ShouldReturnUpdatedEmployee() throws SQLException {
        when(employeeDaoMock.update(existingEmployee)).thenReturn(existingEmployee);
        UpdateEmployeeService service = new UpdateEmployeeService(existingEmployee, employeeDaoMock);
        Employee result = service.execute();

        assertEquals(existingEmployee, result, "The result should be true");

        verify(employeeDaoMock, times(1)).update(existingEmployee);
    }

    @Test
    void updateNonExistantEmployee_ShouldThrowException() throws SQLException {
        when(employeeDaoMock.update(unexistantEmployee)).thenThrow(NoSuchElementException.class);
        UpdateEmployeeService service = new UpdateEmployeeService(unexistantEmployee, employeeDaoMock);
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
                () -> new UpdateEmployeeService(null, employeeDaoMock),
                "Passing a null employee should throw an exception."
        );
    }
}
