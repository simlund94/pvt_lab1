package service.cleaningorder;

import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to retrieve a cleaning order from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class GetCleaningOrderByIdService extends BaseService<CleaningOrder> {

    private final int id;

    public GetCleaningOrderByIdService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or negative");
        }
        this.id = id;
    }

    @Override
    protected CleaningOrder executeImplementation() throws SQLException {
        return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).get(id);
    }
}
