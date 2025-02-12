package spike;

import domain.Employee;
import domain.Room;
import repository.EmployeeDao;
import service.EmployeeService;
import service.room.GetRoomById;

import java.util.List;

/**
 * Demo program to demonstrate the Employee and EmployeeDao classes.
 */
public class SpikeEmployee {

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService(new EmployeeDao());

    }
}