package spike;

import domain.Room;
import repository.Dao;
import repository.RoomDao;

import java.util.List;

public class SpikeRoom {

    public static void main(String[] args) {
        // Run simple room
        Room kitchen = new Room(15.5, "Kitchen");
        System.out.println(kitchen);
        System.out.println();

        Dao<Room> dao = new RoomDao();

        // Runs save()
        System.out.println("--- RUN SAVE ---");
        Room room1 = dao.save(new Room(25, "Grupprum"));
        System.out.println("Saved room: " + room1);
        System.out.println();

        // runs getAll()
        System.out.println("--- RUN GET ALL ---");
        List<Room> rooms = dao.getAll();
        for(Room room : rooms) {
            System.out.println(room);
        }
        System.out.println();

        // Runs update()
        System.out.println("--- RUN UPDATE ---");
        room1.setDescription("Kontorsrum");
        boolean status = dao.update(room1);
        System.out.println("Update successful: " + status);
        rooms = dao.getAll();
        for(Room room : rooms) {
            System.out.println(room);
        }
        System.out.println();

        // Runs get()
        System.out.println("--- RUN GET ---");
        Room room2 = dao.get(room1.getId());
        System.out.printf("%s retrieved!\n", room2);
        System.out.println();

        // Runs delete()
        System.out.println("--- RUN DELETE ---");
        boolean deleteStatus = dao.delete(room2);
        System.out.printf("%s deleted: %s\n", room2, deleteStatus);
        System.out.println();
        rooms = dao.getAll();
        for(Room room : rooms) {
            System.out.println(room);
        }
    }
}
