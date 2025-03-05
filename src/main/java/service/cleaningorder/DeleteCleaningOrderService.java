package service.cleaningorder;

import db.DbConn;
import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class DeleteCleaningOrderService implements ServiceCommand<Boolean> {

    private final CleaningOrder cleaningOrder;

    private final CleaningOrderDao cleaningOrderDao;

    public DeleteCleaningOrderService(CleaningOrder cleaningOrder, CleaningOrderDao cleaningOrderDao) {
        if (cleaningOrder == null) {
            throw new IllegalArgumentException("Cleaning Order cannot be null");
        }
        if (cleaningOrderDao == null) {
            throw new IllegalArgumentException("Dao cannot be null");
        }
        this.cleaningOrder = cleaningOrder;
        this.cleaningOrderDao = cleaningOrderDao;
    }

    public DeleteCleaningOrderService(CleaningOrder cleaningOrder) {
        this(cleaningOrder, new CleaningOrderDao());
    }

    @Override
    public Boolean execute() {
        try {
            DbConn.i().open();
            return cleaningOrderDao.delete(cleaningOrder);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while deleting the cleaning order");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {

    }
}
