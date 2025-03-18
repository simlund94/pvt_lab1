package service.employee;

import domain.Employee;
import repository.DaoFactory.*;
import repository.EmployeeDao;
import service.BaseService;
import service.CleaningManagerServiceException;

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
        String errorMessage = String.format("An employee with ID: %d does not exist in the database!", id);
        return daoFactory.<EmployeeDao>get(DaoType.EMPLOYEE).get(id)
                .orElseThrow(() -> new CleaningManagerServiceException(errorMessage));
    }
}
