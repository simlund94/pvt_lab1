package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    Employee employeeWithId;
    Employee employeeWithoutId;

    String name = "Simon";

    @BeforeEach
    void setUp() {
        employeeWithId = new Employee(32, name, 1994);
        employeeWithoutId = new Employee(name, 1994);
    }

    @AfterEach
    void tearDown() {
        employeeWithId = null;
        employeeWithoutId = null;
    }

    @Test
    void getId() {
        assertEquals(32, employeeWithId.getId(), "Employee id should be 32");
    }

    @Test
    void GetIdFromEmployeeWithNoId() {
        assertEquals(
                0,
                employeeWithoutId.getId(),
                "An employee created with the non-id constructor should have an id of 0, representing no id."
        );
    }

    @Test
    void getName() {
        assertEquals(name, employeeWithId.getName(), "Employee name should be " + name);
    }

    @Test
    void getBirthYear() {
        assertEquals(1994, employeeWithId.getBirthYear(), "Birth year should be 1994");
    }

    @Test
    void createEmployeeWithNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(null, 1994),
                "The employee class should throw an exception when passed a null name");
    }

    @Test
    void createEmployeeWithEmptyName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("", 1994),
                "The employee constructor should throw and exception when passed an empty name");
    }

    @Test
    void createEmployeeWithTooLowBirthYear() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("Simon", 1899),
                "The employee constructor should throw an exception when passed a birth year below 1900");
    }

    @Test
    void createEmployeeWithTooHighBirthYear() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("Simon", 3000),
                "The employee constructor should throw an exception when passed a birth year higher than the current date");
    }

    @Test
    void createEmployeeWithNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(-1, "Simon", 1994),
                "The employee constructor should throw an exception when passed a negative id"
        );
    }
}