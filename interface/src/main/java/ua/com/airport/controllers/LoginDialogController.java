package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.LoginDaoImpl;
import ua.com.airport.entities.RootsEntity;

public class LoginDialogController extends Controller{

    private Stage dialogStage;
    private boolean okClicked = false;
    private RootsEntity appUser;

    @FXML private Text actiontarget;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;


    @FXML public void handleSubmitButtonAction() {

        LoginDaoImpl login = new LoginDaoImpl();
        String massage = "";
        if (login.checkAppUser(loginField.getText(), passwordField.getText())) {
            appUser = login.getAppUser();
            massage = "Hello " + appUser.getRootName();
            actiontarget.setFill(Color.GREEN);
            this.okClicked = true;
            dialogStage.close();
        } else {
            massage = "Authorization error!";
            actiontarget.setFill(Color.FIREBRICK);
        }
        actiontarget.setText(massage);
    }

    @FXML public void handleLogoutButtonAction() {
        logoutAction();
        dialogStage.close();
    }

    @FXML protected void handleCancelButtonAction() {
        dialogStage.close();
    }

    public RootsEntity getUser(){
        return appUser;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
