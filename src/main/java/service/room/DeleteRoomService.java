package service.room;

import domain.Room;

import repository.RoomDao;

/**
 * A command class that encapsulates a request to delete a room in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class DeleteRoomService {

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

    public boolean execute() {
       return roomDao.delete(room);
    }
}
