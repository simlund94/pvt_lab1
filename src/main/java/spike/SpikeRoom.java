package spike;

import domain.Room;
import repository.Dao;
import repository.RoomDao;

import java.util.List;

public class SpikeRoom {

    public static void main(String[] args) {
        Dao<Room> dao = new RoomDao();

        // Runs save()
        Room room1 = dao.save(new Room(25, "Grupprum"));
        System.out.println("Saved room: " + room1);
        System.out.println();

        // runs getAll()
        List<Room> rooms = dao.getAll();
        for(Room room : rooms) {
            System.out.println(room);
        }
        System.out.println();

        // Runs update()
        room1.setDescription("Kontorsrum");
        boolean status = dao.update(room1);
        System.out.println("Update successful: " + status);
        rooms = dao.getAll();
        for(Room room : rooms) {
            System.out.println(room);
        }
        System.out.println();

        // Runs get()
        Room room2 = dao.get(room1.getId());
        System.out.printf("%s retrieved!\n", room2);
        System.out.println();

        // Runs delete()
        boolean deleteStatus = dao.delete(room2);
        System.out.printf("%s deleted: %s\n", room2, deleteStatus);
        rooms = dao.getAll();
        for(Room room : rooms) {
            System.out.println(room);
        }
    }
}
