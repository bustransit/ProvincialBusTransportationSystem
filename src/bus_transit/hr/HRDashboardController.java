/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import bus_transit.hr.reports.HR2Report;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
//import net.sf.jasperreports.engine.JRException;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class HRDashboardController implements Initializable {

    @FXML
    private JFXButton shorReport;
    
    DBUtilities db = new DBUtilities();
    @FXML
    private static AnchorPane reports;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        shorReport.setOnAction(evt->{
//            try {
//                // --- Show Jasper Report on click-----
//                new HR2Report().showReport();
//            } catch (ClassNotFoundException | JRException | SQLException e1) {
//                e1.printStackTrace();
//            }        
//        });
    }    

    @FXML
    public void showReport(ActionEvent e){
//        try{
//            JasperReport jrp = JasperCompileManager.compileReport("reports/hr2_report.frxml");
//            JasperPrint jrpPrint = JasperFillManager.fillReport(jrp,null, db.connection );
//            JRViewer view =  new JRViewer(jrpPrint);
//            System.out.println(view);
//            //reports.getChildren();
//            view.setVisible(true);
//        }catch (Exception ex){
//            System.out.println(e);
//        }
    }
    
    private void createReport(){
        //Document report = new Document() {};
    }
}
