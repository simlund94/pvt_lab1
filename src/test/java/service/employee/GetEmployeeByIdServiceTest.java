package service.employee;

import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EmployeeDao;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class GetEmployeeByIdServiceTest {

    Employee employeeToTest;

    @BeforeEach
    void setUp() {
        employeeToTest = new Employee(1, "Simon Lundgren", 1994);
    }

    @AfterEach
    void tearDown() {
        employeeToTest = null;
    }

    @Test
    void getEmployeeByValidId() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        when(employeeDaoMock.get(1)).thenReturn(employeeToTest);
        GetEmployeeByIdService service = new GetEmployeeByIdService(1, employeeDaoMock);
        Employee result = service.execute();

        assertNotNull(result, "The method should not return null");
        assertEquals(employeeToTest, result, "The employee should be the same");

        verify(employeeDaoMock, times(1)).get(1);
    }

    @Test
    void getNonExistingEmployeeShouldThrowException() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        when(employeeDaoMock.get(2)).thenThrow(NoSuchElementException.class);
        GetEmployeeByIdService service = new GetEmployeeByIdService(2, employeeDaoMock);

        assertThrows(
                NoSuchElementException.class,
                () -> service.execute(),
                "Trying to get a non existing employee should throw an exception"
        );

        verify(employeeDaoMock, times(1)).get(2);
    }

    @Test
    void getEmployeeWithNonValidIdShouldThrowException() {
        EmployeeDao employeeDaoMock = mock(EmployeeDao.class);
        assertThrows(
                IllegalArgumentException.class,
                () -> new GetEmployeeByIdService(-1, employeeDaoMock),
                "Trying to pass a negative id should throw an exception"
        );
    }
}