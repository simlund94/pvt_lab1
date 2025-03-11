package service.cleaningorder;

import domain.CleaningOrder;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import repository.EmployeeDao;
import repository.RoomDao;
import service.BaseService;

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
public class SaveCleaningOrderService extends BaseService<CleaningOrder> {

    private final CleaningOrder cleaningOrder;

    public SaveCleaningOrderService(CleaningOrder cleaningOrder) {
        if (cleaningOrder == null) {
            throw new IllegalArgumentException("Cleaning order cannot be null");
        }

        this.cleaningOrder = cleaningOrder;
    }

    @Override
    protected CleaningOrder executeImplementation() throws SQLException {
        validateReferences();
        return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).save(cleaningOrder);
    }

    private void validateReferences() throws SQLException {
        RoomDao roomDao = daoFactory.get(DaoType.ROOM);
        EmployeeDao employeeDao = daoFactory.get(DaoType.EMPLOYEE);
        if (!roomDao.existsById(cleaningOrder.getRoomId())) {
            String error = String.format("No room with id %d exists in the database", cleaningOrder.getRoomId());
            throw new IllegalArgumentException(error);
        }
        if (!employeeDao.existsById(cleaningOrder.getEmployeeId())) {
            String error = String.format("No employee with id %d exists in the database", cleaningOrder.getEmployeeId());
            throw new IllegalArgumentException(error);
        }
    }

}
