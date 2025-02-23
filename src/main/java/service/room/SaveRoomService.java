package service.room;

import domain.Room;
import repository.RoomDao;

/**
 * A command class that encapsulates a request to save a room record to the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SaveRoomService {

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

    public Room execute() {
        return roomDao.save(room);
    }

}
