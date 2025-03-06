package spike;

import db.DbConn;
import domain.Employee;
import repository.DaoFactory;
import service.ServiceCommand;
import service.ServiceRunner;
import service.employee.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Demo program to demonstrate the service layer classes that primarily interact with Employee
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SpikeEmployee {

    public static void main(String[] args) throws SQLException {
        // Get employee
        ServiceRunner runner = new ServiceRunner();
        Employee employee1 = runner.execute(new GetEmployeeByIdService(1));
        System.out.println(employee1);

        // Save employee
        Employee employee2 = new Employee("Kent Beck", 1961);
        employee2 = runner.execute(new SaveEmployeeService(employee2));
        System.out.println(employee2);

        // Update employee
        employee2.setName("Kenny Bäck");
        Employee updatedEmployee = runner.execute(new UpdateEmployeeService(employee2));
        System.out.printf("Employee with id %d updated: %s\n", employee2.getId(), updatedEmployee.getName());
        employee2 = runner.execute(new GetEmployeeByIdService(employee2.getId()));
        System.out.println(employee2);

        // Delete emploee
        boolean deleteStatus = runner.execute(new DeleteEmployeeService(employee2));
        System.out.printf("%s was deleted: %s\n", employee2.getName(), deleteStatus);

        // Get all employees
        System.out.println();
        List<Employee> allEmployees = runner.execute(new GetAllEmployeesService());
        System.out.println("--- All Employees currently in the database:");
        allEmployees.forEach(e -> System.out.println(e)
        );
    }
}