import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDao {

    private PreparedStatement prst = null;

    public Person getPerson(int personId) throws SQLException {
        prst = DbConn.i().prepareStatement(
                "SELECT id, name, age FROM personer "
        + "WHERE id = ?");
        prst.setInt(1, personId);
        prst.executeQuery();
        ResultSet rs = prst.getResultSet();

        Person person = null;
        if(rs.next()) {
            String id = Integer.toString(rs.getInt("id"));
            int age = rs.getInt("age");
            String name = rs.getString("name");
            person = new Person(id, name, age);
        }
        return person;
    }

}
