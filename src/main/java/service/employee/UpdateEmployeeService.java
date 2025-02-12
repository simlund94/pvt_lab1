package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class UpdateEmployeeService {

    private final Employee employee;

    public UpdateEmployeeService(Employee employee) {
        this.employee = employee;
    }

    public boolean execute() {
        return new EmployeeDao().update(employee);
    }
}

