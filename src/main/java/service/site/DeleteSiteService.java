package service.site;

import domain.Site;
import repository.DaoFactory.*;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to delete a site from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class DeleteSiteService extends BaseService<Boolean> {

    private final Site site;

    public DeleteSiteService(Site site) {
        if (site == null) {
            throw new IllegalArgumentException("Site cannot be null");
        }
        this.site = site;
    }

    @Override
    protected Boolean executeImplementation() throws SQLException {
        return daoFactory.<SiteDao>get(DaoType.SITE).delete(site);
    }
}
