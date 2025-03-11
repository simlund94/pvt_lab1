package service.room;

import domain.Room;

import repository.DaoFactory.*;
import repository.RoomDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to delete a room in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class DeleteRoomService extends BaseService<Boolean> {

    private final Room room;

    public DeleteRoomService(Room room, RoomDao roomDao) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        this.room = room;
    }

    @Override
    public Boolean executeImplementation() throws SQLException {
            return daoFactory.<RoomDao>get(DaoType.ROOM).delete(room);
    }
}
