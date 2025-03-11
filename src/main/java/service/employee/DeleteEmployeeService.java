package service.employee;

import domain.Employee;
import repository.DaoFactory.*;
import repository.EmployeeDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to delete an employee from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class DeleteEmployeeService extends BaseService<Boolean> {

    private final Employee employee;

    public DeleteEmployeeService(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.employee = employee;
    }

    @Override
    protected Boolean executeImplementation() throws SQLException {
        return daoFactory.<EmployeeDao>get(DaoType.EMPLOYEE).delete(employee);
    }
}
