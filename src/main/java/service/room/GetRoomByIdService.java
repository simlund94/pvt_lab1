package service.room;

import domain.Room;
import repository.RoomDao;

public class GetRoomByIdService {

    private int id;

    public GetRoomByIdService(int id) {
        this.id = id;
    }

    public Room execute() {
        return new RoomDao().get(id);
    }
}
