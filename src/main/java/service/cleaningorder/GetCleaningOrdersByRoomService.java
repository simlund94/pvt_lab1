package service.cleaningorder;

import domain.CleaningOrder;
import domain.Room;
import repository.CleaningOrderDao;
import repository.DaoFactory.*;
import service.BaseService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-11
 */
public class GetCleaningOrdersByRoomService extends BaseService<List<CleaningOrder>> {

    private final int id;

    public GetCleaningOrdersByRoomService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or negative");
        }
        this.id = id;
    }

    public GetCleaningOrdersByRoomService(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        this.id = room.getId();
    }

    @Override
    protected List<CleaningOrder> executeImplementation() throws SQLException {
        return daoFactory.<CleaningOrderDao>get(DaoType.CLEANING_ORDER).getAllByRoom(id);
    }
}
