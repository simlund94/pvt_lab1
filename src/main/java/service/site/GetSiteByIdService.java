package service.site;

import db.DbConn;
import domain.Site;
import repository.SiteDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * A command class that encapsualtes a request to retrieve a site from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class GetSiteByIdService implements ServiceCommand<Site> {

    private final int id;

    private final SiteDao siteDao;

    public GetSiteByIdService(int id, SiteDao siteDao) {
        if (id <= 0) {
            throw new IllegalArgumentException("id cannot be zero or negative");
        }
        if (siteDao == null) {
            throw new IllegalArgumentException("SiteDao cannot be null");
        }
        this.id = id;
        this.siteDao = siteDao;
    }

    public GetSiteByIdService(int id) {
        this(id, new SiteDao());
    }

    @Override
    public Site execute() {
        try {
            DbConn.i().open();
            return siteDao.get(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new CleaningManagerServiceException("An error occurred while retrieving the site");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
                System.err.println(e.getMessage());
            }
        }
    }
}
