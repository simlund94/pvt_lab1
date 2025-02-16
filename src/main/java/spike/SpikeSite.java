package spike;

import domain.Site;
import repository.Dao;
import repository.SiteDao;

import java.util.List;

public class SpikeSite {

    public static void main(String[] args) {

        Dao<Site> dao = new SiteDao();

        // get site
        Site site1 = dao.get(1);
        System.out.println(site1);
        site1.getRooms().forEach(room -> System.out.println(room));
        System.out.println();

        // save site
        Site site2 = new Site("Vågskrivaren", "Vågskrivargatan 34A", 80320, "Gävle", "GÄVLE VÄSTER 12:12");
        site2 = dao.save(site2);
        System.out.println(site2);

        // update site
        site2.setName("Kålhagsmätarhuset");
        boolean result1 = dao.update(site2);
        System.out.printf("%s updated: %s\n", site2.getName(), result1);

        site2 = dao.get(site2.getId());
        System.out.println(site2);
        System.out.println();

        // delete site
        boolean result2 = dao.delete(site2);
        System.out.printf("%s deleted: %s\n", site2.getName(), result2);
        System.out.println();

        // get all sites
        List<Site> sites = dao.getAll();
        sites.forEach(site -> System.out.println(site));

    }

}
