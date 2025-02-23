package service.employee;

import domain.Employee;
import repository.EmployeeDao;

/**
 * A command class that encapsulates a request to save an employee to the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SaveEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public SaveEmployeeService(Employee employee, EmployeeDao employeeDao) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (employeeDao == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.employee = employee;
        this.employeeDao = employeeDao;
    }

    public SaveEmployeeService(Employee employee) {
        this(employee, new EmployeeDao());
    }

    public Employee execute() {
        return employeeDao.save(employee);
    }
}
