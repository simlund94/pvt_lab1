package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    Employee employeeWithId;
    Employee employeeWithoutId;

    @BeforeEach
    void setUp() {
        employeeWithId = new Employee(32, "Simon", 1994);
        employeeWithoutId = new Employee("Simon", 1994);
    }

    @AfterEach
    void tearDown() {
        employeeWithId = null;
        employeeWithoutId = null;
    }

    @Test
    void getValidId() {
        assertEquals(32, employeeWithId.getId(), "Employee id should be 32");
    }

    @Test
    void getValidName() {
        assertEquals("Simon", employeeWithId.getName(), "Employee name should be Simon");
    }

    @Test
    void getValidBirthYear() {
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
    void createEmployeeWithLowBirthYear() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("Simon", 1899),
                "The employee constructor should throw an exception when passed a birth year below 1900");
    }

    @Test
    void createEmployeeWithHighBirthYear() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("Simon", 3000),
                "The employee constructor should throw an exception when passed a birth year higher than the current date");
    }

    @Test
    void createEmployeeWithLowId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(-1, "Simon", 1994),
                "The employee constructor should throw an exception when passed a low id"
        );
    }
}