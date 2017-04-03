package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.RootsEntity;

public class EmployeeAddController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private MenuButton rootName;


    private Stage dialogStage;
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
    public void handleOk() {
        if (isInputValid()) {
            RootsEntity currentEmployee = new RootsEntity (rootName.getText(), login.getText(), password.getText());

            okClicked = true;
            RootsDaoImpl rootsDao = new RootsDaoImpl();
            rootsDao.createRoot(currentEmployee);
            rootsDao.updateRoot(currentEmployee);
            dialogStage.close();
        }
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
        if (rootName.getText() == null || rootName.getText().length() == 0) {
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
