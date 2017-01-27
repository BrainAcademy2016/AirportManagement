package adminInterface.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import adminInterface.Employee;

import javax.imageio.IIOParam;
import java.io.IOException;

import static javafx.application.Application.launch;

/**
 * Created by Alish on 07.12.2016.
 */
public class AddEmployeeController {

    public ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    @FXML
    private TableView<Employee> tableEmployee;

    @FXML
    private TableColumn<Employee, Integer> idRootsColumn;

    @FXML
    private TableColumn <Employee, String> loginColumn;

    @FXML
    private TableColumn <Employee, String> passwordColumn;

    @FXML
    private TableColumn <Employee, String> rootNameColumn;

    @FXML
    private Button addEmployee;

    public void openAddMenu (ActionEvent actionEvent){


        try{
            Stage createStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("addEmployee.fxml"));

            createStage.setTitle("Creating new employee");
            createStage.setScene(new Scene(root, createStage.getWidth(), createStage.getHeight()));
            createStage.setResizable(false);
            createStage.setScene(new Scene(root));
            createStage.initModality(Modality.WINDOW_MODAL);
            createStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            createStage.show();


        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void initialize(){
        initData();

        idRootsColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("idRoots"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
        rootNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("rootName"));

        tableEmployee.setItems(employeeData);

    }

    private void initData(){
        employeeData.add(new Employee(1,"alex","xela","Meneger"));
        employeeData.add(new Employee(2,"alish","hsila","Admin"));
    }



}
