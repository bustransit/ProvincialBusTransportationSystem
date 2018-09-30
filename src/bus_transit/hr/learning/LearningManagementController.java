/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import bus_transit.DashboardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningManagementController extends Application implements Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;    
    
    @FXML private AnchorPane container;
    @FXML private AnchorPane heading;
    @FXML private JFXTabPane content;
    @FXML private Tab modules;
    @FXML private CustomTextField txt_searchAll;
    @FXML private FlowPane container_all;
    @FXML public static Label title;
    @FXML private JFXButton btnEdit;
    @FXML private JFXButton btnView;
    @FXML private Text description;
    @FXML private Label date1;
    @FXML private Label date;
    @FXML private Label title1;
    @FXML private JFXButton btnEdit1;
    @FXML private JFXButton btnView1;
    @FXML private Text description1;
    @FXML private Label date11;
    @FXML private Tab learningProgress;
    @FXML private CustomTextField txt_searchPending;
    @FXML private FlowPane container_pending;
    @FXML private JFXButton btnNew;
    @FXML private FlowPane modulePane;
    @FXML private FlowPane modulePane1;
    @FXML private JFXButton btnView2;
    @FXML private Tab test;
    @FXML private CustomTextField txt_searchPending1;
    @FXML private FlowPane flpTestContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTestContainer();
    }    

    @FXML
    private void SearchAllRequest(KeyEvent event) {
    }


    @FXML
    private void SaveRequest(ActionEvent event) {
    }

    @FXML
    private void CancelRequest(ActionEvent event) {
    }

    @FXML
    private void SearchPendingRequest(KeyEvent event) {
    }
    
    private void loadFunction(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();
        System.out.println(file);
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(file));
            pane.setPrefSize(DashboardController.root.getWidth(), DashboardController.root.getHeight());
            DashboardController.root.getChildren().removeAll(DashboardController.root.getChildren());
            DashboardController.root.getChildren().add(pane);
            DashboardController.draw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }     

    @FXML
    private void toHoverState(MouseEvent event) {
    }
    
    
    @FXML
    private void populateTestContainer(){
        String q = "SELECT * FROM test";
        rs = db.displayRecords(q);               
        ArrayList<FXMLLoader> fxmlLoaders = new ArrayList();
        ArrayList<FlowPane> arrFp = new ArrayList();
        ArrayList<TestController> testControllers = new ArrayList();
        try {
            while(rs.next()){
                String testId = rs.getString("test_id");
                String testName = rs.getString("test_name").toUpperCase();
                String description = rs.getString("description").toUpperCase();
                String lastUpdate = rs.getString("last_update").toUpperCase();    
                
                FXMLLoader l = new FXMLLoader(getClass().getResource("Test.fxml"));                                  
                TestController testController = new TestController();
                testController.setTestId(testId); 
                FlowPane fp = l.load();
                l.setController(testController);
                
                fxmlLoaders.add(l);
                arrFp.add(fp);
                
                testControllers.add(testController);
                
                flpTestContainer.getChildren().add(fp);
                System.out.println("From LMS: "+testId);                                                                                                                                                                                                                                            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LearningManagement.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }    
    
}
