package service.employee;

import db.DbConn;
import domain.Employee;
import repository.DaoFactory;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to save an employee to the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SaveEmployeeService implements ServiceCommand<Employee> {

    private final Employee employee;

    private final DaoFactory daoFactory;

    public SaveEmployeeService(Employee employee, DaoFactory daoFactory) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (daoFactory == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.employee = employee;
        this.daoFactory = daoFactory;
    }

    public SaveEmployeeService(Employee employee) {
        this(employee, new DaoFactory());
    }

    public Employee execute() {
        try {
            DbConn.i().open();
            return daoFactory.getEmployeeDao().save(employee);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error saving employee to the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection: " + e.getMessage());
            }
        }
    }

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {

    }
}
