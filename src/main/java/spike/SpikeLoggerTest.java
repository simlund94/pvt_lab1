package spike;

import domain.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceRunner;
import service.room.GetAllRoomsService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-18
 */
public class SpikeLoggerTest {

    private static final Logger logger = LogManager.getLogger("cleaningManager");

    public static void main(String[] args) throws SQLException {
        ServiceRunner runner = new ServiceRunner();
        List<Room> rooms = runner.execute(new GetAllRoomsService());
    }

}
