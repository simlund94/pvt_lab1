package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class GetEmployeeByIdService {

    private final int id;

    private final EmployeeDao employeeDao;

    public GetEmployeeByIdService(int id, EmployeeDao employeeDao) {
        this.id = id;
        this.employeeDao = employeeDao;
    }

    public GetEmployeeByIdService(int id) {
        this(id, new EmployeeDao());
    }

    public Employee execute() {
        return employeeDao.get(id);
    }
}
