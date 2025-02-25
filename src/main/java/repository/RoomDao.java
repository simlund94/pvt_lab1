package repository;

import db.DbConn;
import domain.Room;
import domain.Site;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A DAO-class for retrieving and persisting room records in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class RoomDao implements Dao<Room> {

    private PreparedStatement prst = null;

    private DbConn dbConn;

    /**
     * Constructor with injectable database connection instance for mock testing.
     *
     * @param dbConn The database connection instance
     */
    public RoomDao(DbConn dbConn) {
        this.dbConn = dbConn;
    }

    /**
     * Constructor which retrieves an instance of the database connection.
     */
    public RoomDao() {
        this(DbConn.i());
    }

    /**
     * Saves a room record in the database corresponding to the passed Room object. The id field of the passed
     * object is ignored. Instead, if successful, this will return a Room object with a generated id from the
     * database.
     *
     * @param room The Room record to be saved in the database
     * @return A room object with a generated id.
     */
    @Override
    public Room save(Room room) throws SQLException {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (!new SiteDao().checkIfSiteExists(room.getSiteId())) {
            String errorMessage = String.format("No site with the id %d exists in the database", room.getSiteId());
            throw new IllegalArgumentException(errorMessage);
        }

        String query = "INSERT INTO lab_rooms(size_in_sqm, description, site_id) VALUES(?, ?, ?)";
        Room roomSaved = null;
        prst = DbConn.i().prepareStatement(query);
        prst.setDouble(1, room.getSizeInSqm());
        prst.setString(2, room.getDescription());
        prst.setInt(3, room.getSiteId());
        prst.executeUpdate();
        ResultSet rs = prst.getGeneratedKeys();
        if (rs.next()) {
            int newId = rs.getInt(1);
            roomSaved = new Room(newId, room.getSizeInSqm(), room.getDescription(), room.getSiteId());
        }
        return roomSaved;
    }

    /**
     * Updates non-immutable fields of a room record from the passed Room object, matching the passed
     * Room object id.
     *
     * @param room The Room object containing new values to be updated in the database
     * @return The updated Room from the database
     */
    @Override
    public Room update(Room room) throws SQLException {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }

        String query = "UPDATE lab_rooms SET description = ? WHERE id = ?";
        prst = DbConn.i().prepareStatement(query);
        prst.setString(1, room.getDescription());
        prst.setInt(2, room.getId());
        int changedRows = prst.executeUpdate();
        if (changedRows == 1) {
            return get(room.getId());
        } else {
            throw new NoSuchElementException("No Room in the database with that id");
        }
    }

    /**
     * Removes a room record from the database corresponding to the id of the passed Room object.
     *
     * @param room The Room object to be deleted
     * @return true if successful, otherwise false
     */
    @Override
    public boolean delete(Room room) throws SQLException {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }

        String query = "DELETE FROM lab_rooms WHERE id = ?";
        prst = DbConn.i().prepareStatement(query);
        prst.setInt(1, room.getId());
        int affectedRows = prst.executeUpdate();
        int expectedAffectedRows = 1;

        if (affectedRows == expectedAffectedRows) {
            return true;
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
    public Room get(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("The room id cannot be zero or negative");
        }

        String query = "SELECT id, size_in_sqm, description, site_id FROM lab_rooms WHERE id = ?";
        prst = DbConn.i().prepareStatement(query);
        prst.setInt(1, id);
        ResultSet rs = prst.executeQuery();
        if (rs.next()) {
            int fetchedId = rs.getInt("id");
            double sizeInSqm = rs.getDouble("size_in_sqm");
            String description = rs.getString("description");
            int siteId = rs.getInt("site_id");
            return new Room(fetchedId, sizeInSqm, description, siteId);
        } else {
            String errorMessage = String.format("A room with ID: %d does not exist in the database!", id);
            throw new NoSuchElementException(errorMessage);
        }
    }

    /**
     * Retrieves all room records from the database.
     *
     * @return A List of Room objects of every room record in the database.
     */
    @Override
    public List<Room> getAll() throws SQLException {
        String query = "SELECT id, size_in_sqm, description, site_id FROM lab_rooms";
        List<Room> rooms = new ArrayList<>();
        prst = DbConn.i().prepareStatement(query);
        ResultSet rs = prst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            double sizeInSqm = rs.getDouble("size_in_sqm");
            String description = rs.getString("description");
            int siteId = rs.getInt("site_id");
            rooms.add(new Room(id, sizeInSqm, description, siteId));
        }
        return rooms;
    }

    /**
     * Retrieves all rooms records that have the matching Site id foreign key in the database.
     *
     * @param siteId The site id
     * @return A List of Room objects associated with the site of the passed id.
     */
    public List<Room> getAllRoomsOnSite(int siteId) throws SQLException {
        if (siteId <= 0) {
            throw new IllegalArgumentException("SiteId cannot be zero or negative");
        }

        String query = "SELECT id, size_in_sqm, description FROM lab_rooms WHERE site_id = ?";
        List<Room> roomsOnSite = new ArrayList<>();
        prst = DbConn.i().prepareStatement(query);
        prst.setInt(1, siteId);
        prst.executeQuery();
        ResultSet rs = prst.getResultSet();
        while (rs.next()) {
            int roomId = rs.getInt("id");
            double sizeInSqm = rs.getDouble("size_in_sqm");
            String description = rs.getString("description");
            roomsOnSite.add(new Room(roomId, sizeInSqm, description, siteId));
        }
        return roomsOnSite;
    }

    /**
     * Retrieves all room records that belong to the passed site in the database.
     *
     * @param site The site of the rooms
     * @return A List of room objects associated with the site.
     */
    public List<Room> getAllRoomsOnSite(Site site) throws SQLException {
        if (site == null) {
            throw new IllegalArgumentException("Site cannot be null");
        }
        return getAllRoomsOnSite(site.getId());
    }
}
