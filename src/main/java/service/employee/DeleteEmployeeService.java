package service.employee;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import db.DbConn;
import domain.Employee;
import repository.DaoFactory;
import repository.EmployeeDao;
import service.CleaningManagerServiceException;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to delete an employee from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class DeleteEmployeeService {

    private final Employee employee;

    private final DaoFactory daoFactory;

    public DeleteEmployeeService(Employee employee, DaoFactory daoFactory) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (daoFactory == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.employee = employee;
        this.daoFactory = daoFactory;
    }

    public DeleteEmployeeService(Employee employee) {
        this(employee, new DaoFactory());
    }

    public boolean execute() {
        try {
            DbConn.i().open();
            return daoFactory.getEmployeeDao().delete(employee);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error deleting employee from the database");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred trying to close the database connection: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        }
    }
}
