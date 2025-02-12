package service.room;

import domain.Room;
import repository.RoomDao;

public class UpdateRoomService {

    private final Room room;

    public UpdateRoomService(Room room) {
        this.room = room;
    }

    public boolean execute() {
        return new RoomDao().update(room);
    }
}
