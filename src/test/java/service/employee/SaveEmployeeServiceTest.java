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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mocked unit test suite for the UpdateEmployeeService command class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
class SaveEmployeeServiceTest {

    Employee employeeToSave;
    Employee employeeReturn;
    EmployeeDao employeeDaoMock;
    DaoFactory daoFactoryMock;
    DbConn dbConnMock;

    @BeforeEach
    void setUp() {
        employeeToSave = new Employee("Simon Lundgren", 1994);
        employeeReturn = new Employee(1, "Simon Lundgren", 1994);
        employeeDaoMock = mock(EmployeeDao.class);
        daoFactoryMock = mock(DaoFactory.class);
        dbConnMock = mock(DbConn.class);
    }

    @AfterEach
    void tearDown() {
        employeeToSave = null;
        employeeReturn = null;
        employeeDaoMock = null;
        daoFactoryMock = null;
        dbConnMock = null;

    }

    @Test
    void saveValidEmployee_ShouldReturnFullEmployee() throws SQLException {
        when(daoFactoryMock.get(DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.save(employeeToSave)).thenReturn(employeeReturn);
        SaveEmployeeService service = new SaveEmployeeService(employeeToSave);
        service.init(daoFactoryMock, dbConnMock);
        Employee result = service.execute();

        assertEquals(employeeReturn, result, "Employee returned is not the same as expected");

        verify(employeeDaoMock, times(1)).save(employeeToSave);
    }

    @Test
    void saveNullEmployee_ShouldThrowException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SaveEmployeeService(null),
                "Passing a null employee should throw an exception"
        );
    }
}