package spike;

import domain.Person;
import repository.PersonDao;

import java.sql.SQLException;
import java.util.List;

public class Spike {

    public static void main(String[] args) throws SQLException {
        PersonDao dao = new PersonDao();
        List<Person> persons = dao.getAllPersons();
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}