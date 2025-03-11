package service.cleaningorder;

import domain.CleaningOrder;
import domain.Employee;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import service.BaseService;

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
public class GetCleaningOrdersByEmployeeService extends BaseService<List<CleaningOrder>> {

    private final int id;

    public GetCleaningOrdersByEmployeeService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or less");
        }
        this.id = id;
    }

    public GetCleaningOrdersByEmployeeService(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.id = employee.getId();
    }

    @Override
    protected List<CleaningOrder> executeImplementation() throws SQLException {
            return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).getAllByEmployee(id);
    }

}
