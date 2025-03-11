package service.site;

import domain.Site;
import repository.DaoFactory.*;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A service class that encapsulates a request to update a site in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class UpdateSiteService extends BaseService<Site> {

    private final Site site;

    public UpdateSiteService(Site site) {
        if (site == null) {
            throw new IllegalArgumentException("Site cannot be null");
        }
        this.site = site;
    }

    @Override
    protected Site executeImplementation() throws SQLException {
            return daoFactory.<SiteDao>get(DaoType.SITE).update(site);
    }
}