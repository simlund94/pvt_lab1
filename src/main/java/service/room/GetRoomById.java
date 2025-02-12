package service.room;

import domain.Room;
import repository.RoomDao;

public class GetRoomById {

    private int id;

    public GetRoomById(int id) {
        this.id = id;
    }

    public Room execute() {
        return new RoomDao().get(id);
    }
}
