package service.employee;

import domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EmployeeDao;

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

    @BeforeEach
    void setUp() {
        employees = List.of(
                new Employee(1, "Görgen Antonsson", 1971),
                new Employee(2, "Niklas Andersson", 1991));

        employeeDaoMock = mock(EmployeeDao.class);
        when(employeeDaoMock.getAll()).thenReturn(employees);
    }

    @Test
    void getAllEmployees() {
        GetAllEmployeesService instance = new GetAllEmployeesService(employeeDaoMock);
        List<Employee> result = instance.execute();

        assertNotNull(result, "The list returned should not be null");
        assertEquals(2, result.size(), "The list should have size 2");
        assertEquals(result, employees,
                "The list should correspond to the mocked list");

        verify(employeeDaoMock, times(1)).getAll();

    }
}