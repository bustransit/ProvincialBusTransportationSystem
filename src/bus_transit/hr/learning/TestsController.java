/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestsController extends Application implements Initializable {

    /**
     * @return the stackpane
     */
    public StackPane getStackpane() {
        return stackpane;
    }

    /**
     * @param stackpane the stackpane to set
     */
    public void setStackpane(StackPane stackpane) {
        this.stackpane = stackpane;
    }
    DBUtilities db = new DBUtilities();
    ResultSet rs; 
    
    @FXML
    private CustomTextField txt_searchAll;
    @FXML
    private JFXButton btnNew;
    @FXML
    private Tooltip btnNewToolTip;
    @FXML
    private FlowPane flpTestContainer;
    @FXML
    public StackPane stackpane;
    public int duration = 0;
    public String testTitle;
    public String type;
    public String description;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTestContainer();
    }    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("Tests.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }       

    @FXML
    private void newTest(ActionEvent event) throws IOException {            
        getStackpane().toFront();

        VBox vbx = new VBox();
        vbx.setSpacing(10);
        int row=0;
        
        Font font = new Font("SansSerif", 12);
        
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("New Test")); 
                                
        JFXTextField tfTestTitle = new JFXTextField();
        tfTestTitle.setFont(font);
        tfTestTitle.setPromptText("Title".toUpperCase());        
        vbx.getChildren().add(row, tfTestTitle);
        
        JFXTextField tfTestType = new JFXTextField();
        tfTestType.setPromptText("type".toUpperCase());
        row++;        
        vbx.getChildren().add(row, tfTestType);  
        
        JFXTextArea tfTestDescription = new JFXTextArea();
        tfTestDescription.setPrefHeight(120);
        tfTestDescription.setPromptText("description".toUpperCase());        
        row++;
        vbx.getChildren().add(row, tfTestDescription);         
        
        JFXTextField tfTestDuration = new JFXTextField();
        tfTestDuration.setPromptText("duration".toUpperCase());
        row++;
        vbx.getChildren().add(row, tfTestDuration);         
        
        layout.setBody(vbx);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(getStackpane(), layout, JFXDialog.DialogTransition.LEFT);
        dialog.setOverlayClose(false);

        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            testTitle = tfTestTitle.getText();
            description = tfTestDescription.getText();
            type = tfTestType.getText();
            duration = Integer.parseInt(tfTestDuration.getText());            
            addNewTest();            
            dialog.close();            
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dialog.close();
            getStackpane().toBack();
        });

        layout.setActions(btn_cancel, btn_yes);
        dialog.show();       
    }    

    
    private void populateTestContainer(){
        String q = "SELECT * FROM test";
        rs = db.displayRecords(q);               
        ArrayList<FXMLLoader> fxmlLoaders = new ArrayList();
        ArrayList<FlowPane> arrFp = new ArrayList();
        ArrayList<TestController> testControllers = new ArrayList();
                        
        try {
            flpTestContainer.getChildren().clear();
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

    private void addNewTest() {
        String q = "INSERT INTO test (id,test_id, test_name, "
                + "test_type, description, last_update,duration) "
                + "VALUES (null,'"+getRandom()+
                "','"+testTitle+
                "','"+type+
                "','"+description+
                "',CURDATE(),'"+duration+"')";
        db.execute(q);
        flpTestContainer.getChildren().clear();
        populateTestContainer();
        System.out.println("New Test Added");
    }

    private String getRandom(){
        char[] chrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_$#@".toCharArray();
        Random n = new Random();
        StringBuilder sb = new StringBuilder((100000 + n.nextInt(900000)));
        for(int i=0; i < 10; i++){
            sb.append(chrs[n.nextInt(chrs.length)]);
        }
        return sb.toString();
    }    
    
    @FXML
    private void search(KeyEvent event) {
        String s = ((CustomTextField) event.getSource()).getText();
        String q = "SELECT * FROM test WHERE test_name LIKE '%"+s+"%'";
        searchTest(q);
        System.out.println("Search for: "+s);
    } 
    
    private void searchTest(String q){ 
        
        rs = db.displayRecords(q);               
        ArrayList<FXMLLoader> fxmlLoaders = new ArrayList();
        ArrayList<FlowPane> arrFp = new ArrayList();
        ArrayList<TestController> testControllers = new ArrayList();
                        
        try {
            flpTestContainer.getChildren().clear();
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
}
