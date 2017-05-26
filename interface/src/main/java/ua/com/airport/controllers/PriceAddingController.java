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
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.PriceEntity;
import ua.com.airport.daoimpl.PriceDaoImpl;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import static javafx.application.ConditionalFeature.FXML;

public class PriceAddingController implements Initializable{
    private Stage dialogStage;
    private boolean okClicked = false;

    private ObservableList<String> classList = FXCollections.observableArrayList();
    private ClassTypeDaoImpl classTypeDao = new ClassTypeDaoImpl();

    @FXML private ComboBox<String> classBox;
    @FXML private TextField priceField;
    @FXML private TextField flightField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classList.addAll(classTypeDao.getClassType());
        classBox.setItems(classList);
    }

    @FXML
    public void handleOkButton() {
        if(isInputValid()){
            PriceEntity newPriceEntity = new PriceEntity(classBox.getValue(), Double.parseDouble(priceField.getText()), flightField.getText());
            PriceDaoImpl priceDao = new PriceDaoImpl();
            priceDao.createPrice(newPriceEntity);
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancelButton() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (classBox.getValue() == null) {
            errorMessage += "No valid class!\n";
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No valid price!\n";
        } if (errorMessage.length() == 0) {
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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    public void setFlightField(String flight) {
        this.flightField.setText(flight);
    }
}
