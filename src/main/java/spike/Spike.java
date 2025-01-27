package spike;

import domain.Person;
import repository.PersonDao;

import java.util.List;

public class Spike {

    public static void main(String[] args) {
        PersonDao dao = new PersonDao();
        List<Person> persons = dao.getAll();
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}