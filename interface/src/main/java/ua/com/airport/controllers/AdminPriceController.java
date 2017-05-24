package ua.com.airport.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.PriceDaoImpl;
import ua.com.airport.entities.FlightsEntity;
import ua.com.airport.entities.PriceEntity;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPriceController extends PriceController {
    private Stage dialogStage;
    private String selectedFlight;

    private final int ROWS_PER_PAGE = 15;
    private int currentPage = 1;

    @FXML
    private TableView priceTable;
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
}
