package service.employee;

import domain.Employee;
import repository.EmployeeDao;
import service.CleaningManagerServiceException;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to update an employee record in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class UpdateEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public UpdateEmployeeService(Employee employee, EmployeeDao employeeDao) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (employeeDao == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.employee = employee;
        this.employeeDao = employeeDao;
    }

    public UpdateEmployeeService(Employee employee) {
        this(employee, new EmployeeDao());
    }

    public boolean execute() {
        try {
            return employeeDao.update(employee);

        } catch (SQLException e) {
            throw new CleaningManagerServiceException(e.getMessage());
        }
    }
}

