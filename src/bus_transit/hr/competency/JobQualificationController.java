/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.competency;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class JobQualificationController extends Application implements Initializable {
    ResultSet rs;
    DBUtilities db = new DBUtilities();
    
    @FXML
    private AnchorPane heading;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXButton btnNew;
    @FXML
    private FlowPane flpMainContainer;
    @FXML
    private CustomTextField txtSearch;
    @FXML
    private JFXComboBox<String> cbFilterBy;
    JFXDialog dlg;
    JFXDialogLayout dialog;
    @FXML
    private AnchorPane ancHeader;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadJobPosition();  
        cbFilterBy.getItems().add("Position Code");        
        cbFilterBy.getItems().add("Salary Rate");
    }   
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("JobQualification.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }   
       
    public static void main(String[] args) {
        launch(args);        
    }    
    
//    @FXML
//    private void setClearableText(Event event) {
//        AnchorPane vbx = ancHeader;
//        int i = vbx.getChildren().indexOf(event.getSource());
//        Node node = (Node) event.getSource();
//        if(node instanceof TextField){
//            vbx.getChildren().remove(i);
//            TextField b = TextFields.createClearableTextField();
//            b.setId(node.getId());            
//            vbx.getChildren().add(i, b);
//            b.requestFocus();
//            
//            
//            
//            b.setOnKeyReleased((evt) -> {
//                search(evt);
//            });
//        }
//    }    

    @FXML
    private void search(KeyEvent event) {
        String s = ((CustomTextField) event.getSource()).getText().trim();
        if((s != null) || (!"".equals(s))){

            String q =  "SELECT position.position_code " +
                        "FROM POSITION,position_benefits,position_responsibilities,position_qualification, salary_grade " +
                        "WHERE " +
                        "MATCH(" +
                        "position_name," +
                        "position.position_code," +
                        "description) " +
                        "AGAINST ('"+s+"' IN NATURAL LANGUAGE MODE) " +
                        "OR position.department_code LIKE CONCAT('%','"+s+"','%') " +
                        "OR position.position_name LIKE CONCAT('%','"+s+"','%') " +
                        "OR position.description LIKE CONCAT('%','"+s+"','%') " +
                        "OR salary_grade.monthly_rate LIKE CONCAT('%','"+s+"','%') " +
                        "GROUP BY position.position_code;";
            
            rs = db.displayRecords(q);
            flpMainContainer.getChildren().clear();
            try {
                while(rs.next()){
                    FXMLLoader l =  new FXMLLoader(getClass().getResource("PositionCard.fxml"));                                                            
                    PositionCardController positionCardController = new PositionCardController();                
                    positionCardController.setPositionId(rs.getString("position_code"));
                    Node fp = l.load();
                    l.setController(positionCardController);
                    flpMainContainer.getChildren().addAll(fp);                
                }
            } catch (SQLException ex) {
                Logger.getLogger(JobQualificationController.class.getName())
                      .log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NullPointerException ex){
                loadNotFound();
            }
        }else{
            loadJobPosition();
        }
    }

    private void loadNotFound(){
        Label lbl = new Label("Not Found");
        flpMainContainer.getChildren().add(lbl);
    }
    
    @FXML
    public void newJobPosition(ActionEvent event) {
        try {
            dialog = new JFXDialogLayout();
            //dialog.setHeading(new Text("New"));                        
            //NewJobQualificationController newJobQualification  = new NewJobQualificationController();
            
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewJobQualification.fxml"));
            Parent root = loader.load();
            NewJobQualificationController newJobQualification = loader.getController();                        
            stage.setScene(new Scene(root));
                                    
            stage.setTitle("New Job Qualification");          
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());            
                                    
            dialog.setBody(root);           
            dlg = new JFXDialog(stackpane,dialog,JFXDialog.DialogTransition.CENTER);                 
            dlg.show();
                                    
            JFXButton save = new JFXButton("Save");
            save.setOnAction((sEvt) -> {
                newJobQualification.save();
                loadJobPosition();
                dlg.close();
            });
            
            JFXButton cancel = new JFXButton("Cancel");
            cancel.setOnAction((sEvt) -> {
                dlg.close();
            });
                                    
            dialog.setActions(save,cancel);            
        } catch (IOException ex) {
            Logger.getLogger(JobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void loadJobPosition(){        
        String q = "select * from position order by position_name";
        rs = db.displayRecords(q);
        flpMainContainer.getChildren().clear();
        try {
            while(rs.next()){
                FXMLLoader l =  new FXMLLoader(getClass().getResource("PositionCard.fxml"));                                                            
                PositionCardController positionCardController = new PositionCardController();                
                positionCardController.setPositionId(rs.getString("position_code"));                
                Node fp = l.load();
                l.setController(positionCardController);
                                
                flpMainContainer.getChildren().addAll(fp);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobQualificationController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
