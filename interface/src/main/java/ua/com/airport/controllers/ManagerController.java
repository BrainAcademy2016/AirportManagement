package ua.com.airport.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.com.airport.MainApp;

public class ManagerController extends Controller implements Initializable {
    private MainApp mainApp;
    private Stage mainStage;
    private BorderPane mainRoot;
    private AnchorPane currentRoot;

    @FXML
    private AnchorPane passengersLayout;
    @FXML
    private PassengerInfoController passengersLayoutController;
    @FXML
    private AnchorPane flightLayout;
    @FXML
    private UserSceneController flightLayoutController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setMainApp(MainApp mainApp){
       this.mainApp = mainApp;
        passengersLayoutController.setMainApp(mainApp);
        flightLayoutController.setMainApp(mainApp);
    }

    @Override
    public MainApp getMainApp(){
        return mainApp;
    }



}
