package service.room;

import db.DbConn;
import domain.Room;
import repository.DaoFactory;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to retrieve a room by id from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetRoomByIdService implements ServiceCommand<Room> {

    private final int id;

    private final RoomDao roomDao;

    public GetRoomByIdService(int id, RoomDao roomDao) {
        if (id <= 0) {
            throw new IllegalArgumentException("The room id cannot be zero or less");
        }
        if (roomDao == null) {
            throw new IllegalArgumentException("The roomDAO cannot be null");
        }
        this.id = id;
        this.roomDao = roomDao;
    }

    public GetRoomByIdService(int id) {
        this(id, new RoomDao());
    }

    @Override
    public Room execute() {
        try {
            DbConn.i().open();
            return roomDao.get(id);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error retrieving room from the database.");
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
}
