package superStaffInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Alish on 02.12.2016.
 */
public class    MainSuperStaff extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("UserSuperStuff.fxml"));
        primaryStage.setTitle("Brain Academy Air-flight management system");
        primaryStage.setScene(new Scene(root, 1280, 980));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
