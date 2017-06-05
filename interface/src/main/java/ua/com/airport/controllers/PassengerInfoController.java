package ua.com.airport.controllers;

import ua.com.airport.dbUtils.GuiFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.FiltersDaoImpl;
import ua.com.airport.daoimpl.PassengersDaoImpl;
import ua.com.airport.entities.PassengersEntity;
import ua.com.airport.MainApp;
import ua.com.airport.utils.SplitPaneDividerSlider;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PassengerInfoController extends Controller implements Initializable{
    private Stage stage;
    private MainApp mainApp;
    private ManagerController managerController;
    private KeyCombination keyCombClose = new KeyCodeCombination(KeyCode.ESCAPE);
    private KeyCombination keyCombOk = new KeyCodeCombination(KeyCode.ENTER);

    @FXML private ChoiceBox flightNumberBox;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField passportField;

    @FXML private TableView<PassengersEntity> passengersTable;
    @FXML private TableColumn<ObservableList<PassengersEntity>, String> numberColumn;
    @FXML private TableColumn<PassengersEntity, String> firstNameColumn;
    @FXML private TableColumn<PassengersEntity, String> lastNameColumn;
    @FXML private TableColumn<PassengersEntity, String> nationalityColumn;
    @FXML private TableColumn<PassengersEntity, String> birthdayColumn;
    @FXML private TableColumn<PassengersEntity, String> passportColumn;
    @FXML private TableColumn<PassengersEntity, String> sexColumn;
    @FXML private TableColumn<PassengersEntity, String> classTypeColumn;
    @FXML private TableColumn<PassengersEntity, String> flightNumColumn;

    @FXML private AnchorPane leftFilters;
    @FXML private ToggleButton leftToggleButton;
    @FXML private SplitPane centerSplitPane;
    @FXML private SplitPane mainSplitPane;


    private final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;

    private ObservableList<PassengersEntity> passengersData = FXCollections.observableArrayList();
    private List<GuiFilter> filtersList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filtersList.add(new GuiFilter(flightNumberBox, "Flights", "FlightNumber", true));
        filtersList.add(new GuiFilter(firstNameField, "PassengersInfo", "FirstName", true));
        filtersList.add(new GuiFilter(lastNameField, "PassengersInfo", "LastName"));
        filtersList.add(new GuiFilter(passportField, "PassengersInfo", "Passport", true));

        setFiltersPaneAnimation();
        setFiltersItems();
        initTableView();
        showPassengersInfo();
    }

    private void showPassengersInfo() {
        passengersData.clear();
        PassengersDaoImpl passengersDao = new PassengersDaoImpl();
        List<PassengersEntity> passengersListDB = passengersDao.getAllFilteredPassengers(filtersList);
        if (passengersListDB != null) {
            passengersData.addAll(FXCollections.observableList(passengersListDB));
            passengersTable.setItems(passengersData);
        }
    }

    private void initTableView() {
        numberColumn.setCellValueFactory(cellData -> {
            int index = cellData.getTableView().getItems().indexOf(cellData.getValue());
            return new SimpleStringProperty(String.valueOf((index+1)+(currentPage-1)*ROWS_PER_PAGE));
        });
        numberColumn.setSortable(false);

        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());
        nationalityColumn.setCellValueFactory(
                cellData -> cellData.getValue().nationalityProperty());
        birthdayColumn.setCellValueFactory(
                cellData -> cellData.getValue().birthdayProperty());
        passportColumn.setCellValueFactory(
                cellData -> cellData.getValue().passportProperty());
        sexColumn.setCellValueFactory(
                cellData -> cellData.getValue().sexProperty());
        classTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().classTypeProperty());
        flightNumColumn.setCellValueFactory(
                cellData -> cellData.getValue().flightNumProperty());
    }

    @FXML
    public void handleAddPassenger() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/PassengerAdding.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Passenger adding");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainAppWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PassengersAddingController pAController = loader.getController();
            pAController.setDialogStage(dialogStage);

            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (keyCombClose.match(event)) {
                        dialogStage.close();
                    } else if (keyCombOk.match(event)) {
                        pAController.handleOkAdd();
                    }
                }
            });

            dialogStage.showAndWait();
            showPassengersInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditPassenger() {
        try {
            PassengersEntity selectedPassenger = passengersTable.getSelectionModel().getSelectedItem();
            if(selectedPassenger != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/PassengerEditing.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Passenger adding");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                PassengersEditingController pEController = loader.getController();
                pEController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            pEController.handleOkEdit();
                        }
                    }
                });

                pEController.setCurrentPassenger(selectedPassenger);
                pEController.setFirstName(selectedPassenger.getFirstName());
                pEController.setLastName(selectedPassenger.getLastName());
                pEController.setNationality(selectedPassenger.getNationality());
                pEController.setPassport(selectedPassenger.getPassport());
                char[] array = selectedPassenger.getBirthday().toCharArray();
                pEController.setDay(array[0] + "" + array[1]);
                pEController.setMonth(array[3] + "" + array[4]);
                pEController.setYear(array[6] + "" + array[7] + "" + array[8] + "" + array[9]);
                pEController.setDayBox(pEController.getDay());
                pEController.setMonthBox(pEController.getMonth());
                pEController.setYearBox(pEController.getYear());
                pEController.setDayBoxItems(pEController.getMonth(), pEController.getYear());
                pEController.setSex(selectedPassenger.getSex());
                pEController.setClassType(selectedPassenger.getClassType());
                pEController.setFlightNum(selectedPassenger.getFlightNum());

                dialogStage.showAndWait();
                showPassengersInfo();
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
                errorController.setErrorLabel("Please choose passenger for editing!");

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

    @FXML
    public void handleDeletePassenger() {
        try {
            boolean okClicked;
            PassengersEntity selectedPassenger = passengersTable.getSelectionModel().getSelectedItem();
            if(selectedPassenger != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/PassengerDeleting.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Delete passenger");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                PassengersDeletingController pDController = loader.getController();
                pDController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            pDController.handleOkDelete();
                        }
                    }
                });

                dialogStage.showAndWait();
                PassengersDaoImpl passengersDao = new PassengersDaoImpl();
                okClicked = pDController.isOkClicked();
                if(okClicked){
                    passengersDao.deletePassenger(selectedPassenger.getId());
                }
                showPassengersInfo();
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
                errorController.setErrorLabel("Please choose passenger for deleting!");

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

    public void setFiltersPaneAnimation(){
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

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = (mainApp);
    }

    @Override
    public MainApp getMainApp() {
        return mainApp;
    }


    @FXML
    public void handleResetButton(){
        filtersList.forEach(GuiFilter::resetFilterGui);
        showPassengersInfo();
    }

    @FXML
    public void handleSearchButton(){
        showPassengersInfo();
    }

    private void setFiltersItems(){
        filtersList.forEach(filter->{
            if (filter.isListTypeFromDB()){
                new FiltersDaoImpl().getFilterItems(filter);
                filter.setFilterGui();
            }
        });
    }

    public TableView<PassengersEntity> getPassengersTable() {
        return passengersTable;
    }

}
