package ua.com.airport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.ClassTypeDaoImpl;
import ua.com.airport.daoimpl.FlightStatusDaoImpl;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.RootsEntity;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class FlightAddController implements Initializable {

    @FXML private DatePicker departureDate;
    @FXML private DatePicker arrivalDate;
    @FXML private TextField flightTextField;
    @FXML private TextField departureCityTextField;
    @FXML private TextField arrivalCityTextField;
    @FXML private TextField terminalTextField;
    @FXML private TextField gateTextField;
    @FXML private ComboBox status;

    private Stage dialogStage;
    private boolean okClicked = false;

    private ObservableList<String> classStatusList = FXCollections.observableArrayList();
    private FlightStatusDaoImpl flightStatusDao = new FlightStatusDaoImpl();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        classStatusList.addAll(flightStatusDao.getFlightStatus());
        if (!classStatusList.isEmpty()){
            status.setItems(classStatusList);
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    public void handleOk() {
        if (isInputValid()){
            String flightPrefix = departureCityTextField.getText().toUpperCase().substring(0,1)+arrivalCityTextField.getText().toUpperCase().substring(0,1);
            FlightsEntity currentFlight = new FlightsEntity(
                    arrivalDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    departureDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    (flightPrefix+flightTextField.getText()), status.getValue().toString(), gateTextField.getText(), terminalTextField.getText(),
                    departureCityTextField.getText(), arrivalCityTextField.getText());
            okClicked = true;
            FlightsDaoImpl flightsDao = new FlightsDaoImpl();
            flightsDao.createFlight(currentFlight);
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
}

