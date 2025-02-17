package service.room;

import domain.Room;
import repository.RoomDao;

import java.util.List;

public class GetAllRoomsService {

    private final RoomDao roomDao;

    public GetAllRoomsService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public GetAllRoomsService() {
        this(new RoomDao());
    }

    public List<Room> execute() {
        return roomDao.getAll();
    }
}
