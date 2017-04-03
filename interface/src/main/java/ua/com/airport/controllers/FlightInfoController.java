package ua.com.airport.controllers;

import ua.com.airport.dbUtils.GuiFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ua.com.airport.daoimpl.FiltersDaoImpl;
import ua.com.airport.daoimpl.FlightsDaoImpl;
import ua.com.airport.entities.FlightsEntity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlightInfoController extends UserSceneController implements Initializable {

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

    private final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;

    private ObservableList<FlightsEntity> flightsData = FXCollections.observableArrayList();
    private List<GuiFilter> filtersList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filtersList.add(new GuiFilter(cityFrom, "Flights", "DepartureCity", true));
        filtersList.add(new GuiFilter(cityTo, "Flights", "ArrivalCity", true));
        filtersList.add(new GuiFilter(datePickerFrom, "Flights", "DepartureTime"));
        filtersList.add(new GuiFilter(seatsBox, "PriceList", "ClassType", true));

//        setFiltersPaneAnimation();
        setFiltersItems();
//        initTableView();
        showFlightsInfo();
    }


//    private void setFiltersPaneAnimation(){
//
//        SplitPaneDividerSlider leftSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 0, SplitPaneDividerSlider.Direction.LEFT, leftFilters);
//
//        leftToggleButton.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
//            leftSplitPaneDividerSlider.setAimContentVisible(t1);
//        });
//
//        leftToggleButton.setText("< Hide Filters");
//        leftToggleButton.setCursor(Cursor.HAND);
//
//        leftToggleButton.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
//            if (t1) {
//                leftToggleButton.setText("< Hide Filters");
//            } else {
//                leftToggleButton.setText("> Show Filters");
//            }
//        });
//    }

//    private void initTableView(){
//
//        numberColumn.setCellValueFactory(cellData -> {
//            int index = cellData.getTableView().getItems().indexOf(cellData.getValue());
//            return new SimpleStringProperty(String.valueOf((index+1)+(currentPage-1)*ROWS_PER_PAGE));
//        });
//        numberColumn.setSortable(false);
//
//        flightColumn.setCellValueFactory(
//                cellData -> cellData.getValue().flightNumberProperty());
//        depCityColumn.setCellValueFactory(
//                cellData -> cellData.getValue().cityOfDepartureProperty());
//        depDateColumn.setCellValueFactory(
//                cellData -> cellData.getValue().departureTimeProperty());
//        arrCityColumn.setCellValueFactory(
//                cellData -> cellData.getValue().cityOfArrivalProperty());
//        arrDateColumn.setCellValueFactory(
//                cellData -> cellData.getValue().arrivalTimeProperty());
//        flightStatusColumn.setCellValueFactory(
//                cellData -> cellData.getValue().flightStatusProperty());
//        flightStatusColumn.setCellFactory(column -> {
//            return new TableCell<FlightsEntity, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setText(item);
//                    if (item == null || empty) {
//                        setText("");
//                        setStyle("");
//                    }
//                    if (item != null) {
//                        if (item.equals("In process")){
//                            setTextFill(Color.WHITE);
//                            setStyle("-fx-background-color: green; -fx-border-color: grey");
//                        } else {
//                            setTextFill(Color.BLACK);
//                            setStyle("");
//                        }
//
//                    }
//                }
//            };
//        });
//
//        flightClassColumn.setCellValueFactory(
//                cellData -> cellData.getValue().classTypeProperty());
//
//        flightPriceColumn.setCellValueFactory(new PropertyValueFactory<FlightsEntity, Double>("classPrice"));
//        flightPriceColumn.setCellFactory(column -> {
//            return new TableCell<FlightsEntity, Double>() {
//                @Override
//                protected void updateItem(Double item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item == null || empty){
//                        setText("");
//                    }
//                    if(item != null){
//                        DecimalFormat df = new DecimalFormat("0.00");
//                        setText(String.valueOf(df.format(item)));
//                    }
//
//                }
//            };
//        });
//    }

    private void showFlightsInfo(){
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


}
