package service.cleaningorder;

import db.DbConn;
import domain.CleaningOrder;
import domain.Employee;
import repository.CleaningOrderDao;
import repository.DaoFactory;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all the cleaning orders associated with a specific
 * employee.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class GetAllCleaningOrdersByEmployeeService implements ServiceCommand<List<CleaningOrder>> {

    private final Employee employee;

    private final CleaningOrderDao cleaningOrderDao;

    public GetAllCleaningOrdersByEmployeeService(Employee employee, CleaningOrderDao cleaningOrderDao) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (cleaningOrderDao == null) {
            throw new IllegalArgumentException("Dao cannot be null");
        }
        this.employee = employee;
        this.cleaningOrderDao = cleaningOrderDao;
    }

    public GetAllCleaningOrdersByEmployeeService(Employee employee) {
        this(employee, new CleaningOrderDao());
    }

    @Override
    public List<CleaningOrder> execute() {
        try {
            DbConn.i().open();
            return cleaningOrderDao.getAllByEmployee(employee);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while retrieving the cleaning orders associated with: " + employee.getName());
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection.");
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {

    }
}
