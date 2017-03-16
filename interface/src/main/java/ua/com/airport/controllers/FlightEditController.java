package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.entities.FlightsEntity;

/**
 * Created by Alish on 27.01.2017.
 */
public class FlightEditController {
    @FXML private TextField flight;
    @FXML private TextField departureCity;
    @FXML private DatePicker departureDate;
    @FXML private TextField arrivalCity;
    @FXML private DatePicker arrivalDate;
    @FXML private ComboBox classFlight;
    @FXML private TextField price;
    @FXML private ComboBox status;

    private Stage dialogStage;
    private FlightsEntity currentFlight;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
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
            currentFlight.setFlightNumber(flight.getText());
            currentFlight.setCityOfDeparture(departureCity.getText());
            currentFlight.setCityOfArrival(arrivalCity.getText());
            currentFlight.setClassType((String) classFlight.getValue());
      //      currentFlight.setClassPrice(price.getText());
            currentFlight.setFlightStatus((String) status.getValue());

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

        if (flight.getText() == null || flight.getText().length() == 0) {
            errorMessage += "No valid flight!\n";
        }
      //  if (departureCity.getText() == null || departureCity.getText().length() == 0) {
        //    errorMessage += "No valid ciry!\n";
       // }
        if (arrivalCity.getText() == null || arrivalCity.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }
        if (classFlight.getValue() == null) {
            errorMessage += "No valid city!\n";
        }
        if (price.getText() == null || price.getText().length() == 0) {
            errorMessage += "No valid price!\n";
        }
        if (status.getValue() == null) {
            errorMessage += "No valid status!\n";
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

    public void setCurrentFlight(FlightsEntity currentPassenger) {
        this.currentFlight = currentFlight;
    }

    public void setFlight(String flight) {
        this.flight.setText(flight);
    }
    public void setDepartureCity(String departureCity) {
        this.departureCity.setText(departureCity);
    }
    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity.setText(arrivalCity);
    }
    public void setClassFlight(String classFlight) {
        this.classFlight.setValue(classFlight);
    }
    public void setPrice(String price) {
        this.price.setText(price);
    }
    public void setStatus(String status) {
        this.status.setValue(status);
    }
}

