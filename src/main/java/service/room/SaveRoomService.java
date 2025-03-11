package service.room;

import domain.Room;
import repository.DaoFactory.*;
import repository.RoomDao;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to save a room record to the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SaveRoomService extends BaseService<Room> {

    private final Room room;

    public SaveRoomService(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("The room cannot be null");
        }
        this.room = room;
    }

    @Override
    protected Room executeImplementation() throws SQLException {
            validateReferences();
            return daoFactory.<RoomDao>get(DaoType.ROOM).save(room);
    }

    private void validateReferences() throws SQLException {
        SiteDao siteDao = daoFactory.get(DaoType.SITE);
        if (!siteDao.existsById(room.getSiteId())) {
            String error = String.format("No site with id %d exists in the database", room.getSiteId());
            throw new IllegalArgumentException(error);
        }
    }

}
