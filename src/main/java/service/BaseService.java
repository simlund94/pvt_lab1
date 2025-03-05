package service;

import db.DbConn;
import repository.DaoFactory;

import java.sql.SQLException;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public abstract class BaseService<T> implements ServiceCommand<T> {

    protected DbConn dbConn;
    protected DaoFactory daoFactory;

    @Override
    public void init(DaoFactory daoFactory, DbConn dbConn) {
        this.dbConn = dbConn;
        this.daoFactory = daoFactory;
    }

    @Override
    public abstract T execute() throws SQLException;

}
