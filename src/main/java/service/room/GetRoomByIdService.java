package service.room;

import domain.Room;
import repository.RoomDao;

public class GetRoomByIdService {

    private final int id;

    private final RoomDao roomDao;

    public GetRoomByIdService(int id, RoomDao roomDao) {
        this.id = id;
        this.roomDao = roomDao;
    }

    public GetRoomByIdService(int id) {
        this(id, new RoomDao());
    }

    public Room execute() {
        return new RoomDao().get(id);
    }
}
