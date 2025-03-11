package service.room;

import domain.Room;
import repository.DaoFactory.*;
import repository.RoomDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to retrieve a room by id from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetRoomByIdService extends BaseService<Room> {

    private final int id;

    public GetRoomByIdService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The room id cannot be zero or less");
        }
        this.id = id;
    }

    @Override
    protected Room executeImplementation() throws SQLException {
            return daoFactory.<RoomDao>get(DaoType.ROOM).get(id);
    }
}
