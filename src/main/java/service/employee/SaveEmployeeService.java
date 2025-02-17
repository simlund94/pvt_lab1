package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class SaveEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public SaveEmployeeService(Employee employee, EmployeeDao employeeDao) {
        this.employee = employee;
        this.employeeDao = employeeDao;
    }

    public SaveEmployeeService(Employee employee) {
        this(employee, new EmployeeDao());
    }

    public Employee execute() {
        return employeeDao.save(employee);
    }
}
