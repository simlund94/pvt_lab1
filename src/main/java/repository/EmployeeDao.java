package repository;

import db.DatabaseConnector;
import db.DbConn;
import domain.Employee;
import service.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * A DAO class for retrieving and persisting Employee records in the database.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class EmployeeDao implements Dao<Employee> {

    private PreparedStatement prst = null;

    private final DatabaseConnector dbConn;

    private static final Logger LOGGER = Logger.get(EmployeeDao.class);

    /**
     * Constructor with injectable database connection instance for mock testing.
     *
     * @param dbConn The database connection instance
     */
    public EmployeeDao(DatabaseConnector dbConn) {
        this.dbConn = dbConn;
    }

    /**
     * Constructor which retrieves an instance of the database connection.
     */
    public EmployeeDao() {
        dbConn = DbConn.i();
    }

    /**
     * Retrieves an employee record from the database table that matches the passed id.
     * Throws an exception if no employee with that id is present in the database.
     *
     * @param employeeId the id of the employee in the database
     * @return The Employee matching the passed id
     * @throws NoSuchElementException if no matching id is found.
     */
    @Override
    public Optional<Employee> get(int employeeId) throws SQLException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("employeeId must be greater than 0");
        }

        String query = "SELECT id, name, birth_year FROM lab_employees WHERE id = ?";
        Employee employee = null;
        prst = dbConn.prepareStatement(query);
        prst.setInt(1, employeeId);
        prst.executeQuery();
        ResultSet rs = prst.getResultSet();
        if (rs.next()) {
            int id = rs.getInt("id");
            int birthYear = rs.getInt("birth_year");
            String name = rs.getString("name");
            employee = new Employee(id, name, birthYear);
            LOGGER.info(() -> String.format("Employee retrieved: %s, Id %d", name, id));
        }
        return Optional.ofNullable(employee);
    }

    /**
     * Creates a record of a temporary employee object without id in the database, and if successful
     * will return a copy of the saved employee with the generated id.
     *
     * @param employee The employee record to save in the database. The id field will be ignored.
     * @return The saved employee record with a generated id.
     */
    @Override
    public Employee save(Employee employee) throws SQLException {
        if (employee == null) {
            throw new IllegalArgumentException("Cannot save a null employee");
        }

        String query = "INSERT INTO lab_employees(name, birth_year) VALUES(?, ?)";
        Employee employeeSaved = null;
        prst = dbConn.prepareStatement(query);
        prst.setString(1, employee.getName());
        prst.setInt(2, employee.getBirthYear());
        prst.executeUpdate();
        ResultSet rs = prst.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            employeeSaved = new Employee(id, employee.getName(), employee.getBirthYear());
            LOGGER.info(() -> String.format("Employee saved to database: %s, Id %d", employee.getName(), id));
        }
        return employeeSaved;
    }

    /**
     * Updates non-final fields of an employee in the database which matches the id of the passed employee object.
     *
     * @param employee An employee object containing new values to overwrite an existing one in the database
     * @return The updated Employee record from the database
     */
    @Override
    public Employee update(Employee employee) throws SQLException {
        if (employee == null) {
            throw new IllegalArgumentException("Cannot update a null employee.");
        }

        String query = "UPDATE lab_employees SET name = ? WHERE id = ?";
        prst = dbConn.prepareStatement(query);
        prst.setString(1, employee.getName());
        prst.setInt(2, employee.getId());
        prst.executeUpdate();

        Employee employeeUpdated = this.get(employee.getId()).orElseThrow();
        LOGGER.info(() -> String.format("Employee successfully updated: %s, Id %d",
                employeeUpdated.getName(), employeeUpdated.getId()));
        return employeeUpdated;
    }

    /**
     * Deletes an employee record from the database which matches the id of the passed employee object.
     *
     * @param employee An employee object matching the id of the record to be deleted
     * @return true if successful, otherwise false
     */
    @Override
    public boolean delete(Employee employee) throws SQLException {
        if (employee == null) {
            throw new IllegalArgumentException("Cannot delete a null employee");
        }

        String query = "DELETE FROM lab_employees WHERE id = ?";
        prst = dbConn.prepareStatement(query);
        prst.setInt(1, employee.getId());
        int affectedRows = prst.executeUpdate();
        if (affectedRows == 1) {
            LOGGER.info(() -> String.format("Employee successfully deleted: %s, id %d", employee.getName(), employee.getId()));
            return true;
        }
        LOGGER.info(() -> String.format("Failed to delete employee: %s, id %d", employee.getName(), employee.getId()));
        return false;
    }

    /**
     * Retrieves all employee records from the database.
     *
     * @return A List of Employee objects containing all employees in the database
     */
    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<Employee>();
        prst = dbConn.prepareStatement(
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
        return employees;
    }

    /**
     * Checks if an employee with the passed id exists in the database.
     *
     * @param id The employee id
     * @return true if present, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean existsById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be zero or negative");
        }

        String query = "SELECT EXISTS(SELECT 1 FROM lab_employees WHERE id = ?)";
        prst = dbConn.prepareStatement(query);
        prst.setInt(1, id);
        ResultSet rs = prst.executeQuery();
        if (rs.next()) {
            return rs.getBoolean(1);
        } else {
            return false;
        }
    }
}
