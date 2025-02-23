package service.employee;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EmployeeDao;

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
    void updateExistingEmployee_ShouldReturnTrue() {
        when(employeeDaoMock.update(existingEmployee)).thenReturn(true);
        UpdateEmployeeService service = new UpdateEmployeeService(existingEmployee, employeeDaoMock);
        boolean result = service.execute();

        assertTrue(result, "The result should be true");

        verify(employeeDaoMock, times(1)).update(existingEmployee);
    }

    @Test
    void updateNonExistantEmployee_ShouldReturnFalse() {
        when(employeeDaoMock.update(unexistantEmployee)).thenReturn(false);
        UpdateEmployeeService service = new UpdateEmployeeService(unexistantEmployee, employeeDaoMock);
        boolean result = service.execute();

        assertFalse(result, "The result should be false");

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
