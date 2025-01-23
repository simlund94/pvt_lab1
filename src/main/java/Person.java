import java.sql.SQLException;

public class Person {

    private String id;
    private String name;
    private int age;

    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) throws SQLException {
        PersonDao dao = new PersonDao();
        Person person = dao.getPerson(1);
        System.out.println(person.getName());
    }
}
