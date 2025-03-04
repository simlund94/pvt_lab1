package service.employee;

import db.DbConn;
import domain.Employee;
import repository.EmployeeDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to update an employee record in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class UpdateEmployeeService implements ServiceCommand<Employee> {

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

    @Override
    public Employee execute() {
        try {
            DbConn.i().open();
            return employeeDao.update(employee);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error updating employee in the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection: " + e.getMessage());
            }
        }
    }
}

