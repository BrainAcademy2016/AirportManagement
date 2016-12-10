package superStaffInterface.superStaffControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import superStaffInterface.Stuffs;

import java.io.IOException;

/**
 * Created by Alish on 07.12.2016.
 */
public class AddStuffController {

    public ObservableList<Stuffs> stuffData = FXCollections.observableArrayList();

    @FXML
    private TableView<Stuffs> tableStuffs;

    @FXML
    private TableColumn<Stuffs, Integer> idRootsColumn;

    @FXML
    private TableColumn <Stuffs, String> loginColumn;

    @FXML
    private TableColumn <Stuffs, String> passwordColumn;

    @FXML
    private TableColumn <Stuffs, String> rootNameColumn;

    @FXML
    private Button addFlight;

    @FXML
    private void initialize(){
        initData();

        idRootsColumn.setCellValueFactory(new PropertyValueFactory<Stuffs, Integer>("idRoots"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<Stuffs, String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Stuffs, String>("password"));
        rootNameColumn.setCellValueFactory(new PropertyValueFactory<Stuffs, String>("rootName"));

        tableStuffs.setItems(stuffData);


    }

    private void initData(){
        stuffData.add(new Stuffs(1,"alex","xela","Stuff"));
        stuffData.add(new Stuffs(2,"alish","hsila","SuperStuff"));
    }

    public void addStaff(Stage primaryStage){
        try {
            Parent addStaff = (Parent) FXMLLoader.load(getClass().getResource("addStuff.fxml"));
            primaryStage.setTitle("Add new staff");
            primaryStage.setScene(new Scene(addStaff, 400, 300));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
