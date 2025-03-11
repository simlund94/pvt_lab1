package service.cleaningorder;

import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import service.BaseService;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all cleaning orders from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class GetAllCleaningOrdersService extends BaseService<List<CleaningOrder>> {

    @Override
    protected List<CleaningOrder> executeImplementation() throws SQLException {
        return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).getAll();
    }
}
