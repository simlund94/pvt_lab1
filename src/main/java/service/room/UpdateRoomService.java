package service.room;

import db.DbConn;
import domain.Room;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;
import service.employee.UpdateEmployeeService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to update a room record in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class UpdateRoomService implements ServiceCommand<Room> {

    private final Room room;

    private final RoomDao roomDao;

    public UpdateRoomService(Room room, RoomDao roomDao) {
        if (room == null) {
            throw new IllegalArgumentException("The room cannot be null");
        }
        if (roomDao == null) {
            throw new IllegalArgumentException("The roomDAO cannot be null");
        }
        this.room = room;
        this.roomDao = roomDao;
    }

    public UpdateRoomService(Room room) {
        this(room, new RoomDao());
    }

    @Override
    public Room execute() {
        try {
            DbConn.i().open();
            return roomDao.update(room);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error updating room in the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred trying to close the database connection: " + e.getMessage());
            }
        }
    }
}
