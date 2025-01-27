package domain;

import repository.PersonDao;

import java.sql.SQLException;

public class Person {

    private String name;
    private int id;
    private int birthYear;

    public Person(int id, String name, int birthYear) {
        setId(id);
        this.name = name;
        this.birthYear = birthYear;
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

    private void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
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

    public String toString() {
        return String.format("Name: %s, Birth Year: %d", name, birthYear);
    }

}
