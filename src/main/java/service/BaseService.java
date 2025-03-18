package service;

import db.DbConn;
import repository.DaoFactory;

import java.sql.SQLException;

/**
 * A base class to centralise common fields and operations for the Service Command classes.
 * Child classes must implement the executeImplementation method which in turn is executed when
 * the base classes execute-method is called.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public abstract class BaseService<T> implements ServiceCommand<T> {

    protected DaoFactory daoFactory;

    @Override
    public void init(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void init() {
        init(new DaoFactory());
    }

    /*
     * Jag ville ha kod som kontrollerade att init hade körts korrekt en gång som jag inte behövde
     * upprepa i alla klasser, och detta var min lösning. Execute-metoden lever uppe i BaseService
     * och kan inte överskuggas, vilken i sin tur anropar executeImplementation som är protected och
     * som måste implementeras av alla barnklasser. När execute() anropas så kör den checken, och anropar sedan den specifika
     * metoden i barnklassen.
     *
     * Kanske är lite omständigt/onödigt iochmed att serviceCommand bör köras i runner, men nu får man
     * ett hjälpsamt meddelande om man inte gör det och glömmer init ...
     */
    @Override
    public final T execute() throws SQLException {
        checkResources();
        return executeImplementation();
    }

    /**
     * The implementation of the command operation, to be implemented by all inheriting  classes.
     * This method is in turn called in the body of the {@link #execute()} method with checks
     * that the proper resources have been initialized.
     *
     * @return The result of the operation
     * @throws SQLException if a database error occurs
     */
    protected abstract T executeImplementation() throws SQLException;

    private void checkResources() {
        if (daoFactory == null) {
            throw new IllegalStateException("DaoFactory was null. The init() method must be called correctly before calling execute()");
        }
    }
}