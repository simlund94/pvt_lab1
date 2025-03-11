package service.room;

import domain.Room;
import repository.DaoFactory.*;
import repository.RoomDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to update a room record in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class UpdateRoomService extends BaseService<Room> {

    private final Room room;

    public UpdateRoomService(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("The room cannot be null");
        }
        this.room = room;
    }

    @Override
    protected Room executeImplementation() throws SQLException {
            return daoFactory.<RoomDao>get(DaoType.ROOM).update(room);
    }
}
