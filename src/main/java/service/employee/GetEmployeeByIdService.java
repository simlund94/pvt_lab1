package service.employee;

import domain.Employee;
import repository.EmployeeDao;

public class GetEmployeeByIdService {

    private final int id;

    public GetEmployeeByIdService(int id) {
        this.id = id;
    }

    public Employee execute() {
        return new EmployeeDao().get(this.id);
    }
}
