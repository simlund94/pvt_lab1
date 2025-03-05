package repository;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public class DaoFactory {

    public EmployeeDao getEmployeeDao() {
        return new EmployeeDao();
    }

    public RoomDao getRoomDao() {
        return new RoomDao();
    }

    public SiteDao getSiteDao() {
        return new SiteDao();
    }

    public CleaningOrderDao getCleaningOrderDao() {
        return new CleaningOrderDao();
    }

    public <T extends Dao<?>> T get(FactoryType factoryType) {
        return (T) factoryType.createDao();
    }

    public enum FactoryType {
        EMPLOYEE {
            @Override
            public EmployeeDao createDao() {
                 return new EmployeeDao();
            }
        },
        ROOM {
            @Override
            public RoomDao createDao() {
                return new RoomDao();
            }
        },
        SITE {
            @Override
            public SiteDao createDao() {
                return new SiteDao();
            }
        },
        CLEANING_ORDER {
            @Override
            public CleaningOrderDao createDao() {
                return new CleaningOrderDao();
            }
        };

        public abstract Dao createDao();
    }
}