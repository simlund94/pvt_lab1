package service.cleaningorder;

import db.DbConn;
import domain.CleaningOrder;
import repository.CleaningOrderDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to retrieve a cleaning order from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class GetCleaningOrderByIdService implements ServiceCommand<CleaningOrder> {

    private final int id;

    private final CleaningOrderDao cleaningOrderDao;

    public GetCleaningOrderByIdService(int id, CleaningOrderDao cleaningOrderDao) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or negative");
        }
        if (cleaningOrderDao == null) {
            throw new IllegalArgumentException("Dao cannot be null");
        }
        this.id = id;
        this.cleaningOrderDao = cleaningOrderDao;
    }

    public GetCleaningOrderByIdService(int id) {
        this(id, new CleaningOrderDao());
    }

    @Override
    public CleaningOrder execute() {
        try {
            DbConn.i().open();
            return cleaningOrderDao.get(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while retrieving the Cleaning order");
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
