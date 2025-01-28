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

        Person person1 = dao.save(new Person("Gurra Kaka", 1850));
        System.out.println("Person saved: "+ person1);
        persons = dao.getAll();
        for (Person person : persons) {
            System.out.println(person);
        }

        Person person2 = new Person(7, "Karrotter Morötter", 1900);
        person2 = dao.update(person2);
        System.out.println("Person updated: "+ person2);
        persons = dao.getAll();
        for (Person person : persons) {
            System.out.println(person);
        }

    }
}