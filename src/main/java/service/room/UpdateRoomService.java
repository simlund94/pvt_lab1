package service.room;

import domain.Room;
import repository.RoomDao;
import service.employee.UpdateEmployeeService;

/**
 * A command class that encapsulates a request to update a room record in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class UpdateRoomService {

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

    public boolean execute() {
        return roomDao.update(room);
    }
}
