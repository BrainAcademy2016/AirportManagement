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
import java.util.ResourceBundle;


public class FlightAddController implements Initializable {
    @FXML private ComboBox flight;
    @FXML private ComboBox departureCity;
    @FXML private DatePicker departureDate;
    @FXML private ComboBox arrivalCity;
    @FXML private DatePicker arrivalDate;
    @FXML private ComboBox classFlight;
    @FXML private TextField price;
    @FXML private ComboBox status;
    @FXML private ComboBox terminal;
    @FXML private ComboBox gate;

    private Stage dialogStage;
    private boolean okClicked = false;

    private FlightsDaoImpl flightsDao = new FlightsDaoImpl();

    private ObservableList<String> classTypeList = FXCollections.observableArrayList();
    private ObservableList<String> classStatusList = FXCollections.observableArrayList();
    private ClassTypeDaoImpl classTypeDao = new ClassTypeDaoImpl();
    private FlightStatusDaoImpl flightStatusDao = new FlightStatusDaoImpl();
    private static ObservableList<String> gateList = FXCollections.observableArrayList();
    private ObservableList<String> terminalList = FXCollections.observableArrayList();
    private ObservableList<String> flightNumList = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        classTypeList.addAll(classTypeDao.getClassType());
        classStatusList.addAll(flightStatusDao.getFlightStatus());
        classFlight.setItems(classTypeList);
        status.setItems(classStatusList);
        fillGateList(AdminFlightInfoController.getGateNumber());
        gate.setItems(gateList);
        terminalList.add("T1");
        terminalList.add("T2");
        terminal.setItems(terminalList);
        flightNumList.addAll(flightsDao.getAllFightNumbers());
        flight.setItems(flightNumList);
    }

    public static void fillGateList(int number) {
        for(int i = 1; i <= number; i++) {
            gateList.add(String.valueOf(i));
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
//            FlightsEntity currentFlight = new FlightsEntity(flight.getValue(), departureCity.getValue(),
//                    arrivalCity.getValue(),(String) classFlight.getValue(),price.getText(),(String) status.getValue());
//
//            okClicked = true;
//            FlightsDaoImpl flightsDao = new FlightsDaoImpl();
//            flightsDao.createFlight(currentFlight);
//
//            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (flight.getValue() == null || ((String) flight.getValue()).length() == 0) {
            errorMessage += "No valid flight!\n";
        }
        if (departureCity.getValue() == null || ((String)departureCity.getValue()).length() == 0) {
            errorMessage += "No valid ciry!\n";
        }
        if (arrivalCity.getValue() == null || ((String)arrivalCity.getValue()).length() == 0) {
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

