package adminInterface;

/**
 * Created by Alish on 07.12.2016.
 */
public class Flights {

    private int idFlight;
    private double arivalTime;
    private double departureTime;
    private int flightNumber;
    private String flightStatus;
    private int gate;
    private String terminal;
    private String cityPortOfDeparture;
    private String cityPortOfArival;

    public Flights(int idFlight, double arivalTime, double departureTime, int flightNumber, String flightStatus,
                   int gate, String terminal,String cityPortOfDeparture, String cityPortOfArival){
        this.idFlight = idFlight;
        this.arivalTime = arivalTime;
        this.departureTime = departureTime;
        this.flightNumber = flightNumber;
        this.flightStatus = flightStatus;
        this.gate = gate;
        this.terminal = terminal;
        this.cityPortOfDeparture = cityPortOfDeparture;
        this.cityPortOfArival = cityPortOfArival;
    }

    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    public double getArivalTime() {
        return arivalTime;
    }

    public void setArivalTime(double arivalTime) {
        this.arivalTime = arivalTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public int getGate() {
        return gate;
    }

    public void setGate(int gate) {
        this.gate = gate;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getCityPortOfDeparture() {
        return cityPortOfDeparture;
    }

    public void setCityPortOfDeparture(String cityPortOfDeparture) {
        this.cityPortOfDeparture = cityPortOfDeparture;
    }

    public String getCityPortOfArival() {
        return cityPortOfArival;
    }

    public void setCityPortOfArival(String cityPortOfArival) {
        this.cityPortOfArival = cityPortOfArival;
    }
}
