package service.employee;

import domain.Employee;
import repository.DaoFactory.*;
import repository.EmployeeDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request retrieve an employee by id from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetEmployeeByIdService extends BaseService<Employee> {

    private final int id;

    public GetEmployeeByIdService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        this.id = id;
    }

    @Override
    protected Employee executeImplementation() throws SQLException {
        return daoFactory.<EmployeeDao>get(DaoType.EMPLOYEE).get(id).orElseThrow();
    }
}
