package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class SaveEmployeeService {

    private final Employee employee;

    public SaveEmployeeService(Employee employee) {
        this.employee = employee;
    }

    public Employee execute() {
        return new EmployeeDao().save(this.employee);
    }
}
