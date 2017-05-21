package ua.com.airport.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class EmployeeDeleteController {
    private Stage dialogStage;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleCancelButton(){
        dialogStage.close();
    }

    @FXML
    public void handleOkDelete() {
        okClicked = true;
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
