package ua.com.airport.dao;

import ua.com.airport.entities.PriceEntity;
import java.util.List;

public interface PriceDao {
    /**
     * @return list of all prices
     */
    List<PriceEntity> getAllPrices();

    /**
     * @param flightNumber
     * @return list of prives by selected flight number
     */
    List<PriceEntity> getPricesByFlightNum(String flightNumber);

    void deletePrice(int id);

    void createPrice(PriceEntity priceListEntity);

    void updatePrice(PriceEntity priceListEntity);
}
