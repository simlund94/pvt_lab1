package service.employee;

import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.DaoFactory;
import repository.DaoFactory.*;
import repository.EmployeeDao;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    Optional<Employee> optionalReturned;

    EmployeeDao employeeDaoMock;
    DaoFactory daoFactoryMock;

    @BeforeEach
    void setUp() {
        employeeToTest = new Employee(1, "Simon Lundgren", 1994);
        employeeReturned = new Employee(1, "Simon Lundgren", 1994);
        employeeDaoMock = mock(EmployeeDao.class);
        daoFactoryMock = mock(DaoFactory.class);
        optionalReturned = mock(Optional.class);
    }

    @AfterEach
    void tearDown() {
        employeeToTest = null;
        employeeReturned = null;
        employeeDaoMock = null;
        daoFactoryMock = null;
        optionalReturned = null;
    }

    @Test
    void getEmployeeByValidId() throws SQLException {
        when(daoFactoryMock.get(DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(optionalReturned.orElseThrow()).thenReturn(employeeReturned);
        when(employeeDaoMock.get(1)).thenReturn(optionalReturned);
        GetEmployeeByIdService service = new GetEmployeeByIdService(1);
        service.init(daoFactoryMock);
        Employee result = service.execute();

        assertNotNull(result, "The method should not return null");
        assertEquals(employeeToTest, result, "The employee should be the same");

        verify(employeeDaoMock, times(1)).get(1);
    }

    @Test
    void getNonExistingEmployee_ShouldThrowException() throws SQLException {
        when(daoFactoryMock.get(DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.get(2)).thenThrow(NoSuchElementException.class);
        GetEmployeeByIdService service = new GetEmployeeByIdService(2);
        service.init(daoFactoryMock);

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