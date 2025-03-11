package service.room;

import domain.Room;
import repository.DaoFactory.*;
import repository.RoomDao;
import service.BaseService;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all rooms in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetAllRoomsService extends BaseService<List<Room>> {

    @Override
    protected List<Room> executeImplementation() throws SQLException {
            return daoFactory.<RoomDao>get(DaoType.ROOM).getAll();
    }
}
