package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.RootsEntity;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alish on 27.01.2017.
 */
public class FlightAddController implements Initializable {
    @FXML private TextField flight;
    @FXML private TextField departureCity;
    @FXML private DatePicker departureDate;
    @FXML private TextField arrivalCity;
    @FXML private DatePicker arrivalDate;
    @FXML private ComboBox classFlight;
    @FXML private TextField price;
    @FXML private ComboBox status;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

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
            FlightsEntity currentFlight = new FlightsEntity(flight.getText(), departureCity.getText(),
                    arrivalCity.getText(),(String) classFlight.getValue(),price.getText(),(String) status.getValue());

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

        if (flight.getText() == null || flight.getText().length() == 0) {
            errorMessage += "No valid flight!\n";
        }
        if (departureCity.getText() == null || departureCity.getText().length() == 0) {
            errorMessage += "No valid ciry!\n";
        }
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
}

