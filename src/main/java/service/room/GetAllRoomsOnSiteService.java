package service.room;

import domain.Room;
import domain.Site;
import repository.DaoFactory.*;
import repository.RoomDao;
import service.BaseService;

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
public class GetAllRoomsOnSiteService extends BaseService<List<Room>> {

    private final int siteId;

    public GetAllRoomsOnSiteService(int siteId) {
        if (siteId <= 0) {
            throw new IllegalArgumentException("Site id cannot be zero or negative");
        }
        this.siteId = siteId;
    }

    public GetAllRoomsOnSiteService(Site site) {
        this(site.getId());
    }

    @Override
    protected List<Room> executeImplementation() throws SQLException {
        return daoFactory.<RoomDao>get(DaoType.SITE).getAllRoomsOnSite(siteId);
    }
}
