package service.site;

import domain.Site;
import repository.DaoFactory.*;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;


/**
 * A command class that encapsualtes a request to retrieve a site from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class GetSiteByIdService extends BaseService<Site> {

    private final int id;

    public GetSiteByIdService(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id cannot be zero or negative");
        }
        this.id = id;
    }

    @Override
    protected Site executeImplementation() throws SQLException {
            return daoFactory.<SiteDao>get(DaoType.SITE).get(id);
    }
}
