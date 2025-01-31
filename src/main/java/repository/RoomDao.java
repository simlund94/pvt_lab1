package repository;

import db.DbConn;
import domain.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A DAO-class for retrieving and persisting room records in the database.
 */
public class RoomDao implements Dao<Room> {

    private PreparedStatement prst = null;

    /**
     * Saves a room record in the database corresponding to the passed Room object. The id field of the passed
     * object is ignored. Instead, if successful, this will return a Room object with a generated id from the
     * database.
     *
     * @param room The Room record to be saved in the database
     * @return A room object with a generated id.
     */
    @Override
    public Room save(Room room) {
        String query = "INSERT INTO lab_rooms(size_in_sqm, description) VALUES(?, ?)";
        Room roomSaved = null;
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setDouble(1, room.getSizeInSqm());
            prst.setString(2, room.getDescription());
            prst.executeUpdate();
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                roomSaved = new Room(newId, room.getSizeInSqm(), room.getDescription());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomSaved;
    }

    /**
     * Updates non-immutable fields of a room record from the passed Room object, matching the passed
     * Room object id.
     *
     * @param room The Room object containing new values to be updated in the database
     * @return true if successful, otherwise false
     */
    @Override
    public boolean update(Room room) {
        String query = "UPDATE lab_rooms SET description = ? WHERE id = ?";
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setString(1, room.getDescription());
            prst.setInt(2, room.getId());
            int affectedRows = prst.executeUpdate();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes a room record from the database corresponding to the id of the passed Room object.
     *
     * @param room The Room object to be deleted
     * @return true if successful, otherwise false
     */
    @Override
    public boolean delete(Room room) {
        String query = "DELETE FROM lab_rooms WHERE id = ?";
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setInt(1, room.getId());
            int affectedRows = prst.executeUpdate();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a Room object from the database corresponding to the passed id. Throws an exception if
     * no room record matches that id.
     *
     * @param id The id of the room in the database
     * @return The Room object matching the passed id
     * @throws NoSuchElementException if no matching id is found
     */
    @Override
    public Room get(int id) {
        String query = "SELECT id, size_in_sqm, description FROM lab_rooms WHERE id = ?";
        Room room = null;
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setInt(1, id);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                int fetchedId = rs.getInt("id");
                double sizeInSqm = rs.getDouble("size_in_sqm");
                String description = rs.getString("description");
                room = new Room(fetchedId, sizeInSqm, description);
            } else {
                String errorMessage = String.format("A room with ID: %d does not exist in the database!", id);
                throw new NoSuchElementException(errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    /**
     * Retrieves all room records from the database.
     *
     * @return A List of Room objects of every room record in the database.
     */
    @Override
    public List<Room> getAll() {
        String query = "SELECT id, size_in_sqm, description FROM lab_rooms";
        List<Room> rooms = new ArrayList<>();
        try {
            prst = DbConn.i().prepareStatement(query);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double sizeInSqm = rs.getDouble("size_in_sqm");
                String description = rs.getString("description");
                rooms.add(new Room(id, sizeInSqm, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
