package service.site;

import domain.Site;
import repository.DaoFactory.*;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;

/**
 * A command class that encapsulates a request to check if a site exists in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class CheckIfSiteExistsService extends BaseService<Boolean> {

    private final int id;

    public CheckIfSiteExistsService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or negative");
        }
        this.id = id;
    }

    public CheckIfSiteExistsService(Site site) {
        this(site.getId());
    }

    @Override
    protected Boolean executeImplementation() throws SQLException {
        return daoFactory.<SiteDao>get(DaoType.SITE).existsById(id);
    }

}
