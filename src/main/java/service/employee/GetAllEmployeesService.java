package service.employee;

import domain.Employee;
import repository.EmployeeDao;

import java.util.List;

public class GetAllEmployeesService {

    private final EmployeeDao employeeDao;

    public GetAllEmployeesService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public GetAllEmployeesService() {
        this(new EmployeeDao());
    }

    public List<Employee> execute() {
        return employeeDao.getAll();
    }
}
