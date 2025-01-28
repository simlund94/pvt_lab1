package domain;

public class Person {

    private String name;
    private final int id;
    private final int birthYear;

    public Person(int id, String name, int birthYear) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
        this.birthYear = birthYear;
        setName(name);
    }

    public Person(String name, int birthYear) {
        this(0, name, birthYear);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
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

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Birth Year: %d", id, name, birthYear);
    }

}
