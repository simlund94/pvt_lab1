package spike;


import domain.Room;
import service.room.*;

import java.util.List;

public class SpikeRoom {

    public static void main(String[] args) {
        // Get room
        Room room1 = new GetRoomByIdService(1).execute();
        System.out.println(room1);

        // Save room
        Room room2 = new Room(50.0, "A room");
        room2 = new SaveRoomService(room2).execute();
        System.out.println(room2);

        // Update room
        room2.setDescription("A fancy room!");
        boolean updateStatus = new UpdateRoomService(room2).execute();
        room2 = new GetRoomByIdService(room2.getId()).execute();
        System.out.println(room2);

        // Delete room
        boolean deleteStatus = new DeleteRoomService(room2).execute();
        System.out.printf("%s was deleted: %s\n", room2.getDescription(), deleteStatus);

        // Get all rooms
        System.out.println();
        List<Room> allRooms = new GetAllRoomsService().execute();
        System.out.println("--- All rooms currently in the database: ");
        allRooms.stream().forEach(r -> System.out.println(r));
    }
}
