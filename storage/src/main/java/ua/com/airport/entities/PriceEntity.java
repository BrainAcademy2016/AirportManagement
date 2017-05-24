package ua.com.airport.entities;

import javafx.beans.property.*;

public class PriceEntity {
    private SimpleIntegerProperty id;
    private SimpleStringProperty classType;
    private SimpleDoubleProperty price;
    private SimpleStringProperty flightNumber;

    public PriceEntity() {
    }

    public PriceEntity(int id, String classType, double price, String flightNumber) {
        this.id = new SimpleIntegerProperty(id);
        this.classType = new SimpleStringProperty(classType);
        this.price = new SimpleDoubleProperty(price);
        this.flightNumber = new SimpleStringProperty(flightNumber);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getClassType() {
        return classType.get();
    }

    public StringProperty classTypeProperty() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType.set(classType);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getFlightNumber() {
        return flightNumber.get();
    }

    public StringProperty flightNumberProperty() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber.set(flightNumber);
    }

    @Override
    public String toString(){
        return id + " " + classType + " " + price + " " + flightNumber;
    }
}
