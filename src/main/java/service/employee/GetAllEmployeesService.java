package service.employee;

import domain.Employee;
import repository.DaoFactory.*;
import repository.EmployeeDao;
import service.BaseService;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all employees from the database.V
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetAllEmployeesService extends BaseService<List<Employee>> {

    @Override
    public List<Employee> executeImplementation() throws SQLException {
        return daoFactory.<EmployeeDao>get(FactoryType.EMPLOYEE).getAll();
    }

}
