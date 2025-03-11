package service.employee;

import domain.Employee;
import repository.DaoFactory.*;
import repository.EmployeeDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to update an employee record in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class UpdateEmployeeService extends BaseService<Employee> {

    private final Employee employee;

    public UpdateEmployeeService(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.employee = employee;
    }

    @Override
    protected Employee executeImplementation() throws SQLException {
        return daoFactory.<EmployeeDao>get(DaoType.EMPLOYEE).update(employee);
    }
}

