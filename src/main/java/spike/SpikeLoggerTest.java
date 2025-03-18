package spike;

import service.ServiceRunner;
import service.employee.GetEmployeeByIdService;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-18
 */
public class SpikeLoggerTest {

    public static void main(String[] args) {
        ServiceRunner runner = new ServiceRunner();
        runner.execute(new GetEmployeeByIdService(12));
    }
}
