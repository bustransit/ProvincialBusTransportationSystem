/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningModulesController implements Initializable {
    DBUtilities db = new DBUtilities();
    ResultSet rs;    
    
    
    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane heading;
    @FXML
    private JFXTabPane content;
    @FXML
    private Tab training;
    @FXML
    private CustomTextField tfSearch;
    @FXML
    private FlowPane container_all;
    @FXML
    private TableView<?> tblTrainingList;
    @FXML
    private JFXTextField tfTrainingTitle;
    @FXML
    private JFXTextField tfTrainingLocation;
    @FXML
    private JFXDatePicker dpTrainingDate;
    @FXML
    private JFXComboBox<?> cbTrainingType;
    @FXML
    private JFXTextField tfNumberOfParticipants;
    @FXML
    private JFXButton btnNew;
    @FXML
    private JFXTimePicker tpTrainingTime;
    @FXML
    private Tab learningProgress;
    @FXML
    private CustomTextField txt_searchPending;
    @FXML
    private FlowPane container_pending;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db.buildData("SELECT title, participants, TIME, target_date, venue, TYPE FROM training", tblTrainingList);
    }    

    @FXML
    private void searchTraining(KeyEvent event) {
    }        
}
