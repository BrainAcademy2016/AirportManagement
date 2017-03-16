package ua.com.airport.daoimpl;

import ua.com.airport.GuiFilter;
import ua.com.airport.DataBaseUtil;
import ua.com.airport.dao.FlightsDao;
import ua.com.airport.entities.FlightsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightsDaoImpl extends DataBaseUtil implements FlightsDao {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String query = "SELECT a.idFlight, " +
            "a.FlightNumber, " +
            "a.DepartureCity, a.DepartureTime, " +
            "a.ArrivalCity, a.ArivalTime, " +
            "a.Gate, a.Terminal, a.FlightStatus, " +
            "b.ClassType, b.Price FROM Flights a INNER JOIN PriceList b ON a.FlightNumber = b.FlightNumber";

    private List<FlightsEntity> flightsList = new ArrayList<>();
    private List<String> flightNumbers = new ArrayList<>();

    @Override
    public List<FlightsEntity> getAllFlights() {
        try {
            con = getConnectionDb();
            if (con != null){
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();
                if (!rs.isBeforeFirst() ) {
                    System.out.println("No data");
                }
                while (rs.next()) {
                    int flightId = rs.getInt(1);
                    String flightNumber = rs.getString(2);
                    String depCity = rs.getString(3);
                    String depTime = rs.getString(4);
                    String arrCity = rs.getString(5);
                    String arrTime = rs.getString(6);
                    String gate = rs.getString(7);
                    String terminal = rs.getString(8);
                    String flightStatus = rs.getString(9);
                    String flightClass = rs.getString(10);
                    String flightPrice = rs.getString(11);
                    FlightsEntity currentFlight = new FlightsEntity(
                            flightId,
                            arrTime,
                            depTime,
                            flightNumber,
                            flightStatus,
                            gate,
                            terminal,
                            depCity,
                            arrCity,
                            flightClass,
                            new Double(flightPrice)
                    );
                   flightsList.add(currentFlight);
                }
            }
        } catch (SQLException sqlEx) {
            System.out.println("Problems with connection");
        } finally {
            try { if(con!=null){con.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){pstmt.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){rs.close();} } catch(SQLException se) { /*can't do anything */ }
        }
        return flightsList;
    }

    public List<FlightsEntity> getAllFilteredFlights(List<GuiFilter> filtersList){
        ArrayList<String> filterValuers = new ArrayList<>();
        filtersList.forEach(filter->{
            if (filter.getSelectedValue()!=null) {
                String filteredString;
                filterValuers.add(filter.getSelectedValue());
                String tableShortName = "a.";
                if (filter.getSqlField().equals("ClassType") || filter.getSqlField().equals("Price")){
                    tableShortName = "b.";
                }
                if (filterValuers.size() == 1) {
                    filteredString = " WHERE " + tableShortName+filter.getSqlField() + "='"+filter.getSelectedValue()+"'";
                } else {
                    filteredString = " AND " + tableShortName+filter.getSqlField() + "='"+filter.getSelectedValue()+"'";
                }
                query = query+filteredString;
            }
        });

        return getAllFlights();
    }

    @Override
    public List<String> getAllFightNumbers(){
        String query = "SELECT DISTINCT  FlightNumber FROM Flights";
        try {
            con = getConnectionDb();
            if (con != null){
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();
                if (!rs.isBeforeFirst() ) {
                    System.out.println("No data");
                }
                while (rs.next()) {
                    String flightNumber = rs.getString(1);
                    flightNumbers.add(flightNumber);
                }
            }
        } catch (SQLException sqlEx) {
            System.out.println("Problems with connection");
        } finally {
            try { if(con!=null){con.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){pstmt.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){rs.close();} } catch(SQLException se) { /*can't do anything */ }
        }
        return flightNumbers;
    }

    @Override
    public List<FlightsEntity> getFlightByNumber(String number) {
        return null;
    }

    @Override
    public List<FlightsEntity> getFlightByCityDeparture(String cityOfDeparture) {
        return null;
    }

    @Override
    public List<FlightsEntity> getFlightByCityArival(String cityOfArrival) {
        return null;
    }

    @Override
    public List<FlightsEntity> getFlightByDepartureArival(String cityOfArrival, String cityOfDeparture) {
        return null;
    }

    @Override
    public void deleteFLight(FlightsEntity flightsEntity) {
        String query = "DELETE FROM Flights WHERE idFlight = ?";
        try{
            con = getConnectionDb();
            if(con != null){
                pstmt = con.prepareStatement(query);
             //   pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException sqlE) {
            System.out.printf("Connection problem");
            System.out.println(sqlE);

        } finally {
            try {
                if (con != null) {
                    pstmt.close();
                }
            } catch (SQLException se) { /*can't do anything */ }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) { /*can't do anything */ }
        }
    }

    @Override
    public void createFlight(FlightsEntity currentFlight) {
        String query = "INSERT INTO Flights (FlightNumber, "+"DepartureCity, DepartureTime, " +
        "ArrivalCity, ArivalTime, " +
                "Gate, Terminal, FlightStatus, " +
                "ClassType, Price)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = getConnectionDb();
            if (con != null) {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, currentFlight.getFlightNumber());
                pstmt.setString(2, currentFlight.getCityOfDeparture());
                pstmt.setString(3, currentFlight.getDepartureTime());
                pstmt.setString(4, currentFlight.getCityOfArrival());
                pstmt.setString(5, currentFlight.getArrivalTime());
                pstmt.setString(6, currentFlight.getGate());
                pstmt.setString(7, currentFlight.getClassType());
                pstmt.setString(8, currentFlight.getFlightStatus());
                pstmt.setString(9, String.valueOf(currentFlight.getClassPrice()));
                pstmt.setString(10,currentFlight.getTerminal());
                pstmt.executeUpdate();
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
                    pstmt.close();
                }
            } catch (SQLException se) { /*can't do anything */ }
            flightsList.add(currentFlight);

        }
    }

    @Override
    public void updateFlight(FlightsEntity flightsEntity) {
        String query = "UPDATE Flights SET FlightNumber = ?, "+"DepartureCity = ?, DepartureTime = ?," +
                "ArrivalCity = ?, ArivalTime = ?," +
                "Gate = ?, Terminal = ?, FlightStatus = ?," +
                "ClassType = ?, Price = ?" + "WHERE idFlight = ?";
        try {
            con = getConnectionDb();
            if (con != null) {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, flightsEntity.getFlightNumber());
                pstmt.setString(2, flightsEntity.getCityOfDeparture());
                pstmt.setString(3, flightsEntity.getDepartureTime());
                pstmt.setString(4, flightsEntity.getCityOfArrival());
                pstmt.setString(5, flightsEntity.getArrivalTime());
                pstmt.setString(6, flightsEntity.getGate());
                pstmt.setString(7, flightsEntity.getClassType());
                pstmt.setString(8, flightsEntity.getFlightStatus());
                pstmt.setString(9, String.valueOf(flightsEntity.getClassPrice()));
                pstmt.setString(10,flightsEntity.getTerminal());
           //     pstmt.setInt(11, get);
                pstmt.executeUpdate();
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
                    pstmt.close();
                }
            } catch (SQLException se) { /*can't do anything */ }
        }
    }


}

