package service.cleaningorder;

import db.DbConn;
import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all cleaning orders from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class GetAllCleaningOrdersService implements ServiceCommand<List<CleaningOrder>> {

    private final CleaningOrderDao cleaningOrderDao;

    public GetAllCleaningOrdersService(CleaningOrderDao cleaningOrderDao) {
        if (cleaningOrderDao == null) {
            throw new IllegalArgumentException("Dao cannot be null");
        }
        this.cleaningOrderDao = cleaningOrderDao;
    }

    public GetAllCleaningOrdersService() {
        this(new CleaningOrderDao());
    }

    @Override
    public List<CleaningOrder> execute() {
        try {
            DbConn.i().open();
            return cleaningOrderDao.getAll();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while retrieving all cleaning orders");
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
