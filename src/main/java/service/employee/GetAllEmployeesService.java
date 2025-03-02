package service.employee;

import db.DbConn;
import domain.Employee;
import repository.EmployeeDao;
import service.CleaningManagerServiceException;

import java.sql.SQLException;
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
        try {
            DbConn.i().open();
            return employeeDao.getAll();
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error retrieving all employees from the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred trying to close the database connection: " + e.getMessage());
            }
        }
    }
}
