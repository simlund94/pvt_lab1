package domain;

/**
 * A class representing an employee.
 */
public class Employee {

    private String name;
    private final int id;
    private final int birthYear;

    public Employee(int id, String name, int birthYear) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
        this.birthYear = birthYear;
        setName(name);
    }

    public Employee(String name, int birthYear) {
        this(0, name, birthYear);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name cannot be longer than 50 characters");
        }
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Description of the object and its state, only for demo and debugging.
     */
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Birth Year: %d", id, name, birthYear);
    }

}
