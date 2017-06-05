package ua.com.airport.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.com.airport.MainApp;
import ua.com.airport.daoimpl.PriceDaoImpl;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.PriceEntity;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPriceController extends PriceController {
    private Stage dialogStage;
    private String selectedFlight;
    private MainApp mainApp;
    private String flight;
    private KeyCombination keyCombClose = new KeyCodeCombination(KeyCode.ESCAPE);
    private KeyCombination keyCombOk = new KeyCodeCombination(KeyCode.ENTER);

    private final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;

    @FXML
    private TableView<PriceEntity> priceTable;
    @FXML private TableColumn<PriceEntity, String> idColumn;
    @FXML private TableColumn<PriceEntity, String> classTypeColumn;
    @FXML private TableColumn priceColumn;
    @FXML private TableColumn<PriceEntity, String> flightColumn;

    private ObservableList<PriceEntity> priceData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
    }

    public void showPriceInfo(String flightNum) {
        priceData.clear();
        PriceDaoImpl priceDao = new PriceDaoImpl();
        List<PriceEntity> priceListDB = priceDao.getPricesByFlightNum(flightNum);
        if (priceListDB != null) {
            priceData.addAll(FXCollections.observableList(priceListDB));
            priceTable.setItems(priceData);
        }
    }

    private void initTableView() {
        idColumn.setCellValueFactory(cellData -> {
            int index = cellData.getTableView().getItems().indexOf(cellData.getValue());
            return new SimpleStringProperty(String.valueOf((index+1)+(currentPage-1)*ROWS_PER_PAGE));
        });
        idColumn.setSortable(false);

        classTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().classTypeProperty());
        priceColumn.setCellValueFactory(new PropertyValueFactory<FlightsEntity, Double>("price"));
        priceColumn.setCellFactory(column -> {
            return new TableCell<FlightsEntity, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty){
                        setText("");
                    }
                    if(item != null){
                        DecimalFormat df = new DecimalFormat("0.00");
                        setText(String.valueOf(df.format(item)));
                    }

                }
            };
        });
        flightColumn.setCellValueFactory(
                cellData -> cellData.getValue().flightNumberProperty());
    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Stage getDialogStage(){
        return  dialogStage;
    }

    @FXML
    public void handleAddPrice(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/PriceAdding.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Price Adding");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PriceAddingController priceAddingController = loader.getController();
            priceAddingController.setDialogStage(dialogStage);

            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (keyCombClose.match(event)) {
                        dialogStage.close();
                    } else if (keyCombOk.match(event)) {
                        priceAddingController.handleOkButton();
                    }
                }
            });

            priceAddingController.setFlightField(flight);

            dialogStage.showAndWait();
            showPriceInfo(flight);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    public void handleEditPrice(ActionEvent actionEvent) {
        try{
            PriceEntity selectedPrice = priceTable.getSelectionModel().getSelectedItem();
            if(selectedPrice != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/PriceEditing.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Price Editing");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                PriceEditingController priceEditingController = loader.getController();
                priceEditingController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            priceEditingController.handleOkButton();
                        }
                    }
                });

                priceEditingController.setCurrentPrice(selectedPrice);
                priceEditingController.setClassBox(selectedPrice.getClassType());
                priceEditingController.setPriceField(String.valueOf(selectedPrice.getPrice()));
                priceEditingController.setFlightField(selectedPrice.getFlightNumber());

                dialogStage.showAndWait();
                showPriceInfo(flight);
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
                errorController.setErrorLabel("Please choose price for editing!");

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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeletePrice(ActionEvent actionEvent) {
        try{
            boolean okClicked;
            PriceEntity selectedPrice = priceTable.getSelectionModel().getSelectedItem();
            if(selectedPrice != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/PriceDeleting.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Price Deleting");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                PriceDeletingController priceDeletingController = loader.getController();
                priceDeletingController.setDialogStage(dialogStage);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (keyCombClose.match(event)) {
                            dialogStage.close();
                        } else if (keyCombOk.match(event)) {
                            priceDeletingController.handleOkButton();
                        }
                    }
                });

                dialogStage.showAndWait();
                PriceDaoImpl priceDao = new PriceDaoImpl();
                okClicked = priceDeletingController.isOkClicked();
                if(okClicked){
                    priceDao.deletePrice(selectedPrice.getId());
                }
                showPriceInfo(flight);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/ErrorLayout.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("ERROR");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                ErrorController errorController = loader.getController();
                errorController.setDialogStage(dialogStage);
                errorController.setErrorLabel("Please choose price for deleting!");

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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setMarkedFlight(String markedFlight){
        flight = markedFlight;
    }
}
