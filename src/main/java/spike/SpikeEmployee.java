package spike;

import domain.Employee;
import service.employee.*;

import java.util.List;

/**
 * Demo program to demonstrate the service layer classes that primarily interact with Employee
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SpikeEmployee {

    public static void main(String[] args) {
        // Get employee
        Employee employee1 = new GetEmployeeByIdService(1).execute();
        System.out.println(employee1);

        // Save employee
        Employee employee2 = new Employee("Kent Beck", 1961);
        employee2 = new SaveEmployeeService(employee2).execute();
        System.out.println(employee2);

        // Update employee
        employee2.setName("Kenny Bäck");
        Employee updatedEmployee = new UpdateEmployeeService(employee2).execute();
        System.out.printf("Employee with id %d updated: %s\n", employee2.getId(), updatedEmployee.getName());
        employee2 = new GetEmployeeByIdService(employee2.getId()).execute();
        System.out.println(employee2);

        // Delete emploee
        boolean deleteStatus = new DeleteEmployeeService(employee2).execute();
        System.out.printf("%s was deleted: %s\n", employee2.getName(), deleteStatus);

        // Get all employees
        System.out.println();
        List<Employee> allEmployees = new GetAllEmployeesService().execute();
        System.out.println("--- All Employees currently in the database:");
        allEmployees.stream().forEach(e -> System.out.println(e)
        );
    }
}