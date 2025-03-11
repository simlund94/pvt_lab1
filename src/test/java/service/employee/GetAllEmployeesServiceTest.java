package service.employee;

import db.DbConn;
import domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DaoFactory;
import repository.EmployeeDao;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Mocked unit test suite for the GetAllEmployeesService command class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-19
 */
class GetAllEmployeesServiceTest {

    List<Employee> employees;

    EmployeeDao employeeDaoMock;
    DaoFactory daoFactoryMock;

    @BeforeEach
    void setUp() throws SQLException {
        employees = List.of(
                new Employee(1, "Görgen Antonsson", 1971),
                new Employee(2, "Niklas Andersson", 1991));

        employeeDaoMock = mock(EmployeeDao.class);
        daoFactoryMock = mock(DaoFactory.class);
    }

    @Test
    void getAllEmployeesShouldReturnListOfEmployees() throws SQLException {
        when(daoFactoryMock.get(DaoFactory.DaoType.EMPLOYEE)).thenReturn(employeeDaoMock);
        when(employeeDaoMock.getAll()).thenReturn(employees);
        GetAllEmployeesService service = new GetAllEmployeesService();
        service.init(daoFactoryMock);
        List<Employee> result = service.execute();

        assertNotNull(result, "The list returned should not be null");
        assertEquals(2, result.size(), "The list should have size 2");
        assertEquals(result, employees,
                "The list should correspond to the mocked list");

        verify(employeeDaoMock, times(1)).getAll();
    }
}