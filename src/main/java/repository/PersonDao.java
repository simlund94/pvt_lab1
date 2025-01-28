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
    public Person save(int id) {
        return null;
    }

    @Override
    public Person update(Person toUpdate) {
        return null;
    }

    @Override
    public Person delete(Person toDelete) {
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

    public void updateName(int id, String name) {
        try {
            prst = DbConn.i().prepareStatement(
                    "UPDATE lab_persons SET name = ? WHERE id = ?"
            );
            prst.setString(1, name);
            prst.setInt(2, id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
