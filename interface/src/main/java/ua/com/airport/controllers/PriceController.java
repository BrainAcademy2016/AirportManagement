package ua.com.airport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ua.com.airport.daoimpl.PriceDaoImpl;
import ua.com.airport.entities.PriceEntity;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PriceController{
    private Stage dialogStage;

    @FXML private TableView priceTable;

    private ObservableList<PriceEntity> priceData = FXCollections.observableArrayList();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
}
