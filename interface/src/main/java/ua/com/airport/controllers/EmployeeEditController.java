package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.RootsEntity;


public class EmployeeEditController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private MenuButton rootName;


    private Stage dialogStage;
    private RootsEntity currentEmployee;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    public void handleOkEdit() {
        currentEmployee.setRootName(rootName.getText());
        currentEmployee.setLogin(login.getText());
        currentEmployee.setPassword(password.getText());

        RootsDaoImpl rootsDao = new RootsDaoImpl();
        rootsDao.updateRoot(currentEmployee);
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

    public RootsEntity getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(RootsEntity currentPassenger) {
        this.currentEmployee = currentEmployee;
    }

    public void setLogin(String login) {
        this.login.setText(login);
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public void setRootName(String rootName) {
        this.rootName.setText(rootName);
    }
}
