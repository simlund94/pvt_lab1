package repository;

import db.DbConn;
import domain.Room;
import domain.Site;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteDao implements Dao<Site> {

    private PreparedStatement prst = null;

    @Override
    public Site save(Site site) {
        String query = "INSERT INTO lab_sites(name, address, postal_code, postal_area, property_designation) " +
                "VALUES(?, ?, ?, ?, ?)";
        Site siteSaved = null;
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setString(1, site.getName());
            prst.setString(2, site.getAddress());
            prst.setInt(3, site.getPostalCode());
            prst.setString(4, site.getPostalArea());
            prst.setString(5, site.getPropertyDesignation());
            prst.executeUpdate();

            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                siteSaved = new Site(newId,
                        site.getName(),
                        site.getAddress(),
                        site.getPostalCode(),
                        site.getPostalArea(),
                        site.getPropertyDesignation(),
                        site.getRooms());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siteSaved;
    }

    @Override
    public boolean update(Site siteToUpdate) {
        String query = "UPDATE lab_sites SET name = ? WHERE id = ?";
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setString(1, siteToUpdate.getName());
            prst.setInt(2, siteToUpdate.getId());
            int affectedRows = prst.executeUpdate();
            int expectedAffectedRows = 1;
            if (affectedRows == expectedAffectedRows) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Site siteToDelete) {
        String query = "DELETE FROM lab_sites WHERE id = ?";
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setInt(1, siteToDelete.getId());
            int affectedRows = prst.executeUpdate();
            int expectedAffectedRows = 1;
            if (affectedRows == expectedAffectedRows) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Site get(int id) {
        String query = "SELECT id, name, address, postal_code, postal_area, property_designation FROM lab_sites " +
                "WHERE id = ?";
        Site fetchedSite = null;
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.setInt(1, id);
            prst.executeQuery();
            ResultSet rs = prst.getResultSet();
            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                int postalCode = rs.getInt("postal_code");
                String postalArea = rs.getString("postal_area");
                String propertyDesignation = rs.getString("property_designation");
                List<Room> roomsOnSite = new RoomDao().getAllRoomsOnSite(id);
                fetchedSite = new Site(id, name, address, postalCode, postalArea, propertyDesignation, roomsOnSite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetchedSite;
    }

    @Override
    public List<Site> getAll() {
        String query = "SELECT id, name, address, postal_code, postal_area, property_designation FROM lab_sites";
        List<Site> fetchedSites = new ArrayList<>();
        RoomDao roomDao = new RoomDao();
        try {
            prst = DbConn.i().prepareStatement(query);
            prst.executeQuery();
            ResultSet rs = prst.getResultSet();
            while (rs.next()) {
                int siteId = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int postalCode = rs.getInt("postal_code");
                String postalArea = rs.getString("postal_area");
                String propertyDesignation = rs.getString("property_designation");
                List<Room> roomsOnSite = roomDao.getAllRoomsOnSite(siteId);
                fetchedSites.add(new Site(siteId, name, address, postalCode, postalArea, propertyDesignation, roomsOnSite));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetchedSites;
    }

}
