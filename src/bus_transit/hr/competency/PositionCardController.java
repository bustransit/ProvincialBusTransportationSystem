/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.competency;

import bus_transit.hr.learning.TestController;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class PositionCardController implements Initializable {
    ResultSet rs;
    DBUtilities db = new DBUtilities();
    /**
     * @return the positionId
     */
    public String getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public static String positionId;
    @FXML
    private AnchorPane ancPosition;
    @FXML
    private Label lblPosition;
    @FXML
    private JFXButton btnShowOptions;
    @FXML
    private VBox vbxPosition;
    @FXML
    private Label lblSalary;
    @FXML
    private Text txtDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadJobDetails();
    }    
    
    /**
     * Job Position Details
     */    
    private void loadJobDetails(){
        try {
            String q = "SELECT * FROM employee_position WHERE position_id="+positionId;
            rs = db.displayRecords(q);
            if(rs.next()){
                lblPosition.setText(rs.getString("position_name").toUpperCase());
                txtDescription.setText(rs.getString("description"));
            }} catch (SQLException ex) {
            Logger.getLogger(PositionCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * OPions
     */    
    private PopOver pop = new PopOver();
    private VBox vbx = new VBox();    
    @FXML
    private void showOptions(ActionEvent event) {
        if(pop.isFocused()){
            
        }else{            
            vbx.getChildren().clear();
            vbx.setFillWidth(true);
            vbx.setSpacing(10);
            double p = 8;
            vbx.setPadding(new Insets(p,p,p,p));
            
            double textSize = 14;
            JFXButton btnEdit = new JFXButton("Edit");                                                
            btnEdit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT));
            btnEdit.setAccessibleText("TestViewer.fxml");
            btnEdit.setFont(new Font(textSize));
            //btnEdit.setTooltip(new Tooltip("Edit Test"));                        
            vbx.getChildren().add(btnEdit);

//            JFXButton btnRun = new JFXButton("Run");                                                
//            btnRun.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PLAY)); 
//            btnRun.setFont(new Font(textSize));
//            
//            btnRun.setOnAction((evt) -> {
//                
//            });
//            vbx.getChildren().add(btnRun);            
            
            JFXButton btnDownload = new JFXButton("Download");                                           
            btnDownload.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD)); 
            btnDownload.setFont(new Font(textSize));
            //btnDownload.setTooltip(new Tooltip("Run Test"));                        
            vbx.getChildren().add(btnDownload);        
            
            pop.setContentNode(vbx);
            pop.setAnimated(true);        
            pop.setId("OptionPopOver"); 

            pop.setDetachable(false);
            pop.setAnimated(true);
            pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
            pop.setCornerRadius(4);        
            pop.show(btnShowOptions);
            String popOverId = pop.getId();
            System.out.println(popOverId);             
        }      
    }     
    
}
