package service.employee;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import domain.Employee;
import repository.EmployeeDao;

public class DeleteEmployeeService {

    private final Employee employee;

    private final EmployeeDao employeeDao;

    public DeleteEmployeeService(Employee employee, EmployeeDao employeeDao) {
        this.employee = employee;
        this.employeeDao = employeeDao;
    }

    public DeleteEmployeeService(Employee employee) {
        this(employee, new EmployeeDao());
    }

    public boolean execute() {
        return employeeDao.delete(employee);
    }
}
