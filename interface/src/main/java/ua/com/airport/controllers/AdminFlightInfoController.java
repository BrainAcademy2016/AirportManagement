package ua.com.airport.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ua.com.airport.controllers.FlightAddController;
import ua.com.airport.controllers.FlightDeleteController;
import ua.com.airport.controllers.FlightEditController;
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.dbUtils.GuiFilter;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.RootsEntity;
import ua.com.airport.MainApp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;


public class AdminFlightInfoController extends UserSceneController implements Initializable{
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
    @FXML private TableView<FlightsEntity> flightsTable;
    @FXML private TableColumn<ObservableList<FlightsEntity>, String> numberColumn;
    @FXML private TableColumn<FlightsEntity, String> flightColumn;
    @FXML private TableColumn<FlightsEntity, String> depCityColumn;
    @FXML private TableColumn<FlightsEntity, String> depDateColumn;
    // @FXML private TableColumn<FlightsEntity, String> depTimeColumn;
    @FXML private TableColumn<FlightsEntity, String> arrCityColumn;
    @FXML private TableColumn<FlightsEntity, String> arrDateColumn;
    // @FXML private TableColumn<FlightsEntity, String> arrTimeColumn;
    @FXML private TableColumn<FlightsEntity, String> pricesColumn;
    @FXML private TableColumn<FlightsEntity, String> flightStatusColumn;
    @FXML private TableColumn<FlightsEntity, String> gateColumn;
    @FXML private TableColumn<FlightsEntity, String> terminalColumn;
    //@FXML private Pagination flightsPagination;
    @FXML private ToggleButton leftToggleButton;
    @FXML private SplitPane centerSplitPane;
    @FXML private SplitPane mainSplitPane;
    @FXML private Button resetButton;
    @FXML private VBox workIndicator;

//    private final int ROWS_PER_PAGE = 15;
//    private int currentPage = 1;

//    private ObservableList<FlightsEntity> flightsData = FXCollections.observableArrayList();
//    private List<GuiFilter> filtersList = new ArrayList<>();

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

    //Price Button onAction method
//    @Override
//    public void handlePrice(ActionEvent actionEvent) {
//        try {
//            FlightsEntity selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
//            if(selectedFlight != null) {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(MainApp.class.getResource("/view/PriceLayout.fxml"));
//                AnchorPane page = (AnchorPane) loader.load();
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("Class type prices");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(mainApp.getMainAppWindow());
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//                PriceController priceController = loader.getController();
//                priceController.setDialogStage(dialogStage);
//                priceController.showPriceInfo(selectedFlight.getFlightNumber());
//
//                dialogStage.showAndWait();
//            } else {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(MainApp.class.getResource("/view/ErrorLayout.fxml"));
//                AnchorPane page = (AnchorPane) loader.load();
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("ERROR");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(mainApp.getMainAppWindow());
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//                ErrorController errorController = loader.getController();
//                errorController.setDialogStage(dialogStage);
//                errorController.setErrorLabel("Please choose flight for price view!");
//
//                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
//                    @Override
//                    public void handle(KeyEvent event) {
//                        if (keyCombOk.match(event)) {
//                            errorController.handleOkError();
//                        }
//                    }
//                });
//
//                dialogStage.showAndWait();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void initTableView(){
        numberColumn.setCellValueFactory(cellData -> {
            int index = cellData.getTableView().getItems().indexOf(cellData.getValue());
            return new SimpleStringProperty(String.valueOf((index+1)+(currentPage-1)*ROWS_PER_PAGE));
        });
        numberColumn.setSortable(false);

        numberColumn.setPrefWidth(45);

        flightColumn.setCellValueFactory(
                cellData -> cellData.getValue().flightNumberProperty());
        depCityColumn.setCellValueFactory(
                cellData -> cellData.getValue().cityOfDepartureProperty());
        depDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().departureTimeProperty());
        arrCityColumn.setCellValueFactory(
                cellData -> cellData.getValue().cityOfArrivalProperty());
        arrDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().arrivalTimeProperty());
        flightStatusColumn.setCellValueFactory(
                cellData -> cellData.getValue().flightStatusProperty());
        gateColumn.setCellValueFactory(
                cellData -> cellData.getValue().gateProperty());
        terminalColumn.setCellValueFactory(
                cellData -> cellData.getValue().terminalProperty());

        pricesColumn.setCellValueFactory( new PropertyValueFactory<>( "DUMMY" ) );

        Callback<TableColumn<FlightsEntity, String>, TableCell<FlightsEntity, String>> cellFactory =
                new Callback<TableColumn<FlightsEntity, String>, TableCell<FlightsEntity, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<FlightsEntity, String> param )
                    {
                        final TableCell<FlightsEntity, String> cell = new TableCell<FlightsEntity, String>()
                        {
                            Button btn = new Button( "Show all" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setMinHeight(5);
                                    btn.setOnAction( ( ActionEvent event ) ->
                                    {
                                        FlightsEntity markedFlight = getTableView().getItems().get(getIndex());
                                        try {
                                            FXMLLoader loader = new FXMLLoader();
                                            loader.setLocation(MainApp.class.getResource("/view/AdminPriceLayout.fxml"));
                                            AnchorPane page = (AnchorPane) loader.load();
                                            Stage dialogStage = new Stage();
                                            dialogStage.setTitle("Class type prices");
                                            dialogStage.initModality(Modality.WINDOW_MODAL);
                                            dialogStage.initOwner(getMainApp().getMainAppWindow());
                                            Scene scene = new Scene(page);
                                            dialogStage.setScene(scene);
                                            PriceController priceController = loader.getController();
                                            priceController.setDialogStage(dialogStage);
                                            priceController.showPriceInfo(markedFlight.getFlightNumber());
                                            dialogStage.showAndWait();
                                        } catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        pricesColumn.setCellFactory( cellFactory );
        pricesColumn.setStyle( "-fx-alignment: CENTER;");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filtersList.add(new GuiFilter(cityFrom, "Flights", "DepartureCity", true));
        filtersList.add(new GuiFilter(cityTo, "Flights", "ArrivalCity", true));
        filtersList.add(new GuiFilter(datePickerFrom, "Flights", "DepartureTime"));

        setFiltersPaneAnimation();
        setFiltersItems();
        initTableView();
        showFlightsInfo();
    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public MainApp getMainApp() {
        return this.mainApp;
    }

    public static int getGateNumber() {
        return GATE_NUMBER;
    }

    public String[] getTERMINAL() {
        return TERMINAL;
    }
}
