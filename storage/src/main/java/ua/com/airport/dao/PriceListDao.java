package ua.com.airport.dao;

import ua.com.airport.entities.PriceListEntity;
import java.util.List;

public interface PriceListDao {
    /**
     * @return list of all prices
     */
    List<PriceListEntity> getAllPrices();

    /**
     * @param flightNumber
     * @return list of prives by selected flight number
     */
    List<PriceListEntity> getPricesByFlightNum(String flightNumber);

    void deletePrice(int id);

    void createPrice(PriceListEntity priceListEntity);

    void updatePrice(PriceListEntity priceListEntity);
}
