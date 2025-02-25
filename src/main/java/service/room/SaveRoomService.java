package service.room;

import db.DbConn;
import domain.Room;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to save a room record to the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SaveRoomService implements ServiceCommand<Room> {

    private final Room room;

    private final RoomDao roomDao;

    public SaveRoomService(Room room, RoomDao roomDao) {
        if (room == null) {
            throw new IllegalArgumentException("The room cannot be null");
        }
        if (roomDao == null) {
            throw new IllegalArgumentException("The roomDAO cannot be null");
        }
        this.room = room;
        this.roomDao = roomDao;
    }

    public SaveRoomService(Room room) {
        this(room, new RoomDao());
    }

    @Override
    public Room execute() {
        try {
            DbConn.i().open();
            Room roomSaved = roomDao.save(room);
            DbConn.i().close();
            return roomSaved;
        } catch (SQLException e) {
            throw new CleaningManagerServiceException(e.getMessage());
        }
    }

}
