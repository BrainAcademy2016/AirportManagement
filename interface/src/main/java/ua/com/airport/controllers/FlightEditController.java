package ua.com.airport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.ClassTypeDaoImpl;
import ua.com.airport.daoimpl.FlightStatusDaoImpl;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.entities.FlightsEntity;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class FlightEditController implements Initializable {
    @FXML private DatePicker departureDate;
    @FXML private DatePicker arrivalDate;
    @FXML private TextField flightTextField;
    @FXML private TextField departureCityTextField;
    @FXML private TextField arrivalCityTextField;
    @FXML private TextField terminalTextField;
    @FXML private TextField gateTextField;
    @FXML private ComboBox status;

    private Stage dialogStage;
    private FlightsEntity currentFlight;
    private boolean okClicked = false;

    private ObservableList<String> statusList = FXCollections.observableArrayList();
    private FlightStatusDaoImpl flightStatusDao = new FlightStatusDaoImpl();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        statusList.addAll(flightStatusDao.getFlightStatus());
        status.setItems(statusList);
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    public void handleOkEdit() {
        if (isInputValid()){
            currentFlight.setFlightNumber(flightTextField.getText());
            currentFlight.setCityOfDeparture(departureCityTextField.getText());
            currentFlight.setDepartureTime(departureDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            currentFlight.setCityOfArrival(arrivalCityTextField.getText());
            currentFlight.setArrivalTime(arrivalDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            currentFlight.setFlightStatus(status.getValue().toString());
            currentFlight.setTerminal(terminalTextField.getText());
            currentFlight.setGate(gateTextField.getText());
            okClicked = true;
            FlightsDaoImpl flightsDao = new FlightsDaoImpl();
            flightsDao.updateFlight(currentFlight);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (flightTextField.getText() == null || flightTextField.getText().length() == 0) {
            errorMessage += "No valid flight!\n";
        }
        if (departureCityTextField.getText() == null || departureCityTextField.getText().length() == 0) {
            errorMessage += "No valid departure city!\n";
        }
        if (arrivalCityTextField.getText() == null || arrivalCityTextField.getText().length() == 0) {
            errorMessage += "No valid arrival city!\n";
        }
        if (terminalTextField.getText() == null || terminalTextField.getText().length() == 0) {
            errorMessage += "No valid terminal!\n";
        }
        if (gateTextField.getText() == null || gateTextField.getText().length() == 0) {
            errorMessage += "No valid gate!\n";
        }
        if (status.getValue() == null) {
            errorMessage += "No valid status!\n";
        }
        if (arrivalDate.getValue() == null) {
            errorMessage += "No valid arrival date!\n";
        }
        if (departureDate.getValue() == null) {
            errorMessage += "No valid departure date!\n";
        }
        if (departureDate.getValue() != null && arrivalDate.getValue() != null){
            if (departureDate.getValue().compareTo(arrivalDate.getValue()) == 1){
                errorMessage += "Departure date can't be after arrival date!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public FlightsEntity getCurrentFlight() {
        return currentFlight;
    }

    public void setCurrentFlight(FlightsEntity currentFlight) {
        this.currentFlight = currentFlight;
        flightTextField.setText(this.currentFlight.getFlightNumber());
        departureCityTextField.setText(this.currentFlight.getCityOfDeparture());
        arrivalCityTextField.setText(this.currentFlight.getCityOfArrival());
        terminalTextField.setText(this.currentFlight.getTerminal());
        gateTextField.setText(this.currentFlight.getGate());
        status.getSelectionModel().select(this.currentFlight.getFlightStatus());
        departureDate.setValue(LocalDate.parse(this.currentFlight.getDepartureTime(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        arrivalDate.setValue(LocalDate.parse(this.currentFlight.getArrivalTime(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

}

