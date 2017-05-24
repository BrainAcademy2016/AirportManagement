package ua.com.airport.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ua.com.airport.MainApp;
import ua.com.airport.daoimpl.PriceDaoImpl;
import ua.com.airport.dbUtils.GuiFilter;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ua.com.airport.daoimpl.FiltersDaoImpl;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.PassengersEntity;
import ua.com.airport.entities.PriceEntity;
import ua.com.airport.utils.SplitPaneDividerSlider;
import ua.com.airport.entities.RootsEntity;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserSceneController extends Controller implements Initializable {
    private MainApp mainApp;
    private KeyCombination keyCombOk = new KeyCodeCombination(KeyCode.ENTER);

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
    @FXML private TableColumn<FlightsEntity, String> gateColumn;
    @FXML private TableColumn<FlightsEntity, String> terminalColumn;
    @FXML private TableColumn flightPriceColumn;
    @FXML private TableColumn<FlightsEntity, String> flightStatusColumn;
    @FXML private TableColumn<FlightsEntity, String> pricesColumn;
    //@FXML private Pagination flightsPagination;
    @FXML private ToggleButton leftToggleButton;
    @FXML private SplitPane centerSplitPane;
    @FXML private SplitPane mainSplitPane;
    @FXML private Button resetButton;
    @FXML private VBox workIndicator;

    private final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;

    private ObservableList<FlightsEntity> flightsData = FXCollections.observableArrayList();
    private List<GuiFilter> filtersList = new ArrayList<>();

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


    private void setFiltersPaneAnimation(){
        SplitPaneDividerSlider leftSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 0, SplitPaneDividerSlider.Direction.LEFT, leftFilters);

        leftToggleButton.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
            leftSplitPaneDividerSlider.setAimContentVisible(t1);
        });

        leftToggleButton.setText("< Hide Filters");
        leftToggleButton.setCursor(Cursor.HAND);

        leftToggleButton.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
            if (t1) {
                leftToggleButton.setText("< Hide Filters");
            } else {
                leftToggleButton.setText("> Show Filters");
            }
        });
    }

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
                                            loader.setLocation(MainApp.class.getResource("/view/PriceLayout.fxml"));
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

    protected void showFlightsInfo(){
        flightsData.clear();
        FlightsDaoImpl flightsDao = new FlightsDaoImpl();
        List<FlightsEntity> flightsListDB = flightsDao.getAllFilteredFlights(filtersList);
        if (flightsListDB != null) {
            flightsData.addAll(FXCollections.observableList(flightsListDB));
            flightsTable.setItems(flightsData);
        }
    }

    @FXML
    public void handleResetButton(){
        filtersList.forEach(GuiFilter::resetFilterGui);
        showFlightsInfo();
    }

    @FXML
    public void handleSearchButton(){
        showFlightsInfo();
    }

    private void setFiltersItems(){
        filtersList.forEach(filter->{
            new FiltersDaoImpl().getFilterItems(filter);
            filter.setFilterGui();
        });
    }

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
////                priceController.showPriceInfo((String)selectedFlight.getFlightNumber());
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

    @Override
    public MainApp getMainApp() {
        return this.mainApp;
    }

    @Override
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
}
