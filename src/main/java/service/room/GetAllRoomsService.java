package service.room;

import domain.Room;
import repository.RoomDao;

import java.util.List;

public class GetAllRoomsService {

    public List<Room> execute() {
        return new RoomDao().getAll();
    }
}
