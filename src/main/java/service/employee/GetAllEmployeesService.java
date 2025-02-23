package service.employee;

import domain.Employee;
import repository.EmployeeDao;

import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all employees from the database.V
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetAllEmployeesService {

    private final EmployeeDao employeeDao;

    public GetAllEmployeesService(EmployeeDao employeeDao) {
        if (employeeDao == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.employeeDao = employeeDao;
    }

    public GetAllEmployeesService() {
        this(new EmployeeDao());
    }

    public List<Employee> execute() {
        return employeeDao.getAll();
    }
}
