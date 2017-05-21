package ua.com.airport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.RootsEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeAddController implements Initializable {
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private ComboBox rootName;

    private Stage dialogStage;
    private boolean okClicked = false;

    private ObservableList<String> rootsList = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL location, ResourceBundle resource) {
        rootsList.add("Admin");
        rootsList.add("Manager");
        rootName.setItems(rootsList);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleOk() {
        if (isInputValid()) {
            RootsEntity currentEmployee = new RootsEntity ((String) rootName.getValue(), login.getText(), password.getText());

            dialogStage.close();
            RootsDaoImpl rootsDao = new RootsDaoImpl();
            rootsDao.createRoot(currentEmployee);
            okClicked = true;
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (login.getText() == null || login.getText().length() == 0) {
            errorMessage += "No valid login!\n";
        }
        if (password.getText() == null || password.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }
        if (rootName.getValue() == null) {
            errorMessage += "No valid roots!\n";
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
