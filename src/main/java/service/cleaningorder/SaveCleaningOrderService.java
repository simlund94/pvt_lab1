package service.cleaningorder;

import db.DbConn;
import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.EmployeeDao;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class to encapsulate a request to save a cleaning order to the database.
 * Contains logic to check that the room and employee given in the cleaning order exists
 * in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-02
 */
public class SaveCleaningOrderService implements ServiceCommand<CleaningOrder> {

    private final CleaningOrder cleaningOrder;

    private final CleaningOrderDao cleaningOrderDao;

    public SaveCleaningOrderService(CleaningOrder cleaningOrder, CleaningOrderDao cleaningOrderDao) {
        if (cleaningOrder == null) {
            throw new IllegalArgumentException("Cleaning order cannot be null");
        }
        if (cleaningOrderDao == null) {
            throw new IllegalArgumentException("Dao cannot be null");
        }

        this.cleaningOrder = cleaningOrder;
        this.cleaningOrderDao = cleaningOrderDao;
    }

    public SaveCleaningOrderService(CleaningOrder cleaningOrder) {
        this(cleaningOrder, new CleaningOrderDao());
    }

    @Override
    public CleaningOrder execute() {
        try {
            DbConn.i().open();
            validateForeignKeys();
            return cleaningOrderDao.save(cleaningOrder);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while saving the cleaning order to the database");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
                System.err.println(e.getMessage());
            }
        }
    }

    private void validateForeignKeys() throws SQLException {
        if (!new RoomDao().existsById(cleaningOrder.getRoomId())) {
            String error = String.format("No room with id %d exists in the database", cleaningOrder.getRoomId());
            throw new IllegalArgumentException(error);
        }
        if (!new EmployeeDao().existsById(cleaningOrder.getEmployeeId())) {
            String error = String.format("No employee with id %d exists in the database", cleaningOrder.getEmployeeId());
            throw new IllegalArgumentException(error);
        }
    }
}
