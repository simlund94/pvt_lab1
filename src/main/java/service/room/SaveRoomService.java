package service.room;

import db.DbConn;
import domain.Room;
import repository.DaoFactory;
import repository.RoomDao;
import repository.SiteDao;
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
            validateForeignKeys();
            return roomDao.save(room);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error saving room in the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred trying to close the database connection: " + e.getMessage());
            }
        }
    }

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {

    }

    private void validateForeignKeys() throws SQLException {
        if (!new SiteDao().existsById(room.getSiteId())) {
            String error = String.format("No site with id %d exists in the database", room.getSiteId());
            throw new IllegalArgumentException(error);
        }
    }

}
