package spike;

import domain.Employee;
import repository.EmployeeDao;

import java.util.List;

/**
 * Demo program to demonstrate the Employee and EmployeeDao classes.
 */
public class SpikeEmployee {

    public static void main(String[] args) {
        // Run simple employee methods
        Employee simon = new Employee("Simon Lundgren", 1994);
        System.out.println(simon);
        System.out.println();

        EmployeeDao dao = new EmployeeDao();

        // Runs getAll()
        System.out.println("--- RUN GET ALL ---");
        List<Employee> employees = dao.getAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        System.out.println();

        // Runs save()
        System.out.println("--- RUN SAVE ---");
        Employee employee1 = dao.save(new Employee("Gurra Kaka", 1850));
        System.out.println("Person saved: "+ employee1);
        employees = dao.getAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        System.out.println();

        // Runs update()
        System.out.println("--- RUN UPDATE ---");
        Employee employee2 = new Employee(8, "Karrotter Morötter", 1900);
        System.out.printf("%s updated: %s", employee2.getName(), dao.update(employee2));
        employees = dao.getAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        System.out.println();

        // runs get()
        System.out.println("--- RUN GET ---");
        Employee employee3 = dao.get(employee1.getId());
        System.out.printf("%s retrieved!\n", employee3);
        System.out.println();

        // runs delete()
        System.out.println("--- RUN DELETE ---");
        System.out.format("%s deleted: %s\n", employee1, dao.delete(employee1));
        employees = dao.getAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}