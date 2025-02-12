package service.room;

import domain.Room;
import repository.RoomDao;

public class DeleteRoomService {

    private final Room room;

    public DeleteRoomService(Room room) {
        this.room = room;
    }

    public boolean execute() {
       return new RoomDao().delete(this.room);
    }
}
