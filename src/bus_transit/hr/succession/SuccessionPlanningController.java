/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.succession;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SuccessionPlanningController extends Application implements Initializable {

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
    @FXML
    private Tab modules;
    @FXML
    private CustomTextField txt_searchAll;
    @FXML
    private JFXButton btnNew;
    @FXML
    private Tooltip btnNewToolTip;
    @FXML
    private StackPane stackPane;
    @FXML
    private FlowPane fpSuccessionPlan;

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
        
       populateSuccesionPlanePane();
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
        chartToPNG(myPiechart, "BarChartReport.jpg");
        chartToPNG(myBarChart, "BarChartReport.jpg");
        chartToPNG(myLineChart, "BarChartReport.jpg");
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
    private void SearchAllRequest(KeyEvent event) {
    }

    public void populateSuccesionPlanePane(){
        String q = "SELECT * FROM succession_plan";
        rs = db.displayRecords(q);
        fpSuccessionPlan.getChildren().clear();
        try {
            while(rs.next()){
                FXMLLoader l =  new FXMLLoader(getClass().getResource("SuccessionCard.fxml"));                                                            
                SuccessionCardController successionCardController = new SuccessionCardController();                
                successionCardController.setPositionId(rs.getString("position_id"));
                
                AnchorPane fp = l.load();
                l.setController(successionCardController);
                
                fpSuccessionPlan.getChildren().addAll(fp);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuccessionPlanningController.class.getName())
                  .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SuccessionPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    LocalDate dateExit;
    String positionId;
    String employeeId;
    String readiness;    
    
    @FXML
    private void loadNewSuccesionPlan(ActionEvent event) throws InterruptedException {
        JFXDialogLayout dialog = new JFXDialogLayout();
        dialog.setHeading(new Text("Examinee Info"));
        
        VBox vbx = new VBox();
        vbx.setFillWidth(true);
        
        String q = "";
        
        JFXComboBox cbPosion = new JFXComboBox();
        q = "SELECT CONCAT(position_id,', ', position_name) FROM employee_position";        
        db.populateComboBox(q, cbPosion);
        vbx.getChildren().add(cbPosion);
        cbPosion.setOnAction((e) -> {
            String s = cbPosion.getSelectionModel().getSelectedItem().toString();
            setSelectedPosition(s);
        });        
        
        JFXComboBox cbEmployee = new JFXComboBox();
        q = "SELECT CONCAT(emp_id,', ',lastname,"
            + "', ', firstname,' ', middlename) AS 'employee' FROM employee";
                        
        db.populateComboBox(q, cbEmployee);
        vbx.getChildren().add(cbEmployee);
        
        cbEmployee.setOnAction((e) -> {
            String s = cbEmployee.getSelectionModel().getSelectedItem().toString();
            setSelectedEmployee(s);
        });
        
//        JFXComboBox<String> cbReadiness = new JFXComboBox();
//        cbReadiness.getItems().add("IMMEDIATE");
//        cbReadiness.getItems().add("1-3 YEARS");
//        cbReadiness.getItems().add("HIGH POTENTIALS");
//        cbReadiness.setOnAction((evt) -> {
//            setReadiness(cbReadiness);
//        });
//        
//        vbx.getChildren().add(cbReadiness);
        
        JFXDatePicker dp = new JFXDatePicker();
        vbx.getChildren().add(dp);
        
        dp.setOnAction((evt) -> {
            dateExit = dp.getValue();
        });
        
        dialog.setBody(vbx);
        
        JFXDialog dlg = new JFXDialog(stackPane,dialog,JFXDialog.DialogTransition.CENTER);
        
        JFXButton btnOK = new JFXButton("OK");
        vbx.getChildren().add(btnOK);
        btnOK.setOnAction((evt) -> {                                         
            dlg.close();
        });
        
        JFXButton btnCancel = new JFXButton("CANCEL");
        vbx.getChildren().add(btnCancel);
        btnOK.setOnAction((evt) -> {          
            dlg.close();
        });        
        
                                      
        dlg.show();
        dialog.setActions(btnOK);        
        
        dlg.setOnDialogClosed((evt) -> {
            System.out.println("Date: "+dateExit);
            System.out.println("Employee ID: "+employeeId);
            System.out.println("Position ID: "+positionId);
            System.out.println("Readiness: "+readiness); 
            System.out.println("Hi");
            try{
                String qry = "INSERT INTO succession_plan "
                        + "(position_id, emp_id, exit_date) "
                        + "VALUES("+positionId
                        +","+employeeId
                        +",'"+dateExit+"')";
                db.execute(qry);
            }catch(Exception e){
                System.out.println(e);
            }
            populateSuccesionPlanePane();
        });
        
    }
    
    
    private void setSelectedPosition(String s){        
        positionId = s.toString().split(",")[0].substring(1);         
    }
    
    private void setSelectedEmployee(String s){
        employeeId = s.toString().split(",")[0].substring(1);         
    }
    
    private void setReadiness(JFXComboBox c){
        readiness = c.getSelectionModel().getSelectedItem().toString();        
    }
}
