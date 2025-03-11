package service.site;

import domain.Site;
import repository.DaoFactory.*;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to save a site to the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class SaveSiteService extends BaseService<Site> {

    private final Site site;

    public SaveSiteService(Site site) {
        if (site == null) {
            throw new IllegalArgumentException("Site cannot be null");
        }
        this.site = site;
    }

    @Override
    public Site executeImplementation() throws SQLException {
            return daoFactory.<SiteDao>get(DaoType.SITE).save(site);
    }
}
