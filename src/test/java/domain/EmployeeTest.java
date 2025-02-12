package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    Employee employeeWithId;
    Employee employeeWithoutId;

    final String name = "Simon";
    final int id = 32;
    final int birthYear = 1994;

    final int lowInvalidBirthYear = 1899;
    final int highInvalidBirthYear = 2100;
    final int negativeId = -1;
    String tooLongName = "Hubert Blaine Wolfeschlegelsteinhausenbergerdorff Gustavus Gundalf";
    String emptyName = "";

    @BeforeEach
    void setUp() {
        employeeWithId = new Employee(id, name, birthYear);
        employeeWithoutId = new Employee(name, birthYear);
    }

    @AfterEach
    void tearDown() {
        employeeWithId = null;
        employeeWithoutId = null;
    }

    @Test
    void getId() {
        assertEquals(id, employeeWithId.getId(), "Employee id should be " + id);
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
        assertEquals(birthYear, employeeWithId.getBirthYear(), "Birth year should be " + birthYear);
    }

    @Test
    void createEmployeeWithNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(null, birthYear),
                "The employee class should throw an exception when passed a null name");
    }

    @Test
    void createEmployeeWithEmptyName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(emptyName, birthYear),
                "The employee constructor should throw and exception when passed an empty name");
    }

    @Test
    void setTooLongName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> employeeWithId.setName(tooLongName),
                "Setting a name over 50 characters should throw an exception."
        );
    }

    @Test
    void createEmployeeWithTooLowBirthYear() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(name, lowInvalidBirthYear),
                "The employee constructor should throw an exception when passed a birth year below 1900");
    }

    @Test
    void createEmployeeWithTooHighBirthYear() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(name, highInvalidBirthYear),
                "The employee constructor should throw an exception when passed a birth year after the current date");
    }

    @Test
    void createEmployeeWithNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(negativeId, name, birthYear),
                "The employee constructor should throw an exception when passed a negative id"
        );
    }
}