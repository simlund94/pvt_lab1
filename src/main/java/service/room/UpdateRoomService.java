package service.room;

import domain.Room;
import repository.RoomDao;
import service.employee.UpdateEmployeeService;

public class UpdateRoomService {

    private final Room room;

    private final RoomDao roomDao;

    public UpdateRoomService(Room room, RoomDao roomDao) {
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
