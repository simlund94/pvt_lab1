package service.site;

import db.DbConn;
import domain.Site;
import repository.SiteDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class CheckIfSiteExistsService implements ServiceCommand<Boolean> {

    private final int id;

    private final SiteDao siteDao;

    public CheckIfSiteExistsService(int id, SiteDao siteDao) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or negative");
        }
        if (siteDao == null) {
            throw new IllegalArgumentException("SiteDao cannot be null");
        }
        this.id = id;
        this.siteDao = siteDao;
    }

    public CheckIfSiteExistsService(int id) {
        this(id, new SiteDao());
    }

    public CheckIfSiteExistsService(Site site) {
        this(site.getId());
    }

    @Override
    public Boolean execute() {
        try {
            DbConn.i().open();
            return siteDao.existsById(id);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("An error occurred while checking if the site exists in the DB");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
            }
        }
    }
}
