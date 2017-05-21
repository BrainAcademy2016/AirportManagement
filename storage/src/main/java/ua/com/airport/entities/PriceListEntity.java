package ua.com.airport.entities;

public class PriceListEntity {
    private int id;
    private String classType;
    private double price;
    private String flightNumber;

    public PriceListEntity() {
    }

    public PriceListEntity(int id, String classType, double price, String flightNumber) {
        this.id = id;
        this.classType = classType;
        this.price = price;
        this.flightNumber = flightNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
