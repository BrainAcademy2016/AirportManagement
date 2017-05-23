package ua.com.airport.daoimpl;

import ua.com.airport.dao.PriceDao;
import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.entities.PriceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriceDaoImpl extends DataBaseUtil implements PriceDao {
    private Connection con;
    private PreparedStatement prst;
    private ResultSet rs;




    @Override
    public List<PriceEntity> getAllPrices() {
        return null;
    }

    @Override
    public List<PriceEntity> getPricesByFlightNum(String flightNumber) {
        String query = "SELECT idPrice, ClassType, " +
                "Price, FlightNumber FROM PriceList " +
                "WHERE FlightNumber = '" + flightNumber+"'";
        List<PriceEntity> priceList = new ArrayList<>();
        try{
            con = getConnectionDb();
            if (con != null){
                prst = con.prepareStatement(query);
                rs = prst.executeQuery();
                if (!rs.isBeforeFirst() ) {
                    System.out.println("No data");
                }
                while (rs.next()) {
                    int priceId = rs.getInt(1);
                    String classType = rs.getString(2);
                    String price = rs.getString(3);
                    String flight = rs.getString(4);

                    PriceEntity currentPrice = new PriceEntity(priceId, classType, new Double(price), flight);

                    priceList.add(currentPrice);
                }
            }
        } catch (SQLException e){
            System.out.println("Problems with connection");
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

        return priceList;
    }

    @Override
    public void deletePrice(int id) {

    }

    @Override
    public void createPrice(PriceEntity priceListEntity) {

    }

    @Override
    public void updatePrice(PriceEntity priceListEntity) {

    }
}
