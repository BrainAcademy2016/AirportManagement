package ua.com.airport.daoimpl;

import ua.com.airport.GuiFilter;
import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.dao.PassengersDao;
import ua.com.airport.entities.PassengersEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengersDaoImpl extends DataBaseUtil implements PassengersDao {
    private Connection con;
    private PreparedStatement prst;
    private ResultSet rs;
    private String query = "SELECT idPassenger, " +
            "FirstName, LastName, " +
            "Nationality, Passport, " +
            "Birthday, Sex, " +
            "ClassType, FlightNumber FROM PassengersInfo";

    private List<PassengersEntity> passengersList = new ArrayList<>();

    @Override
    public List<PassengersEntity> getAllPassengersInfo() {
        try {
            con = getConnectionDb();
            if (con != null) {
                prst = con.prepareStatement(query);
                rs = prst.executeQuery();
                if (!rs.isBeforeFirst()) {
                    System.out.printf("No data");
                }
                while (rs.next()) {
                    int passengerId = rs.getInt(1);
                    String firstName = rs.getString(2);
                    String lastName = rs.getString(3);
                    String nationality = rs.getString(4);
                    String passport = rs.getString(5);
                    String birthday = rs.getString(6);
                    String sex = rs.getString(7);
                    String classType = rs.getString(8);
                    String flightNum = rs.getString(9);

                    PassengersEntity currentPassenger = new PassengersEntity(
                            passengerId,
                            firstName,
                            lastName,
                            nationality,
                            passport,
                            birthday,
                            sex,
                            classType,
                            flightNum
                    );
                    passengersList.add(currentPassenger);
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
        return passengersList;
    }

    public List<PassengersEntity> getAllFilteredPassengers(List<GuiFilter> filtersList) {
        ArrayList<String> filterValuers = new ArrayList<>();
        filtersList.forEach(filter -> {
            if (filter.getSelectedValue() != null) {
                String filteredString;
                filterValuers.add(filter.getSelectedValue());
                if (filterValuers.size() == 1) {
                    filteredString = " WHERE " + filter.getSqlField() + "='" + filter.getSelectedValue() + "'";
                } else {
                    filteredString = " AND " + filter.getSqlField() + "='" + filter.getSelectedValue() + "'";
                }
                query = query + filteredString;
            }
        });
        return getAllPassengersInfo();
    }

    @Override
    public List<PassengersEntity> getPassengersInfoFlightNum() {
        return null;
    }

    @Override
    public PassengersEntity getPassenger(int id) {
        return null;
    }

    @Override
    public void deletePassenger(int id) {
        String query = "DELETE FROM PassengersInfo WHERE idPassenger = ?";
        try {
            con = getConnectionDb();
            if (con != null) {
                prst = con.prepareStatement(query);
                prst.setInt(1, id);
                prst.executeUpdate();
            }
        } catch (SQLException sqlE) {
            System.out.printf("Connection problem");
            System.out.println(sqlE);
        } finally {
            try {
                if (con != null) {
                    prst.close();
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
    public void addPassenger(PassengersEntity currentPassenger) {
        String query = "INSERT INTO PassengersInfo (FirstName, LastName, " +
                "Nationality, Birthday, Passport, Sex, " +
                "ClassType, FlightNumber)" +
                "VALUES (? ,?, ?, ?, ?, ?, ?, ?)";
        try {
            con = getConnectionDb();
            if (con != null) {
                prst = con.prepareStatement(query);
                prst.setString(1, currentPassenger.getFirstName());
                prst.setString(2, currentPassenger.getLastName());
                prst.setString(3, currentPassenger.getNationality());
                prst.setString(4, currentPassenger.getPassport());
                prst.setString(5, currentPassenger.getBirthday());
                prst.setString(6, currentPassenger.getSex());
                prst.setString(7, currentPassenger.getClassType());
                prst.setString(8, currentPassenger.getFlightNum());
                prst.executeUpdate();
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
            passengersList.add(currentPassenger);

        }
    }

    @Override
    public void updatePassenger(PassengersEntity passengersEntity) {
        String query = "UPDATE PassengersInfo SET FirstName = ?, LastName = ?, Nationality = ?," +
                "Passport =?, Birthday = ?, Sex = ?," +
                "ClassType = ?, FlightNumber = ?" +
                "WHERE idPassenger=?";
        try {
            con = getConnectionDb();
            if (con != null) {
                prst = con.prepareStatement(query);
                prst.setString(1, passengersEntity.getFirstName());
                prst.setString(2, passengersEntity.getLastName());
                prst.setString(3, passengersEntity.getNationality());
                prst.setString(4, passengersEntity.getPassport());
                prst.setString(5, passengersEntity.getBirthday());
                prst.setString(6, passengersEntity.getSex());
                prst.setString(7, passengersEntity.getClassType());
                prst.setString(8, passengersEntity.getFlightNum());
                prst.setInt(9, passengersEntity.getId());

                prst.executeUpdate();
            }
        } catch (SQLException sqlE) {
            System.out.printf("Connection problem");
            System.out.println(sqlE);
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

        }
    }
}