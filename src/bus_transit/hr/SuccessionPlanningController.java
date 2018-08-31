/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SuccessionPlanningController extends Application implements Initializable {

    @FXML
    private JFXButton btn_menu;
    private JFXButton btn_report;
    @FXML
    private JFXButton btn_pdf;
    private String External;
    private String Internal;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DBUtilities db = new DBUtilities();
    @FXML
    private PieChart myPiechart;
    private String q;
    @FXML
    private JFXButton btn_pdf2;
    @FXML
    private BarChart<?, ?> myBarChart;
    @FXML
    private StackedAreaChart<?, ?> myLineChart;
    @FXML
    private JFXButton btn_report3;

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
        
        
      //  q = "SELECT employee_type, employee_rate FROM barmoto";
        db.createStackedAreaChartLap("Position Filled By Nominated Successor","2018 Month Of September",q,myLineChart);
        
        q = "SELECT Quarter, Quarter_rate FROM stackmoto";
        db.createStackedAreaChartLap("Position Filled By Nominated Successor","2018 Month Of September",q,myLineChart);
        
        btn_pdf.setOnAction(e -> {
            
            
            chartToPNG(myBarChart,"BarChartReport.png");
            chartToPNG(myLineChart,"BarChartReport.jpg");
            chartToPNG(myPiechart,"piechart.png");
            
            
        });
        
       // }
    }
    
    private void chartToPNG(Chart b, String filename){
        b.setAnimated(false);
        WritableImage image = b.snapshot(
            new SnapshotParameters(), null);
        File file = new File(
        "scr\\bus_transit\\hr\\reports\\"+filename);
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg",file);
        }catch(IOException e){
            System.out.println(e);
    }
        
        
        
        
        btn_pdf2.setOnAction(e -> {            
            chartToPNG(myPiechart,"BarChartReport.jpg");
            chartToPNG(myBarChart,"BarChartReport.jpg");
            chartToPNG(myLineChart,"BarChartReport.jpg");            
        });
        
        }
        
    
    private void chartToJPG(Chart b, String filename){
        b.setAnimated(false);
        WritableImage image = b.snapshot(
            new SnapshotParameters(), null);
        File file = new File(
        "scr\\bus_transit\\hr\\reports\\img\\"+filename);
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg",file);
        }catch(IOException e){
            System.out.println(e);
    }
        
    }
    
    
      @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass().getResource("SuccessionPlanning.fxml"));
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
    private void back(ActionEvent event) {
        
    }
    
}
