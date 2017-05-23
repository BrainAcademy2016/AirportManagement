package ua.com.airport.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.com.airport.controllers.FlightAddController;
import ua.com.airport.controllers.FlightDeleteController;
import ua.com.airport.controllers.FlightEditController;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.RootsEntity;
import ua.com.airport.MainApp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Date;
import java.util.Locale;


public class AdminFlightInfoController extends UserSceneController {
    private Stage stage;
    private MainApp mainApp;

    private KeyCombination keyCombClose = new KeyCodeCombination(KeyCode.ESCAPE);
    private KeyCombination keyCombOk = new KeyCodeCombination(KeyCode.ENTER);

    private static int GATE_NUMBER = 20;
    private static String[] TERMINAL = new String[]{"T1", "T2"};

    @FXML private ChoiceBox cityFrom;
    @FXML private ChoiceBox cityTo;
    @FXML private DatePicker datePickerFrom;
    @FXML private AnchorPane leftFilters;
    @FXML private ChoiceBox seatsBox;
    @FXML private TableView<FlightsEntity> flightsTable;
    @FXML private TableColumn<ObservableList<FlightsEntity>, String> numberColumn;
    @FXML private TableColumn<FlightsEntity, String> flightColumn;
    @FXML private TableColumn<FlightsEntity, String> depCityColumn;
    @FXML private TableColumn<FlightsEntity, String> depDateColumn;
    // @FXML private TableColumn<FlightsEntity, String> depTimeColumn;
    @FXML private TableColumn<FlightsEntity, String> arrCityColumn;
    @FXML private TableColumn<FlightsEntity, String> arrDateColumn;
    // @FXML private TableColumn<FlightsEntity, String> arrTimeColumn;
    @FXML private TableColumn<FlightsEntity, String> flightClassColumn;
    @FXML private TableColumn flightPriceColumn;
    @FXML private TableColumn<FlightsEntity, String> flightStatusColumn;
    //@FXML private Pagination flightsPagination;
    @FXML private ToggleButton leftToggleButton;
    @FXML private SplitPane centerSplitPane;
    @FXML private SplitPane mainSplitPane;
    @FXML private Button resetButton;
    @FXML private VBox workIndicator;

    public void handleAddFlight(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AddFlight.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Flight");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainAppWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FlightAddController flAddController = loader.getController();
            flAddController.setDialogStage(dialogStage);

            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (keyCombClose.match(event)) {
                        dialogStage.close();
                    } else if (keyCombOk.match(event)) {
                        flAddController.handleOk();
                    }
                }
            });

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEditFlight(ActionEvent actionEvent) {
        try {
            FlightsEntity markedFlight = flightsTable.getSelectionModel().getSelectedItem();
            if(markedFlight != null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/EditFlight.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Flight");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Передаём адресата в контроллер.
                FlightEditController flEditController = loader.getController();
                flEditController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            flEditController.handleOkEdit();
                        }
                    }
                });
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                formatter = formatter.withLocale(new Locale("en", "UA"));

                flEditController.setCurrentFlight(markedFlight);
                flEditController.setFlight(markedFlight.getFlightNumber());
                flEditController.setDepartureCity(markedFlight.getCityOfDeparture());
                flEditController.setArrivalCity(markedFlight.getCityOfArrival());
//                flEditController.setClassFlight(markedFlight.getClassType());
//                flEditController.setPrice(markedFlight.getClassPrice().toString());
                flEditController.setStatus(markedFlight.getFlightStatus());
                flEditController.setDepartureDate(LocalDate.parse(markedFlight.getDepartureTime(), formatter));
                flEditController.setArrivalDate(LocalDate.parse(markedFlight.getArrivalTime(), formatter));

                dialogStage.showAndWait();
                showFlightsInfo();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/ErrorLayout.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("ERROR");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                ErrorController errorController = loader.getController();
                errorController.setDialogStage(dialogStage);
                errorController.setErrorLabel("Please choose flight for editing!");

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombOk.match(event)) {
                            errorController.handleOkError();
                        }
                    }
                });

                dialogStage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteFlight(ActionEvent actionEvent) {
        try {
            FlightsEntity markedEmployee = flightsTable.getSelectionModel().getSelectedItem();
            if (markedEmployee != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/DeleteFlight.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Delete Flight");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Передаём адресата в контроллер.
                FlightDeleteController flDeleteController = loader.getController();
                flDeleteController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            flDeleteController.handleOkDelete();
                        }
                    }
                });

                dialogStage.showAndWait();
                RootsDaoImpl rootsDao = new RootsDaoImpl();
                boolean okClicked = flDeleteController.isOkClicked();
                if(okClicked){
                    rootsDao.deleteRoot(markedEmployee.getId());
                }
                showFlightsInfo();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/ErrorLayout.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("ERROR");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                ErrorController errorController = loader.getController();
                errorController.setDialogStage(dialogStage);
                errorController.setErrorLabel("Do you want delete this flight?");

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombOk.match(event)) {
                            errorController.handleOkError();
                        }
                    }
                });

                dialogStage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = (mainApp);
    }

    @Override
    public MainApp getMainApp() {
        return mainApp;
    }

    public static int getGateNumber() {
        return GATE_NUMBER;
    }

    public String[] getTERMINAL() {
        return TERMINAL;
    }
}
