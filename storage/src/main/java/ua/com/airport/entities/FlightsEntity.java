package ua.com.airport.entities;

import javafx.beans.property.*;

public class FlightsEntity {
    private IntegerProperty id;
    private StringProperty arrivalTime;
    private StringProperty departureTime;
    private StringProperty flightNumber;
    private StringProperty flightStatus;
    private StringProperty gate;
    private StringProperty terminal;
    private StringProperty cityOfDeparture;
    private StringProperty cityOfArrival;

    public FlightsEntity(){
    }

    public FlightsEntity(String text, String departureCityText, String arrivalCityText, String classFlightText, String priceText, String statusText){
    }

    public FlightsEntity(String arrivalTime, String departureTime, String flightNumber, String flightStatus, String gate, String terminal, String cityOfDeparture, String cityOfArrival) {
        this.arrivalTime =  new SimpleStringProperty(arrivalTime);
        this.departureTime =  new SimpleStringProperty(departureTime);
        this.flightNumber =  new SimpleStringProperty(flightNumber);
        this.flightStatus =  new SimpleStringProperty(flightStatus);
        this.gate =  new SimpleStringProperty(gate);
        this.terminal =  new SimpleStringProperty(terminal);
        this.cityOfDeparture =  new SimpleStringProperty(cityOfDeparture);
        this.cityOfArrival =  new SimpleStringProperty(cityOfArrival);
    }

    public FlightsEntity(int id, String arrivalTime, String departureTime, String flightNumber, String flightStatus, String gate, String terminal, String cityOfDeparture, String cityOfArrival) {
        this.id =  new SimpleIntegerProperty(id);
        this.arrivalTime =  new SimpleStringProperty(arrivalTime);
        this.departureTime =  new SimpleStringProperty(departureTime);
        this.flightNumber =  new SimpleStringProperty(flightNumber);
        this.flightStatus =  new SimpleStringProperty(flightStatus);
        this.gate =  new SimpleStringProperty(gate);
        this.terminal =  new SimpleStringProperty(terminal);
        this.cityOfDeparture =  new SimpleStringProperty(cityOfDeparture);
        this.cityOfArrival =  new SimpleStringProperty(cityOfArrival);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public StringProperty departureTimeProperty() {
        return departureTime;
    }

    public StringProperty flightNumberProperty() {
        return flightNumber;
    }

    public StringProperty flightStatusProperty() {
        return flightStatus;
    }

    public StringProperty gateProperty() {
        return gate;
    }

    public StringProperty terminalProperty() {
        return terminal;
    }

    public StringProperty cityOfDepartureProperty() {
        return cityOfDeparture;
    }

    public StringProperty cityOfArrivalProperty() {
        return cityOfArrival;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getArrivalTime() {
        return arrivalTime.get();
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public String getDepartureTime() {
        return departureTime.get();
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime.set(departureTime);
    }

    public String getFlightNumber() {
        return flightNumber.get();
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber.set(flightNumber);
    }

    public String getFlightStatus() {
        return flightStatus.get();
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus.set(flightStatus);
    }

    public String getGate() {
        return gate.get();
    }

    public void setGate(String gate) {
        this.gate.set(gate);
    }

    public String getTerminal() {
        return terminal.get();
    }

    public void setTerminal(String terminal) {
        this.terminal.set(terminal);
    }

    public String getCityOfDeparture() {
        return cityOfDeparture.get();
    }

    public void setCityOfDeparture(String cityOfDeparture) {
        this.cityOfDeparture.set(cityOfDeparture);
    }

    public String getCityOfArrival() {
        return cityOfArrival.get();
    }

    public void setCityOfArrival(String cityOfArrival) {
        this.cityOfArrival.set(cityOfArrival);
    }
}
