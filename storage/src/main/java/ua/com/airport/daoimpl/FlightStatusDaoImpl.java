package ua.com.airport.daoimpl;

import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.dao.FlightStatusDao;
import ua.com.airport.entities.FlightStatusEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightStatusDaoImpl extends DataBaseUtil implements FlightStatusDao {
    private Connection con;
    private PreparedStatement prst;
    private ResultSet rs;
    private String query = "SELECT StatusName FROM FlightStatus";

    private List<String> flightStatusList = new ArrayList<>();

    @Override
    public List<String> getFlightStatus() {
        try {
            con = getConnectionDb();
            if (con != null) {
                prst = con.prepareStatement(query);
                rs = prst.executeQuery();
                if (!rs.isBeforeFirst()) {
                    System.out.printf("No data");
                }
                while (rs.next()) {
                    String className = rs.getString(1);
                    flightStatusList.add(className);
                }
            }
        } catch (SQLException sqlE) {
            System.out.printf("Connection problem");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) { /*can't do anything */ }
            try {
                if (con != null) {
                    prst.close();
                }
            } catch (SQLException se) { /*can't do anything */ }
            try {
                if (con != null) {
                    rs.close();
                }
            } catch (SQLException se) { /*can't do anything */ }

        }
        return flightStatusList;
    }

    @Override
    public void deleteFlightStatus(String flightStatus) {

    }

    @Override
    public void createFlightStatus(FlightStatusEntity flightStatusEntity) {

    }

    @Override
    public void updateFlightStatus(FlightStatusEntity flightStatusEntity) {

    }
}
