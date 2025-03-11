package service.site;

import domain.Site;
import repository.DaoFactory.*;
import repository.SiteDao;
import service.BaseService;

import java.sql.SQLException;
import java.util.List;

/**
 * A command class that encapsualtes a request to retrieve all sites from the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class GetAllSitesService extends BaseService<List<Site>> {

    @Override
    public List<Site> executeImplementation() throws SQLException {
            return daoFactory.<SiteDao>get(DaoType.SITE).getAll();
    }
}
