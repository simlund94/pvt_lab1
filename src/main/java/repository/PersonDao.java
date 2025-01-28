package repository;

import db.DbConn;
import domain.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements Dao<Person> {

    private PreparedStatement prst = null;

    @Override
    public Person get(int personId) {
        Person person = null;
        try {
            prst = DbConn.i().prepareStatement(
                    "SELECT id, name, birth_year FROM lab_persons "
                            + "WHERE id = ?");
            prst.setInt(1, personId);
            prst.executeQuery();
            ResultSet rs = prst.getResultSet();

            if (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("birth_year");
                String name = rs.getString("name");
                person = new Person(id, name, age);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return person;
    }

    @Override
    public Person save(Person person) {
        Person personSaved = null;
        try {
            prst = DbConn.i().prepareStatement("INSERT INTO lab_persons(name, birth_year) VALUES(?, ?)");
            prst.setString(1, person.getName());
            prst.setInt(2, person.getBirthYear());
            prst.executeUpdate();
            ResultSet rs = prst.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                personSaved = new Person(id, person.getName(), person.getBirthYear());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return personSaved;
    }

    @Override
    public Person update(Person toUpdate) {
        try {
            prst = DbConn.i().prepareStatement("UPDATE lab_persons SET name = ? WHERE id = ?");
            prst.setString(1, toUpdate.getName());
            prst.setInt(2, toUpdate.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toUpdate;
    }

    @Override
    public Person delete(Person toDelete) {
        try {
            prst = DbConn.i().prepareStatement("DELETE FROM lab_persons WHERE id = ?");
            prst.setInt(1, toDelete.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<Person>();
        try {
            prst = DbConn.i().prepareStatement(
                    "SELECT id, name, birth_year FROM lab_persons"
            );
            prst.executeQuery();
            ResultSet rs = prst.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                int birthYear = rs.getInt("birth_year");
                String name = rs.getString("name");
                Person person = new Person(id, name, birthYear);
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

}
