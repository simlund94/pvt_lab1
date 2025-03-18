package repository;

/**
 * A factory that returns Dao instances by passing the appropriate Enum value to the get-method.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-03-04
 */
public class DaoFactory {

    public <T extends Dao<?>> T get(DaoType factoryType) {
        return (T) factoryType.createDao();
    }

    public enum DaoType {
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