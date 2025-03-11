package service.cleaningorder;

import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class to encapsulate a request to update a cleaning order in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class UpdateCleaningOrderService extends BaseService<CleaningOrder> {

    private final CleaningOrder cleaningOrder;

    public UpdateCleaningOrderService(CleaningOrder cleaningOrder) {
        if (cleaningOrder == null) {
            throw new IllegalArgumentException("Cleaning order cannot be null");
        }
        this.cleaningOrder = cleaningOrder;
    }

    @Override
    protected CleaningOrder executeImplementation() throws SQLException {
        return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).update(cleaningOrder);
    }
}