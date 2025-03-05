package service.employee;

import db.DbConn;
import domain.Employee;
import repository.DaoFactory;
import service.BaseService;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all employees from the database.V
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetAllEmployeesService extends BaseService<List<Employee>> {

    public GetAllEmployeesService(DaoFactory daoFactory) {
        if (daoFactory == null) {
            throw new IllegalArgumentException("EmployeeDAO cannot be null");
        }
        this.daoFactory = daoFactory;
    }

    public GetAllEmployeesService() {
        this(new DaoFactory());
    }

    public List<Employee> execute() throws SQLException {
        return daoFactory.getEmployeeDao().getAll();
    }

}
