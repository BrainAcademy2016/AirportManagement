package ua.com.airport.controllers;

import ua.com.airport.dbUtils.GuiFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import ua.com.airport.daoimpl.RootsDaoImpl;
import ua.com.airport.entities.RootsEntity;
import ua.com.airport.MainApp;
import ua.com.airport.utils.SplitPaneDividerSlider;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeInfoController extends Controller implements Initializable {

    private Stage stage;
    private MainApp mainApp;
    private KeyCombination keyCombClose = new KeyCodeCombination(KeyCode.ESCAPE);
    private KeyCombination keyCombOk = new KeyCodeCombination(KeyCode.ENTER);

    @FXML private TextField rootField;
    @FXML private TextField loginField;

    @FXML private TableView<RootsEntity> employeeTable;
    @FXML private TableColumn<ObservableList<RootsEntity>, String> idRootsColumn;
    @FXML private TableColumn<RootsEntity, String> rootNameColumn;
    @FXML private TableColumn<RootsEntity, String> loginColumn;
    @FXML private TableColumn<RootsEntity, String> passwordColumn;

    @FXML private AnchorPane leftFilters;
    @FXML private ToggleButton leftToggleButton;
    @FXML private SplitPane centerSplitPane;
    @FXML private SplitPane mainSplitPane;

    private final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;

    private ObservableList<RootsEntity> employeeData = FXCollections.observableArrayList();
    private List<GuiFilter> filtersList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filtersList.add(new GuiFilter(rootField, "Roots", "RootName"));
        filtersList.add(new GuiFilter(loginField, "Roots", "Login"));

        setFiltersPaneAnimation();
        setFiltersItems();
        initTableView();
        showEmployeeInfo();
    }

    private void showEmployeeInfo() {
        employeeData.clear();
        RootsDaoImpl rootsDao = new RootsDaoImpl();
        List<RootsEntity> rootsEntityList = rootsDao.getAllFilteredRoots(filtersList);
        if (rootsEntityList != null) {
            employeeData.addAll(FXCollections.observableList(rootsEntityList));
            employeeTable.setItems(employeeData);
        }
    }

    private void initTableView() {
        idRootsColumn.setCellValueFactory(cellData -> {
            int index = cellData.getTableView().getItems().indexOf(cellData.getValue());
            return new SimpleStringProperty(String.valueOf((index+1)+(currentPage-1)*ROWS_PER_PAGE));
        });
        idRootsColumn.setSortable(false);

        rootNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().rootNameProperty());
        loginColumn.setCellValueFactory(
                cellData -> cellData.getValue().loginProperty());
        passwordColumn.setCellValueFactory(
                cellData -> cellData.getValue().passwordProperty());
    }

    public void handleAddEmployee(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AddEmployee.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Employee");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainAppWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            EmployeeAddController emAddController = loader.getController();
            emAddController.setDialogStage(dialogStage);

            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (keyCombClose.match(event)) {
                        dialogStage.close();
                    } else if (keyCombOk.match(event)) {
                        emAddController.handleOk();
                    }
                }
            });

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEditEmployee(ActionEvent actionEvent) {
        try {
            RootsEntity markedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if(markedEmployee != null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/EditEmployee.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Employee");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
                EmployeeEditController emEditController = loader.getController();
                emEditController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            emEditController.handleOkEdit();
                        }
                    }
                });

                emEditController.setCurrentEmployee(markedEmployee);
                emEditController.setLogin(markedEmployee.getLogin());
                emEditController.setPassword(markedEmployee.getPassword());
                emEditController.setRootName(markedEmployee.getRootName());
                dialogStage.showAndWait();
                showEmployeeInfo();
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
                errorController.setErrorLabel("Please choose employee for editing!");

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

    public void handleDeleteEmployee(ActionEvent actionEvent) {
        try {
            RootsEntity markedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (markedEmployee != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/DeleteEmployee.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Delete Employee");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getMainAppWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Передаём адресата в контроллер.
                EmployeeDeleteController emDeleteController = loader.getController();
                emDeleteController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            emDeleteController.handleOkDelete();
                        }
                    }
                });

                dialogStage.showAndWait();
                RootsDaoImpl rootsDao = new RootsDaoImpl();
                boolean okClicked = emDeleteController.isOkClicked();
                if(okClicked){
                    rootsDao.deleteRoot(markedEmployee.getId());
                }
                showEmployeeInfo();
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
                errorController.setErrorLabel("Do you want delete this employee?");

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
        showEmployeeInfo();
    }

    @FXML
    public void handleSearchButton(){
        showEmployeeInfo();
    }

    private void setFiltersItems(){
        filtersList.forEach(filter->{
            new FiltersDaoImpl().getFilterItems(filter);
            filter.setFilterGui();
        });
    }
}
