package service.employee;

import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EmployeeDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class SaveEmployeeServiceTest {

    Employee employeeToSave;
    Employee employeeReturn;

    @BeforeEach
    void setUp() {
        employeeToSave = new Employee("Simon Lundgren", 1994);
        employeeReturn = new Employee(1, "Simon Lundgren", 1994);
    }

    @AfterEach
    void tearDown() {
        employeeToSave = null;
        employeeReturn = null;
    }

    @Test
    void saveValidEmployeeShouldReturnFullEmployee() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        when(employeeDaoMock.save(employeeToSave)).thenReturn(employeeReturn);
        SaveEmployeeService service = new SaveEmployeeService(employeeToSave, employeeDaoMock);
        Employee result = service.execute();

        assertEquals(employeeReturn, result, "Employee returned is not the same as expected");

        verify(employeeDaoMock, times(1)).save(employeeToSave);
    }

    @Test
    void saveNullEmployeeShouldThrowException() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        assertThrows(
                IllegalArgumentException.class,
                () -> new SaveEmployeeService(null, employeeDaoMock),
                "Passing a null employee should throw an exception"
        );
    }
}