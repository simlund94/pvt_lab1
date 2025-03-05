package service.employee;

import domain.Employee;
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

    public Employee execute() {
        try {
            dbConn.open();
            return daoFactory.getEmployeeDao().get(id);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error retrieving employee from the database.");
        } finally {
            try {
                dbConn.close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection: " + e.getMessage());
                System.err.println(e.getErrorCode());
            }
        }
    }

}
