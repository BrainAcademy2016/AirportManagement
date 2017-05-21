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
    private PreparedStatement pstmt;
    private ResultSet rs;




    @Override
    public List<PriceEntity> getAllPrices() {
        return null;
    }

    @Override
    public List<PriceEntity> getPricesByFlightNum(String flightNumber) {
        String query = "SELECT idPrice, ClassType, " +
                "Price, FlightNumber FROM PriceList " +
                "WHERE FlightNumber = " + flightNumber;
        List<PriceEntity> priceList = new ArrayList<>();
        try{
            con = getConnectionDb();
            if (con != null){
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();
                if (!rs.isBeforeFirst() ) {
                    System.out.println("No data");
                }
                while (rs.next()) {
                    int priceId = rs.getInt(1);
                    String classType = rs.getString(2);
                    double price = rs.getDouble(3);
                    String flight = rs.getString(4);

                    PriceEntity currentPrice = new PriceEntity(priceId, classType, price, flight);

                    priceList.add(currentPrice);
                }
            }
        } catch (SQLException e){
            System.out.println("Problems with connection");
        } finally {

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
