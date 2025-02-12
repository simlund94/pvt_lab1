package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class DeleteEmployeeService {

    private final Employee employee;

    public DeleteEmployeeService(Employee employee) {
        this.employee = employee;
    }

    public boolean execute() {
        return new EmployeeDao().delete(employee);
    }
}
