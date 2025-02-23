package service.employee;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import domain.Employee;
import repository.EmployeeDao;

/**
 * A command class that encapsulates a request to delete an employee from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class DeleteEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public DeleteEmployeeService(Employee employee, EmployeeDao employeeDao) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (employeeDao == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.employee = employee;
        this.employeeDao = employeeDao;
    }

    public DeleteEmployeeService(Employee employee) {
        this(employee, new EmployeeDao());
    }

    public boolean execute() {
        return employeeDao.delete(employee);
    }
}
