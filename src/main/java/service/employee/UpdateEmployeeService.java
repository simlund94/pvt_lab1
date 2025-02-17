package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class UpdateEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public UpdateEmployeeService(Employee employee, EmployeeDao employeeDao) {
        this.employee = employee;
        this.employeeDao = employeeDao;
    }

    public UpdateEmployeeService(Employee employee) {
        this(employee, new EmployeeDao());
    }

    public boolean execute() {
        return employeeDao.update(employee);
    }
}

