/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.logistics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import static javafx.scene.input.KeyCode.C;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;
import org.controlsfx.control.Notifications;
import utilities.DBUtilities;
import bus_transit.DashboardController;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class AuditManagementController implements Initializable {

    @FXML
    private BorderPane container;
    @FXML
    private JFXButton save;
    @FXML
    private JFXTextField ai;
    @FXML
    private JFXTextField an;
    
    @FXML
    private JFXTextArea descrip;
    
    
    @FXML
    private JFXDatePicker startdate;
    @FXML
    private JFXDatePicker enddate;

    
    DBUtilities dp = new DBUtilities();
    @FXML
    private TableView<?> tbl_reports;
    @FXML
    private StackPane stackpane;
    
    private String id;
    
    private ResultSet rs;
    @FXML
    private JFXButton ae_save;
    @FXML
    private JFXTextField cd;
    @FXML
    private JFXTextField nm;
    @FXML
    private JFXTextField typ;
    @FXML
    private JFXTextField cntctprsn;
    @FXML
    private JFXTextField dsgntn;
    @FXML
    private JFXTextField nmbr;
    @FXML
    private JFXTextField ml;
    @FXML
    private JFXTextField plc;
    @FXML
    private JFXTextField prvnc;
    @FXML
    private JFXTextField cntry;
    @FXML
    private ComboBox<?> CB;
    private TableView<?> tbl_auditplan;
    private int audit_id;
    private String status;
    @FXML
    private Button btn_done;
    @FXML
    private MenuItem done_ongoing;


    
    
  
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
        
        //FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.)
        
         dp.populateComboBox("Select NAME from audit_entity",CB);
         dp.buildData("Select * from auditplan",tbl_reports);
         
        startdate.setOnAction(evt -> {
            System.out.println(startdate.getValue());
        });
        enddate.setOnAction(evt -> {
            System.out.println(enddate.getValue());
        });
        
          
          
    }    

    @FXML
    private void btn_save(ActionEvent event) {
        
        stackpane.toFront();

        Label header = new Label("SAVE");
        header.setFont(new Font("SansSerif", 14));

        Label body = new Label("Are you sure you want to save?");
        body.setFont(new Font("SansSerif", 14));

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(header);
        layout.setBody(body);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(stackpane, layout, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);

        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
             String d = startdate.getValue().toString();
            String q = "INSERT INTO auditplan (AUDIT_ID,"
                    + "AUDIT_NAME,"
                    + "AUDITEE,"
                    + "DESCRIPTION,"
                    + "START_DATE,"
                    + "END_DATE) "
                    + "VALUES ('"
                    + ai.getText()+ "', '"
                    + an.getText() + "', '"
                    + CB.getValue()+ "', '"
                    + descrip.getText() + "', '"
                    + startdate.getValue() + "','"
                    + enddate.getValue() + "')";
                  
            dp.execute(q);
            dp.buildData("Select * from auditplan",tbl_reports);
           
            ai.setText(null);
            an.setText(null);
            CB.setValue(null);
            descrip.setText(null);
            startdate.setValue(null);
            enddate.setValue(null);
                      Notifications notifications =  Notifications.create()               
                    .title("SAVE")
                    .text("SAVE DONE")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){      
                     System.out.println("CLICK ME");
                    } 
    });             notifications.darkStyle();
                    notifications.showConfirm();
            
            dialog.close();
            stackpane.toBack();
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dialog.close();
            stackpane.toBack();
        });

        layout.setActions(btn_cancel, btn_yes);
        dialog.show();
       

    }
    @FXML
    private void save_auditentity(ActionEvent event) {      
          stackpane.toFront();

        Label header = new Label("SAVE");
        header.setFont(new Font("SansSerif", 14));

        Label body = new Label("Are you sure you want to save?");
        body.setFont(new Font("SansSerif", 14));

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(header);
        layout.setBody(body);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(stackpane, layout, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);

        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {  
         String q = "INSERT INTO audit_entity (CODE,"
                 + "NAME,"
                 + "TYPE,"
                 + "CONTACT_PERSON,"
                 + "DESIGNATION,"
                 + "NUMBER,"
                 + "EMAIL,"
                 + "PLACE,"
                 + "PROVINCE,"
                 + "COUNTRY)"
                 + "VALUES ('"
                 + cd.getText()+ "', '"
                 + nm.getText() + "', '"
                 + typ.getText() + "', '"
                 + cntctprsn.getText() + "', '"
                 + dsgntn.getText() + "', '"
                 + nmbr.getText() + "', '"
                 + ml.getText() + "', '"
                 + plc.getText() + "', '"
                 + prvnc.getText() + "', '"  
                 + cntry.getText() + "')";
            dp.execute(q);
            dp.populateComboBox("Select NAME from audit_entity",CB);
            
            cd.setText(null);
            nm.setText(null);
            typ.setText(null);
            cntctprsn.setText(null);
            dsgntn.setText(null);
            nmbr.setText(null);
            ml.setText(null);
            plc.setText(null);
            prvnc.setText(null);
            cntry.setText(null);
       
                
            
                     Notifications notifications =  Notifications.create()               
                    .title("SAVE")
                    .text("SAVE DONE")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){      
                     System.out.println("CLICK ME");
                    } 
    });             notifications.darkStyle();
                    notifications.showConfirm();
            
            dialog.close();
            stackpane.toBack();
        });

        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            dialog.close();
            stackpane.toBack();
        });

        layout.setActions(btn_cancel, btn_yes);
        dialog.show();
       
        
    }

    @FXML
    private void click_plan(MouseEvent event) {
           ObservableList<String> rf = (ObservableList<String>) tbl_reports.getSelectionModel().getSelectedItem();
        id = rf.get(0);
        rs = dp.displayRecords("Select * from auditplan where  audit_id='" + id + "'");
        try {
            if (rs.next()) {
               
               audit_id = rs.getInt("audit_id");

      
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuditManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btn_done(ActionEvent event) {
          String q = "UPDATE auditplan set status ='DONE' where  audit_id= '" + audit_id + "'";
          dp.execute(q);
        
          dp.buildData("Select * from auditplan",tbl_reports);
                    Notifications notifications =  Notifications.create()               
                    .title("DONE")
                    .text("PROCESS DONE")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){      
                     System.out.println("CLICK ME");
                    } 
    });             notifications.darkStyle();
                    notifications.showConfirm();
              
    }

    @FXML
    private void ongoing_done(ActionEvent event) {
        String q = "UPDATE auditplan set status ='DONE' where  audit_id= '" + audit_id + "'";
          dp.execute(q);
        
          dp.buildData("Select * from auditplan",tbl_reports);
                    Notifications notifications =  Notifications.create()               
                    .title("DONE")
                    .text("PROCESS DONE")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){      
                     System.out.println("CLICK ME");
                    } 
    });             notifications.darkStyle();
                    notifications.showConfirm();
              
    }


   

}
