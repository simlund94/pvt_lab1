package service.room;

import domain.Room;
import repository.RoomDao;

/**
 * A command class that encapsulates a request to retrieve a room by id from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class GetRoomByIdService {

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

    public Room execute() {
        return new RoomDao().get(id);
    }
}
