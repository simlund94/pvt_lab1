package service.room;

import db.DbConn;
import domain.Room;
import repository.DaoFactory;
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
            return roomDao.getAll();
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("Error retrieving all rooms from the database.");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection: " + e.getMessage());
            }
        }
    }

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {

    }
}
