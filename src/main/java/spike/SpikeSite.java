package spike;

import db.DbConn;
import domain.Site;
import repository.Dao;
import repository.SiteDao;
import service.room.DeleteRoomService;
import service.site.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Demo program to demonstrate the DAO for the Site class.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
public class SpikeSite {

    public static void main(String[] args) throws SQLException {


        // get site
        Site site1 = new GetSiteByIdService(1).execute();
        System.out.println(site1);
        System.out.printf("- %s Contains the rooms: -\n", site1.getName());
        site1.getRooms().forEach(room -> System.out.println(room));
        System.out.println();

        // save site
        System.out.println(" --- Saving a site ---");
        Site site2 = new Site("Vågskrivaren", "Vågskrivargatan 34A", 80320, "Gävle", "GÄVLE VÄSTER 12:12");
        site2 = new SaveSiteService(site2).execute();
        System.out.println(site2);
        System.out.println();

        // update site
        System.out.println("--- Updating a site ---");
        site2.setName("Kålhagsmätarhuset");
        Site result1 = new UpdateSiteService(site2).execute();
        System.out.printf("%s updated: %s\n", site2.getName(), result1);

        site2 = new GetSiteByIdService(site2.getId()).execute();
        System.out.println(site2);
        System.out.println();

        System.out.println("--- Checking and deleting a site ---");
        // check if site exists
        System.out.printf("Site with id %d exists in database: %s\n", site2.getId(), new CheckIfSiteExistsService(site2).execute());

        // delete site
        boolean result2 = new DeleteSiteService(site2).execute();
        System.out.printf("%s deleted: %s\n", site2.getName(), result2);

        // check if site exists again
        System.out.printf("Site with id %d exists in database: %s\n", site2.getId(), new CheckIfSiteExistsService(site2).execute());
        System.out.println();

        // get all sites
        System.out.println("--- Printing all sites in the database ---");
        List<Site> sites = new GetAllSitesService().execute();
        sites.forEach(System.out::println);
    }
}
