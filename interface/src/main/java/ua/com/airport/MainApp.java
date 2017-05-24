package ua.com.airport;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ua.com.airport.entities.RootsEntity;
import ua.com.airport.controllers.Controller;
import ua.com.airport.controllers.LoginDialogController;
import java.io.IOException;


public class MainApp extends Application {

    // Parameters of main app Window
    private final String APP_NAME = "Air-flight management system";
    private final String APP_ICON = "images/brain_logo_32.png";
    private final int WINDOW_HEIGHT = 700;
    private final int WINDOW_WIDTH = 1024;

    // App users Scenes
    private final String DEFAULT_USER = "/view/UserScene.fxml";
    private final String STAFF_USER = "/view/ManagerScene.fxml";
    private final String ADMIN_USER = "/view/AdminScene.fxml";

    // App fields
    private Stage mainAppWindow;
    private RootsEntity appUser;
    private Parent mainRoot;

    // Keys combinations for Login Stage
    private KeyCombination keyCombLoginShow = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
    private KeyCombination keyCombLoginClose = new KeyCodeCombination(KeyCode.ESCAPE);
    private KeyCombination keyCombLoginSend = new KeyCodeCombination(KeyCode.ENTER);

    public MainApp() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.mainAppWindow = primaryStage;
        this.mainAppWindow.getIcons().add(new Image(APP_ICON));
        setDefaultAppUser();
        showSplash(5);

    }

    private void showSplash(int secondsToShow) throws Exception{
        try {
            Stage splashStage = new Stage();
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/SplashLayout.fxml"));

            FadeTransition startFade = new FadeTransition(Duration.millis(2000), root);
            startFade.setFromValue(0.0);
            startFade.setToValue(1.0);
            startFade.play();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            splashStage.getIcons().add(new Image(APP_ICON));
            splashStage.setScene(scene);
            splashStage.initStyle(StageStyle.TRANSPARENT);
            splashStage.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(secondsToShow));

            delay.setOnFinished(event -> {
                FadeTransition endFade = new FadeTransition(Duration.millis(2000), root);
                endFade.setFromValue(1.0);
                endFade.setToValue(0.0);
                endFade.play();
                endFade.setOnFinished(event2 ->{
                    splashStage.close();
                    loadAppUserScene();
                });
            });

            delay.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAppUserScene() {
        String layout;
        switch (appUser.getRootName()){
            case "User":
                layout = DEFAULT_USER;
                break;
            case "Manager":
                layout = STAFF_USER;
                break;
            case "Admin":
                layout = ADMIN_USER;
                break;
            default:
                layout = DEFAULT_USER;
                break;
        }
        initScene(layout, APP_NAME + " ("+appUser.getLogin()+")");

    }

    public void initScene(String sceneTemplateUrl, String windowTitle){
        try {
            Scene scene;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(sceneTemplateUrl));
            mainRoot = loader.load();
            Controller controller = loader.getController();
            if (controller != null){
                controller.setMainApp(this);
            }
            if (mainAppWindow.getScene() != null) {
                scene = new Scene(mainRoot, mainAppWindow.getScene().getWidth(), mainAppWindow.getScene().getHeight());
            } else {
                scene = new Scene(mainRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            }
            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (keyCombLoginShow.match(event)) {
                        boolean okClicked = showLoginDialog();
                        if (okClicked) {
                            loadAppUserScene();
                        }
                    }
                }
            });
            // mainWindow.setResizable(false);
            mainAppWindow.setScene(scene);
            mainAppWindow.setTitle(windowTitle);
            mainAppWindow.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public boolean showLoginDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/LoginDialog.fxml"));
            GridPane page = (GridPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Authorisation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainAppWindow);
            Scene scene = new Scene(page, 350, 250);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);

            LoginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if (keyCombLoginClose.match(event)) {
                        dialogStage.close();
                    } else if (keyCombLoginSend.match(event)) {
                        controller.handleSubmitButtonAction();
                    }
                }
            });

            dialogStage.showAndWait();

            if (controller.getUser() != null){
                this.appUser = controller.getUser();
            }

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void setDefaultAppUser(){
        appUser = new RootsEntity(1,"User","user","");
    }

    public Stage getMainAppWindow(){
        return mainAppWindow;
    }
    public  void setMainAppWindow(Stage mainAppWindow){
        this.mainAppWindow = mainAppWindow;
    }

    public  Parent getMainRoot(){
        return mainRoot;
    }
    public  void setMainRoot(Parent mainRoot){
        this.mainRoot = mainRoot;
    }

    public RootsEntity getAppUser(){return appUser;}
    public void setUser(RootsEntity appUser){
        this.appUser = appUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
