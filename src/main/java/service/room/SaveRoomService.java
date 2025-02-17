package service.room;

import domain.Room;
import repository.RoomDao;

public class SaveRoomService {

    private final Room room;

    private final RoomDao roomDao;

    public SaveRoomService(Room room, RoomDao roomDao) {
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
