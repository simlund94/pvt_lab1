package service.room;

import domain.Room;
import repository.RoomDao;

public class SaveRoomService {

    private final Room room;

    public SaveRoomService(Room room) {
        this.room = room;
    }

    public Room execute() {
        return new RoomDao().save(this.room);
    }

}
