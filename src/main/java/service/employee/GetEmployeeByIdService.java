package service.employee;

import domain.Employee;
import repository.EmployeeDao;

/**
 * A command class that encapsulates a request retrieve an employee by id from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetEmployeeByIdService {

    private final int id;

    private final EmployeeDao employeeDao;

    public GetEmployeeByIdService(int id, EmployeeDao employeeDao) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (employeeDao == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.id = id;
        this.employeeDao = employeeDao;
    }

    public GetEmployeeByIdService(int id) {
        this(id, new EmployeeDao());
    }

    public Employee execute() {
        return employeeDao.get(id);
    }
}
