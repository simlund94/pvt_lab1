package service.room;

import db.DbConn;
import domain.Room;
import domain.Site;
import repository.RoomDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsulates a request to retrieve all Rooms associated with a site from the
 * database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class GetAllRoomsOnSiteService implements ServiceCommand<List<Room>> {

    private final int siteId;

    private final RoomDao roomDao;

    public GetAllRoomsOnSiteService(int siteId, RoomDao roomDao) {
        if (siteId <= 0) {
            throw new IllegalArgumentException("Site id cannot be zero or negative");
        }
        if (roomDao == null) {
            throw new IllegalArgumentException("Room DAO cannot be null");
        }
        this.siteId = siteId;
        this.roomDao = roomDao;
    }

    public GetAllRoomsOnSiteService(int siteId) {
        this(siteId, new RoomDao());
    }

    public GetAllRoomsOnSiteService(Site site) {
        this(site.getId(), new RoomDao());
    }

    public GetAllRoomsOnSiteService(Site site, RoomDao roomDao) {
        this(site.getId(), roomDao);
    }

    @Override
    public List<Room> execute() {
        try {
            DbConn.i().open();
            return roomDao.getAllRoomsOnSite(siteId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while retrieving the rooms");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.err.println("An error occurred while closing the database connection");
            }
        }
    }
}
