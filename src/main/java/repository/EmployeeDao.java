package repository;

import db.DbConn;
import domain.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EmployeeDao implements Dao<Employee> {

    private PreparedStatement prst = null;

    /**
     * Retrieves an employee record from the database table that matches the passed id.
     * Throws an exception if no employee with that id is present in the database.
     *
     * @param employeeId the id of the employee in the database
     * @return The Employee matching the passed id
     * @throws NoSuchElementException if no matching id is found.
     */
    @Override
    public Employee get(int employeeId) {
        String query = "SELECT id, name, birth_year FROM lab_employees WHERE id = ?";
        Employee employee = null;
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setInt(1, employeeId);
            prst.executeQuery();
            ResultSet rs = prst.getResultSet();
            if (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("birth_year");
                String name = rs.getString("name");
                employee = new Employee(id, name, age);
            } else {
                String errorMessage = String.format("An employee with ID: %d does not exist in the database!", employeeId);
                throw new NoSuchElementException(errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * Creates a record of a temporary employee object without id in the database, and if successful
     * will return a copy of the saved employee with the generated id.
     *
     * @param employee The employee record to save in the database. The id field will be ignored.
     * @return The saved employee record with a generated id.
     */
    @Override
    public Employee save(Employee employee) {
        String query = "INSERT INTO lab_employees(name, birth_year) VALUES(?, ?)";
        Employee employeeSaved = null;
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setString(1, employee.getName());
            prst.setInt(2, employee.getBirthYear());
            prst.executeUpdate();
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                employeeSaved = new Employee(id, employee.getName(), employee.getBirthYear());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeSaved;
    }

    /**
     * Updates non-final fields of an employee in the database which matches the id of the passed employee object.
     *
     * @param employee An employee object containing new values to overwrite an existing one in the database
     * @return true if successful, otherwise false
     */
    @Override
    public boolean update(Employee employee) {
        String query = "UPDATE lab_employees SET name = ? WHERE id = ?";
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setString(1, employee.getName());
            prst.setInt(2, employee.getId());
            int affectedRows = prst.executeUpdate();
            int expectedAffectedRows = 1;

            // Om databasen returnerar 1 påverkad rad så lyckades uppdateringen.
            // Om databasen returnerar 0 rader så misslyckades det.
            // Om databasen returnerar något annat så har något gått åt skogen, då id ska identifiera
            // en unik tupel i tabellen.
            if (affectedRows == expectedAffectedRows) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an employee record from the database which matches the id of the passed employee object.
     *
     * @param employee An employee object matching the id of the record to be deleted
     * @return true if successful, otherwise false
     */
    @Override
    public boolean delete(Employee employee) {
        String query = "DELETE FROM lab_employees WHERE id = ?";
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setInt(1, employee.getId());
            int affectedRows = prst.executeUpdate();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all employee records from the database.
     *
     * @return A List of Employee objects containing all employees in the database
     */
    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<Employee>();
        try {
            prst = DbConn.i().prepareStatement(
                    "SELECT id, name, birth_year FROM lab_employees"
            );
            prst.executeQuery();
            ResultSet rs = prst.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                int birthYear = rs.getInt("birth_year");
                String name = rs.getString("name");
                Employee employee = new Employee(id, name, birthYear);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

}
