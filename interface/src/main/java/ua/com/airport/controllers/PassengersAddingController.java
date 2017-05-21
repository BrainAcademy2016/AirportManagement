package ua.com.airport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.ClassTypeDaoImpl;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.daoimpl.PassengersDaoImpl;
import ua.com.airport.entities.PassengersEntity;
import ua.com.airport.utils.DateUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class PassengersAddingController implements Initializable{
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField nationality;
    @FXML private String birthday;
    @FXML private TextField passport;
    @FXML private ComboBox sex;
    @FXML private ComboBox classType;
    @FXML private ComboBox flightNum;
    @FXML private ComboBox dayBox;
    @FXML private ComboBox monthBox;
    @FXML private ComboBox yearBox;

    private Stage dialogStage;
    private boolean okClicked = false;

    private ObservableList<String> sexList = FXCollections.observableArrayList();
    private ObservableList<String> classTypeList = FXCollections.observableArrayList();
    private ObservableList<String> flightNumList = FXCollections.observableArrayList();

    private ClassTypeDaoImpl classTypeDao = new ClassTypeDaoImpl();
    private FlightsDaoImpl flightsDao = new FlightsDaoImpl();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sexList.add("Woman");
        sexList.add("Man");
        classTypeList.addAll(classTypeDao.getClassType());
        flightNumList.addAll(flightsDao.getAllFightNumbers());
        sex.setItems(sexList);
        classType.setItems(classTypeList);
        flightNum.setItems(flightNumList);
        monthBox.setItems(DateUtils.generateMonthList());
        yearBox.setItems(DateUtils.generateYearList());
        monthBox.setVisible(false);
        dayBox.setVisible(false);

    }

    @FXML
    public void handleCancelButton() {
        dialogStage.close();
    }

    /**
     * * Вызывается, когда пользователь кликнул по кнопке OK.
     * */
    @FXML
    public void handleOkAdd() {
        if(isInputValid()) {
            birthday = dayBox.getValue() + "." + monthBox.getValue() + "." + yearBox.getValue();
            PassengersEntity currentPassenger = new PassengersEntity(firstName.getText(), lastName.getText(), nationality.getText(), birthday,
                    passport.getText(), (String) sex.getValue(), (String) classType.getValue(), (String) flightNum.getValue());
            dialogStage.close();
            PassengersDaoImpl passengersDao = new PassengersDaoImpl();
            passengersDao.addPassenger(currentPassenger);
            okClicked = true;
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstName.getText() == null || firstName.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastName.getText() == null || lastName.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (nationality.getText() == null || nationality.getText().length() == 0) {
            errorMessage += "No valid nationality!\n";
        }
        if (passport.getText() == null || passport.getText().length() != 8) {
            errorMessage += "No valid passport!\n";
        }
        if (yearBox.getValue() == null) {
            errorMessage += "No valid year!\n";
        }
        if (monthBox.getValue() == null) {
            errorMessage += "No valid month!\n";
        }
        if (dayBox.getValue() == null) {
            errorMessage += "No valid day!\n";
        }
        if (sex.getValue() == null) {
            errorMessage += "No valid sex!\n";
        }
        if (classType.getValue() == null) {
            errorMessage += "No valid class type!\n";
        }
        if (flightNum.getValue() == null) {
            errorMessage += "No valid flight number!\n";
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


    public void isYearSelected(ActionEvent actionEvent) {
        if(yearBox.getValue() != null && monthBox.getValue() == null){
            monthBox.setVisible(true);
        }
        if(yearBox.getValue() != null && monthBox.getValue() != null){
            dayBox.getItems().clear();
            dayBox.setValue(null);
            dayBox.setItems(DateUtils.generateDayList((String ) monthBox.getValue(),(String) yearBox.getValue()));
        }
    }

    public void isMonthSelected(ActionEvent actionEvent) {
        if(monthBox.getValue() != null ){
            dayBox.getItems().clear();
            dayBox.setValue(null);
            dayBox.setItems(DateUtils.generateDayList((String ) monthBox.getValue(),(String) yearBox.getValue()));
            dayBox.setVisible(true);
        }
    }
}
