package service.employee;

import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EmployeeDao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class DeleteEmployeeServiceTest {

    Employee existingEmployee;
    Employee nonExistantEmployee;

    @BeforeEach
    void setUp() {
        existingEmployee = new Employee(1, "Simon Lundgren", 1994);
        nonExistantEmployee = new Employee(2, "Kalle Kaka", 1988);
    }

    @AfterEach
    void tearDown() {
        existingEmployee = null;
        nonExistantEmployee = null;
    }

    @Test
    void deleteExistingEmployee() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        when(employeeDaoMock.delete(existingEmployee)).thenReturn(true);
        DeleteEmployeeService service = new DeleteEmployeeService(existingEmployee, employeeDaoMock);
        boolean result = service.execute();

        assertTrue(result, "Delete employee should return true");

        verify(employeeDaoMock, times(1)).delete(existingEmployee);
    }

    @Test
    void deleteNonExistentEmployee() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        when(employeeDaoMock.delete(nonExistantEmployee)).thenReturn(false);
        DeleteEmployeeService service = new DeleteEmployeeService(nonExistantEmployee, employeeDaoMock);
        boolean result = service.execute();

        assertFalse(result, " Delete employee should return false");

        verify(employeeDaoMock, times(1)).delete(nonExistantEmployee);
    }
}