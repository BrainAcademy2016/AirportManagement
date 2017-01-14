package adminInterface.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Created by Alish on 07.12.2016.
 */
public class AddFlightController {

    @FXML
    private Button addFlight;

    @FXML
    public void initialize(){

        addFlight.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }
}
