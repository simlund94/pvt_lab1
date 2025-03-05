package spike;

import db.DbConn;
import domain.Employee;
import repository.DaoFactory;
import service.ServiceCommand;
import service.ServiceRunner;
import service.ServiceRunner2;
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
        ServiceRunner runner = new ServiceRunner();
        runner.setCommand(new GetEmployeeByIdService(1));
        Employee employee1 = (Employee) runner.execute();
        System.out.println(employee1);

        ServiceRunner2 runner2 = new ServiceRunner2();

        Employee hej = runner2.execute(new GetEmployeeByIdService(1));
        List<Employee> list = runner2.execute(new GetAllEmployeesService());

        System.out.println(hej);
        System.out.println(list);
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
        ServiceCommand<List<Employee>> service2 = new GetAllEmployeesService();
        service2.init(new DaoFactory(), DbConn.i());
        List<Employee> allEmployees = service2.execute();
        System.out.println("--- All Employees currently in the database:");
        allEmployees.forEach(e -> System.out.println(e)
        );
    }
}