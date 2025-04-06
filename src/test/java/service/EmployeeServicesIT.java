package service;

import db.DatabaseConnector;
import db.H2DbConn;
import domain.Employee;
import org.junit.jupiter.api.*;
import service.employee.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test of the Employee service command classes using an H2-database, representing the basic
 * CRUD operation that can be employed on the Employee class in the application from
 * the service layer.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-24
 */
class EmployeeServicesIT {

    ServiceRunner runner;
    DatabaseConnector h2DbConn;

    Employee employeeNoId;
    Employee employee;

    final int idNotInDatabase = 10;
    final List<Employee> employeesInTestDatabase = List.of(
            new Employee(1, "Simon Lundgren", 1994),
            new Employee(2, "Kent Beck", 1968),
            new Employee(3, "Martin Fowler", 1956),
            new Employee(4, "Nicholas Cage", 1962));

    @BeforeEach
    void setUp() throws SQLException {
        h2DbConn = new H2DbConn();
        h2DbConn.open();
        initializeDatabaseSchema();
        runner = new ServiceRunner(h2DbConn);

        employee = new Employee(1, "Donny Devito", 1960);
        employeeNoId = new Employee("Donny Devito", 1960);
    }

    @AfterEach
    void tearDown() throws SQLException {
        h2DbConn.close();
        h2DbConn = null;
        runner = null;

        employee = null;
        employeeNoId = null;
    }

    @Test
    void saveAndGetEmployeeSuccessfully() {
        ServiceCommand<Employee> saveEmployee = new SaveEmployeeService(employeeNoId);
        Employee employeeSaved = runner.execute(saveEmployee);
        assertNotNull(employeeSaved);
        assertEquals(employee, employeeSaved,
                "The employee returned from the database save doesn't match the expected employee");

        GetEmployeeByIdService getEmployee = new GetEmployeeByIdService(employeeSaved.getId());
        Employee employeeRetrieved = runner.execute(getEmployee);

        assertNotNull(employeeRetrieved);
        assertEquals(employee, employeeRetrieved,
                "The Employee retrieved doesn't match the employee saved.");
    }

    @Test
    void getNonExistantEmployee_ShouldThrowException() {
        ServiceCommand<Employee> getEmployee = new GetEmployeeByIdService(idNotInDatabase);

        assertThrows(CleaningManagerServiceException.class,
                () -> runner.execute(getEmployee),
                "Trying to retrieve an id not in the database should throw an exception");
    }

    @Test
    void getAllEmployeesSuccessfully() throws SQLException {
        initializeTestData();
        ServiceCommand<List<Employee>> getAllEmployees = new GetAllEmployeesService();
        List<Employee> employeesRetrieved = runner.execute(getAllEmployees);

        assertNotNull(employeesRetrieved);
        assertEquals(employeesInTestDatabase.size(), employeesRetrieved.size());
        assertEquals(employeesInTestDatabase, employeesRetrieved,
                "The employees retrieved should match the fixture.");
    }

    @Test
    void getAllEmployeesFromEmptyDatabase_ShouldReturnEmptyList() {
        ServiceCommand<List<Employee>> getAllEmployees = new GetAllEmployeesService();
        List<Employee> employeesRetrieved = runner.execute(getAllEmployees);

        assertNotNull(employeesRetrieved);
        assertEquals(employeesRetrieved, Collections.emptyList(),
                "If the database contains no employees, it should return an empty list");
    }

    @Test
    void updateEmployeeSuccessfully() {
        ServiceCommand<Employee> saveEmployee = new SaveEmployeeService(employeeNoId);
        Employee employeeToUpdate = runner.execute(saveEmployee);
        employeeToUpdate.setName("Bobby Runner");
        UpdateEmployeeService updateEmployee = new UpdateEmployeeService(employeeToUpdate);
        Employee employeeUpdated = runner.execute(updateEmployee);

        assertEquals(employeeToUpdate, employeeUpdated,
                "The updated employee object and the one retrieved from the db should be equal");
    }

    @Test
    void updateNonExistantEmployee_ShouldThrowException() {
        ServiceCommand<Employee> updateEmployee = new UpdateEmployeeService(employee);
        assertThrows(CleaningManagerServiceException.class,
                () -> runner.execute(updateEmployee),
                "Updating a non-existant employee should throw an exception");
    }

    @Test
    void deleteEmployeeSuccessfully() throws SQLException {
        initializeTestData();
        ServiceCommand<Boolean> deleteEmployee = new DeleteEmployeeService(employeesInTestDatabase.get(3));
        boolean deleteSuccessful = runner.execute(deleteEmployee);
        List<Employee> employeesRetrieved = runner.execute(new GetAllEmployeesService());

        assertTrue(deleteSuccessful, "A successful delete operation should return true");
        assertFalse(employeesRetrieved.contains(employeesInTestDatabase.get(3)),
                "The deleted employee should not be present anymore.");
    }

    @Test
    void deleteNonExistantEmployee_ShouldReturnFalse() {
        ServiceCommand<Boolean> deleteEmployee = new DeleteEmployeeService(employee);
        boolean deleteSuccessful = runner.execute(deleteEmployee);
        assertFalse(deleteSuccessful, "Deleting an employee not in the database should return false");
    }

    @Test
    void deleteEmployeeTwice_SecondAttemptShouldReturnFalse() throws SQLException {
        initializeTestData();
        Employee employeeToDelete = employeesInTestDatabase.getFirst();
        ServiceCommand<Boolean> service = new DeleteEmployeeService(employeeToDelete);
        assertTrue(runner.execute(service));
        assertFalse(runner.execute(service), "The second attempt to delete the same employee should return false");
    }

    // Kör ett SQL-script som populerar H2-databasen med testdata när det är nödvändigt
    private void initializeTestData() throws SQLException {
        h2DbConn.prepareStatement("RUNSCRIPT FROM 'classpath:db/testdata.sql'").execute();
    }

    // Kör ett SQL-script som skapar databasens schema, en kopia av den produktionsdatabasen.
    private void initializeDatabaseSchema() throws SQLException {
        h2DbConn.prepareStatement("RUNSCRIPT FROM 'classpath:db/schema.sql'").execute();
    }


}