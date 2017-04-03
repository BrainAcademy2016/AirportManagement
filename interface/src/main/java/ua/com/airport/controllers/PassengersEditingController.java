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

public class PassengersEditingController implements Initializable {
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField nationality;
    @FXML private TextField passport;
    @FXML private String birthday;
    @FXML private ComboBox sex;
    @FXML private ComboBox classType;
    @FXML private ComboBox flightNum;
    @FXML private ComboBox dayBox;
    @FXML private ComboBox monthBox;
    @FXML private ComboBox yearBox;

    private String day;
    private String month;
    private String year;

    private Stage dialogStage;
    private PassengersEntity currentPassenger;

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
        sex.setItems(sexList);
        classTypeList.addAll(classTypeDao.getClassType());
        classType.setItems(classTypeList);
        flightNumList.addAll(flightsDao.getAllFightNumbers());
        flightNum.setItems(flightNumList);
        monthBox.setItems(DateUtils.generateMonthList());
        yearBox.setItems(DateUtils.generateYearList());
    }

    public void setDayBoxItems(String month, String year){
        dayBox.setItems(DateUtils.generateDayList(month, year));
    }

    @FXML
    public void handleCancelButton(){
        dialogStage.close();
    }

    @FXML
    public void handleOkEdit(){
        if(isInputValid()) {
            birthday = dayBox.getValue() + "." + monthBox.getValue() + "." + yearBox.getValue();
            currentPassenger.setFirstName(firstName.getText());
            currentPassenger.setLastName(lastName.getText());
            currentPassenger.setNationality(nationality.getText());
            currentPassenger.setPassport(passport.getText());
            currentPassenger.setBirthday(birthday);
            currentPassenger.setSex((String) sex.getValue());
            currentPassenger.setClassType((String) classType.getValue());
            currentPassenger.setFlightNum((String) flightNum.getValue());

            dialogStage.close();

            PassengersDaoImpl passengersDao = new PassengersDaoImpl();
            passengersDao.updatePassenger(currentPassenger);
        }
    }

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("ERROR. Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public PassengersEntity getCurrentPassenger() {
        return currentPassenger;
    }

    public void setCurrentPassenger(PassengersEntity currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    public void setNationality(String nationality) {
        this.nationality.setText(nationality);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPassport(String passport) {
        this.passport.setText(passport);
    }

    public void setSex(String sex) {
        this.sex.setValue(sex);
    }

    public void setClassType(String classType) {
        this.classType.setValue(classType);
    }

    public void setFlightNum(String flightNum) {
        this.flightNum.setValue(flightNum);
    }

    public String getBirthday(){
        return birthday;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public void setDayBox(String day) {
        this.dayBox.setValue(day);
    }

    public void setMonthBox(String month){
        this.monthBox.setValue(month);
    }

    public void setYearBox(String year){
        this.yearBox.setValue(year);
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public void isYearSelected(ActionEvent actionEvent) {
        dayBox.getItems().clear();
        dayBox.setValue(null);
        dayBox.setItems(DateUtils.generateDayList((String ) monthBox.getValue(),(String) yearBox.getValue()));
    }

    public void isMonthSelected(ActionEvent actionEvent) {
        dayBox.getItems().clear();
        dayBox.setValue(null);
        dayBox.setItems(DateUtils.generateDayList((String ) monthBox.getValue(),(String) yearBox.getValue()));
    }
}
