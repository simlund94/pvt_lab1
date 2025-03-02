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
public class UpdateSiteService implements ServiceCommand<Site> {

    private final Site site;

    private final SiteDao siteDao;

    public UpdateSiteService(Site site, SiteDao siteDao) {
        if (site == null) {
            throw new IllegalArgumentException("Site cannot be null");
        }
        if (siteDao == null) {
            throw new IllegalArgumentException("SiteDao cannot be null");
        }
        this.site = site;
        this.siteDao = siteDao;
    }

    public UpdateSiteService(Site site) {
        this(site, new SiteDao());
    }

    @Override
    public Site execute() {
        try {
            DbConn.i().open();
            return siteDao.update(site);
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("An error occurred while updating the site in the database");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection.");
            }
        }
    }
}
