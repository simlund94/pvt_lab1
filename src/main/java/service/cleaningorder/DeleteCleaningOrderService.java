package service.cleaningorder;

import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class to encapsulate a request to delete a cleaning order from the database.

 * @author Simon Lundgren
 * @version 1.1
 * Created on: 2025-03-02
 */
public class DeleteCleaningOrderService extends BaseService<Boolean> {

    private final CleaningOrder cleaningOrder;

    public DeleteCleaningOrderService(CleaningOrder cleaningOrder) {
        if (cleaningOrder == null) {
            throw new IllegalArgumentException("Cleaning Order cannot be null");
        }
        this.cleaningOrder = cleaningOrder;
    }

    @Override
    protected Boolean executeImplementation() throws SQLException {
            return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).delete(cleaningOrder);
    }
}
