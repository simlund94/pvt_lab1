package service.room;

import db.DbConn;
import domain.Room;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all rooms in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetAllRoomsService implements ServiceCommand<List<Room>> {

    private final RoomDao roomDao;

    public GetAllRoomsService(RoomDao roomDao) {
        if (roomDao == null) {
            throw new IllegalArgumentException("The RoomDAO cannot be null");
        }
        this.roomDao = roomDao;
    }

    public GetAllRoomsService() {
        this(new RoomDao());
    }

    @Override
    public List<Room> execute() {
        try {
            DbConn.i().open();
            List<Room> rooms = roomDao.getAll();
            DbConn.i().close();
            return rooms;
        } catch (SQLException e) {
            throw new CleaningManagerServiceException(e.getMessage());
        }
    }
}
