package repository;

import db.DbConn;
import domain.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private PreparedStatement prst = null;

    public Person getPerson(int personId) throws SQLException {
        prst = DbConn.i().prepareStatement(
                "SELECT id, name, birth_year FROM lab_persons "
        + "WHERE id = ?");
        prst.setInt(1, personId);
        prst.executeQuery();
        ResultSet rs = prst.getResultSet();

        Person person = null;
        if(rs.next()) {
            int id = rs.getInt("id");
            int age = rs.getInt("birth_year");
            String name = rs.getString("name");
            person = new Person(id, name, age);
        }
        return person;
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<Person>();
        prst = DbConn.i().prepareStatement(
                "SELECT id, name, birth_year FROM lab_persons"
        );
        prst.executeQuery();
        ResultSet rs = prst.getResultSet();
        while(rs.next()) {
            int id = rs.getInt("id");
            int birthYear = rs.getInt("birth_year");
            String name = rs.getString("name");
            Person person = new Person(id, name, birthYear);
            persons.add(person);
        }
        return persons;
    }

}
