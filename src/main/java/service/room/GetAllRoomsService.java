package service.room;

import domain.Room;
import repository.RoomDao;

import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all rooms in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetAllRoomsService {

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

    public List<Room> execute() {
        return roomDao.getAll();
    }
}
