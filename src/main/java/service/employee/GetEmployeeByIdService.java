package service.employee;

import com.google.protobuf.Service;
import db.DbConn;
import domain.Employee;
import repository.EmployeeDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request retrieve an employee by id from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetEmployeeByIdService implements ServiceCommand<Employee> {

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
        Employee employeeReturned;
        try {
            employeeReturned = employeeDao.get(id);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("An error occured.");
        } finally {
            DbConn.i().close();
        }
        return employeeReturned;
    }
}
