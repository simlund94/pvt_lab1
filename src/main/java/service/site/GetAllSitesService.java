package service.site;

import db.DbConn;
import domain.Site;
import repository.DaoFactory;
import repository.SiteDao;
import service.CleaningManagerServiceException;
import service.ServiceCommand;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-28
 */
public class GetAllSitesService implements ServiceCommand<List<Site>> {

    private final SiteDao siteDao;

    public GetAllSitesService(SiteDao siteDao) {
        this.siteDao = siteDao;
    }

    public GetAllSitesService() {
        this(new SiteDao());
    }

    @Override
    public List<Site> execute() {
        try {
            DbConn.i().open();
            return siteDao.getAll();
        } catch (SQLException e) {
            throw new CleaningManagerServiceException("An error occurred while retrieving all sites from the database");
        } finally {
            try {
                DbConn.i().close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing the database connection");
            }
        }
    }

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {

    }
}
