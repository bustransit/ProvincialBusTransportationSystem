/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.finance;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private JFXButton btn_menu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(bus_transit.DashboardController.root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }    

    @FXML
    private void OpenMenu(ActionEvent event) {
    }
    
}
