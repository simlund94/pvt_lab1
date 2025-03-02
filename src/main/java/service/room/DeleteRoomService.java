package service.room;

import db.DbConn;
import domain.Room;

import domain.Site;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to delete a room in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class DeleteRoomService implements ServiceCommand<Boolean> {

    private final Room room;

    private final RoomDao roomDao;

    public DeleteRoomService(Room room, RoomDao roomDao) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (roomDao == null) {
            throw new IllegalArgumentException("The RoomDAO cannot be null");
        }
        this.room = room;
        this.roomDao = roomDao;
    }

    public DeleteRoomService(Room room) {
        this(room, new RoomDao());
    }

    @Override
    public Boolean execute() {
        try {
            DbConn.i().open();
            return roomDao.delete(room);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error deleting room in the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection: " + e.getMessage());
            }
        }
    }
}
