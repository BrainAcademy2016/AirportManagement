package ua.com.airport.entities;

public class FlightStatusEntity {
    private String statusName;

    public FlightStatusEntity() {
    }

    public FlightStatusEntity(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
