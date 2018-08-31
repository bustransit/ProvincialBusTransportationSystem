/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrainingManagementController implements Initializable {

    @FXML
    private JFXButton btn_menu;
    @FXML
    private JFXButton btn_pdf;
    @FXML
    private PieChart myPiechart;
    @FXML
    private JFXButton btn_pdf2;
    @FXML
    private BarChart<?, ?> myBarChart;
    @FXML
    private LineChart<?, ?> myLineChart;
    @FXML
    private JFXButton btn_report3;
    private String q;
    DBUtilities db = new DBUtilities();
    private ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //database data
        q = "SELECT succession_name, succession_rate FROM succ";
        //para lumabas data sa chart mo 
        db.createPieChart("Internal Placement Rate", q, myPiechart);
        q = "SELECT employee_type, employee_rate FROM barmoto";
        db.createBarChart("Successor Readines For Key Roles", "2018 Month Of September", q, myBarChart);
        db.createLineChart("My line Chart", "2019 series", q, myLineChart);
    }    

    @FXML
    private void back(ActionEvent event) {
    }
    
}
