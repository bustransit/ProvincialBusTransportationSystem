/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.system;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author NelsonDelaTorre
 */
public class Modals implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        
    }
        
    public void showAlert(String msg) {                
        Alert dlg = new Alert(Alert.AlertType.INFORMATION, msg);
        dlg.show();
    }
    
    public void showConfirmation(String msg) {
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION, msg);
        dlg.show();
    }
    
    public void showInputDialog(String msg) {
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION, msg);
        dlg.show();
    }    
}
