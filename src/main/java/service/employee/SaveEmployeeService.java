package service.employee;

import domain.Employee;
import repository.EmployeeDao;

/**
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-20
 */
public class SaveEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public SaveEmployeeService(Employee employee, EmployeeDao employeeDao) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
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
