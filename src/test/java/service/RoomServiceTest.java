package service;

import domain.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RoomDao;
import service.room.GetAllRoomsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    Room room;

    @BeforeEach
    void setUp() {
        room = new Room(1, 12.5, "Kök", 1);
    }

    @AfterEach
    void tearDown() {
        room = null;
    }

    @Test
    public void getAllRoomsService() {
        System.out.println("GetAll");
        RoomDao roomDaoMock = mock(RoomDao.class);

        when(roomDaoMock.getAll()).thenReturn(
                List.of(room));

        GetAllRoomsService instance = new GetAllRoomsService(roomDaoMock);
        List<Room> result = instance.execute();
        assertTrue(result instanceof List<Room>);
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Room);
        assertEquals(room, result.get(0));

    }

}
