package service.room;

import domain.Room;
import repository.RoomDao;

import java.math.RoundingMode;

public class DeleteRoomService {

    private final Room room;

    private final RoomDao roomDao;

    public DeleteRoomService(Room room, RoomDao roomDao) {
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
