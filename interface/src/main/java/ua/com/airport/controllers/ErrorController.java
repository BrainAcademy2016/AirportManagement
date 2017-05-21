package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorController {
    @FXML
    private Label errorLabel;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleOkError() {
        dialogStage.close();
    }

    public void setErrorLabel(String errorMessege){
        errorLabel.setText(errorMessege);
    }
}
