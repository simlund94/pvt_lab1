package service.employee;

import domain.Employee;
import repository.EmployeeDao;

import java.util.List;

public class GetAllEmployeesService {

    public List<Employee> execute() {
        return new EmployeeDao().getAll();
    }
}
