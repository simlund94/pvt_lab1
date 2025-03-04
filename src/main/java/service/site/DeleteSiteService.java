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
public class DeleteSiteService implements ServiceCommand<Boolean> {

    private final SiteDao siteDao;

    private final Site site;

    public DeleteSiteService(Site site, SiteDao siteDao) {
        if (site == null) {
            throw new IllegalArgumentException("Site cannot be null");
        }
        if (siteDao == null) {
            throw new IllegalArgumentException("SiteDao cannot be null");
        }
        this.siteDao = siteDao;
        this.site = site;
    }

    public DeleteSiteService(Site site) {
        this(site, new SiteDao());
    }

    @Override
    public Boolean execute() {
        try {
            DbConn.i().open();
            return siteDao.delete(site);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("An error occurred while deleting the site");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
            }
        }
    }
}
