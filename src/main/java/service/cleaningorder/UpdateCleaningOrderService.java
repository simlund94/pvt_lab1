package service.cleaningorder;

import db.DbConn;
import domain.CleaningOrder;
import repository.CleaningOrderDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class to encapsulate a request to update a cleaning order in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class UpdateCleaningOrderService implements ServiceCommand<CleaningOrder> {

    private final CleaningOrder cleaningOrder;

    private final CleaningOrderDao cleaningOrderDao;

    public UpdateCleaningOrderService(CleaningOrder cleaningOrder, CleaningOrderDao cleaningOrderDao) {
        if (cleaningOrder == null) {
            throw new IllegalArgumentException("Cleaning order cannot be null");
        }
        if (cleaningOrderDao == null) {
            throw new IllegalArgumentException("Dao cannot be null");
        }
        this.cleaningOrder = cleaningOrder;
        this.cleaningOrderDao = cleaningOrderDao;
    }

    public UpdateCleaningOrderService(CleaningOrder cleaningOrder) {
        this(cleaningOrder, new CleaningOrderDao());
    }

    @Override
    public CleaningOrder execute() {
        try {
            DbConn.i().open();
            return cleaningOrderDao.update(cleaningOrder);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while updating the cleaning order");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
                System.err.println(e.getMessage());
            }
        }
    }
}
