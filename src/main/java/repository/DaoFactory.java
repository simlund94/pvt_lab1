package repository;

import db.DatabaseConnector;
import db.DbConn;

/**
 * A factory that returns Dao instances by passing the appropriate Enum value to the get-method.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public class DaoFactory {

    private DatabaseConnector dbConn;

    public DaoFactory(DatabaseConnector dbConn) {
        this.dbConn = dbConn;
    }

    public DaoFactory() {
        this(DbConn.i());
    }

    public <T extends Dao<?>> T get(DaoType factoryType) {
        return (T) factoryType.createDao(dbConn);
    }

    public enum DaoType {
        EMPLOYEE {
            @Override
            public EmployeeDao createDao(DatabaseConnector dbConn) {
                 return new EmployeeDao(dbConn);
            }
        },
        ROOM {
            @Override
            public RoomDao createDao(DatabaseConnector dbConn) {
                return new RoomDao(dbConn);
            }
        },
        SITE {
            @Override
            public SiteDao createDao(DatabaseConnector dbConn) {
                return new SiteDao(dbConn);
            }
        },
        CLEANING_ORDER {
            @Override
            public CleaningOrderDao createDao(DatabaseConnector dbConn) {
                return new CleaningOrderDao(dbConn);
            }
        };

        public abstract Dao createDao(DatabaseConnector dbConn);
    }
}