package spike;


import domain.Room;
import service.room.*;

import java.util.List;

/**
 * Demo program to demonstrate service layer programs that interact primarily with Room
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SpikeRoom {

    public static void main(String[] args) {
        // Get room
        Room room1 = new GetRoomByIdService(1).execute();
        System.out.println(room1);

        // Save room
        Room room2 = new Room(50.0, "A room", 1);
        room2 = new SaveRoomService(room2).execute();
        System.out.println(room2);
        System.out.println();

        // Update room
        System.out.println("--- Updating room ---");
        room2.setDescription("A fancy room!");
        room2 = new UpdateRoomService(room2).execute();
        System.out.println(room2);

        // Delete room
        boolean deleteStatus = new DeleteRoomService(room2).execute();
        System.out.printf("%s was deleted: %s\n", room2.getDescription(), deleteStatus);

        // Get all rooms
        System.out.println();
        List<Room> allRooms = new GetAllRoomsService().execute();
        System.out.println("--- All rooms currently in the database: ");
        allRooms.forEach(r -> System.out.println(r));
    }
}
