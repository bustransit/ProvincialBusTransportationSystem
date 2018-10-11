/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.training;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrainingManagementController 
       extends Application 
       implements Initializable {

    private String q;
    DBUtilities db = new DBUtilities();
    private ResultSet rs;
    @FXML
    private AnchorPane container;
    @FXML private AnchorPane heading;
    @FXML private JFXTabPane content;
    @FXML private Tab training;
    @FXML private CustomTextField tfSearch;
    @FXML private TableView<String> tblTrainingList;
    @FXML private JFXTextField tfTrainingTitle;
    @FXML private JFXTextField tfTrainingLocation;
    @FXML private JFXDatePicker dpTrainingDate;
    @FXML private JFXComboBox<String> cbTrainingType;
    @FXML private JFXTextField tfNumberOfParticipants;
    @FXML private JFXButton btnNew;
    @FXML private JFXTimePicker tpTrainingTime;
    @FXML private JFXButton btnClear;
    @FXML private Label lblTraineesForTraining;
    @FXML private JFXTextField tfSearchTrainees;
    @FXML private VBox vbxTrainees;
    
    public String selectedTrainingId = null;
    @FXML
    private TitledPane tdpAddTrainees;
    @FXML
    private VBox vbxAddTrainees;
    @FXML
    private AnchorPane ancTraining;
    @FXML
    private JFXButton btnUpdate;
    private StackPane stackPane;
    @FXML
    private MenuItem mnuGenerateReport;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {               
        refreshTable();
        
        populateVBXTrainees();
        
        ObservableList type = cbTrainingType.getItems();       
        type.add("");
        type.add("Inhouse");
        type.add("Outdoor");        
        cbTrainingType.setItems(type);
        
    }    
    
    private Integer getTotalEmployee(){
        String q = "SELECT COUNT(emp_id) as 'total' FROM employee";
        rs = db.displayRecords(q);
        int t = 0;
        try {
            if(rs.next()){
                t = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
        return t;
    }
    
    @FXML
    private void clearTrainingFields(){
        tfTrainingTitle.setText("");
        tfTrainingLocation.setText("");
        tfNumberOfParticipants.setText("");
        tpTrainingTime.setValue(null);
        dpTrainingDate.setValue(null);
        cbTrainingType.getSelectionModel().select(0);
        
        btnUpdate.setDisable(true);         
        btnNew.setDisable(false);
    }
    
    
    EventHandler<MouseEvent> newTraining = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String title = tfTrainingTitle.getText();
                int participants = Integer.parseInt(tfNumberOfParticipants.getText());
                LocalTime time = tpTrainingTime.getValue();
                LocalDate date = dpTrainingDate.getValue();
                String venue = tfTrainingLocation.getText();
                String type = (String) cbTrainingType.getSelectionModel()
                                                     .getSelectedItem().toString();

                System.out.println(title);
                System.out.println(participants);
                System.out.println(time);
                System.out.println(date);
                System.out.println(venue);
                System.out.println(type);

                String q = "INSERT INTO training "+
                           "(training_id, "+
                           "title, participants, TIME, target_date, venue, TYPE) " +
                           "VALUES(NULL, '"+title+
                           "', '"+participants+
                           "', '"+time+
                           "', '"+date+
                           "','"+venue+
                           "','"+type+"');";

                db.execute(q);

                refreshTable();
                clearTrainingFields();            
            }
    };
    
    EventHandler<MouseEvent> updateTraining = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String title = tfTrainingTitle.getText();
                int participants = Integer.parseInt(tfNumberOfParticipants.getText());
                LocalTime time = tpTrainingTime.getValue();
                LocalDate date = dpTrainingDate.getValue();
                String venue = tfTrainingLocation.getText();
                String type = (String) cbTrainingType.getSelectionModel()
                                                     .getSelectedItem().toString();

                System.out.println(title);
                System.out.println(participants);
                System.out.println(time);
                System.out.println(date);
                System.out.println(venue);
                System.out.println(type);

                String q = "UPDATE training "
                        + "SET title = '"+title+"', "
                        + "participants='"+participants+"', "
                        + "TIME='"+time+"', "
                        + "target_date='"+date+"', "
                        + "venue='"+venue+"', "
                        + "TYPE='"+type+"' "
                        + "WHERE training_id="+selectedTrainingId;

                db.execute(q);

                refreshTable();
                clearTrainingFields();            
            }
    };    
    
    
    @FXML
    private void updateTraining(){
                String title = tfTrainingTitle.getText();
                int participants = Integer.parseInt(tfNumberOfParticipants.getText());
                LocalTime time = tpTrainingTime.getValue();
                LocalDate date = dpTrainingDate.getValue();
                String venue = tfTrainingLocation.getText();
                String type = (String) cbTrainingType.getSelectionModel()
                                                     .getSelectedItem().toString();

                System.out.println(title);
                System.out.println(participants);
                System.out.println(time);
                System.out.println(date);
                System.out.println(venue);
                System.out.println(type);

                String q = "UPDATE training "
                        + "SET title = '"+title+"', "
                        + "participants='"+participants+"', "
                        + "TIME='"+time+"', "
                        + "target_date='"+date+"', "
                        + "venue='"+venue+"', "
                        + "TYPE='"+type+"' "
                        + "WHERE training_id="+selectedTrainingId;

                db.execute(q);
                                
                refreshTable();
                clearTrainingFields();
                btnNew.setDisable(false);
                btnUpdate.setDisable(true);
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("TrainingManagement.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    
    private void refreshTable(){
        db.populateTable("SELECT training_id as 'ID', "
                + "title as 'TITLE', "
                + "participants as 'TRAINEES', "
                + "TIME as 'TIME', "
                + "target_date as 'DATE', "
                + "venue as 'VENUE', "
                + "TYPE FROM training", tblTrainingList);
    }

    public static void main(String[] args) {
        launch(args);
    }      

    @FXML    
    private void searchTraining(KeyEvent event) {
        String s = tfSearch.getText();
        db.populateTable("SELECT training_id as 'ID', "
                + "title as 'TITLE', "
                + "participants as 'TRAINEES', "
                + "TIME as 'TIME', "
                + "target_date as 'DATE', "
                + "venue as 'VENUE', "
                + "TYPE FROM training "
                + "WHERE title LIKE '%"+s+"%'", tblTrainingList);
    }

    @FXML
    private void saveNewTraining(ActionEvent event) {          
        try{
            String title = tfTrainingTitle.getText();
            int participants = Integer.parseInt(tfNumberOfParticipants.getText());
            LocalTime time = tpTrainingTime.getValue();
            LocalDate date = dpTrainingDate.getValue();
            String venue = tfTrainingLocation.getText();
            String type = (String) cbTrainingType.getSelectionModel()
                                                 .getSelectedItem().toString();

            System.out.println(title);
            System.out.println(participants);
            System.out.println(time);
            System.out.println(date);
            System.out.println(venue);
            System.out.println(type);

            String q = "INSERT INTO training "+
                       "(training_id, "+
                       "title, participants, TIME, target_date, venue, TYPE) " +
                       "VALUES(NULL, '"+title+
                       "', '"+participants+
                       "', '"+time+
                       "', '"+date+
                       "','"+venue+
                       "','"+type+"');";

            db.execute(q);

            refreshTable();
            clearTrainingFields();            
        }catch(Exception e){
            System.out.println(e);
        }
    }   


    @FXML
    private void addTrainee(ActionEvent event) {
    }
    
    private void addToTraining(String trainingId, String empId){
        String q = "INSERT INTO training_participants (training_id,emp_id) "
                + "VALUES("+trainingId+","+empId+")";
        db.execute(q);
    }
    
    private void removeFromTraining(String trainingId, String empId){
        String q = "DELETE FROM training_participants "
                 + "WHERE training_id="+trainingId
                 + " AND emp_id="+empId;
       
        db.execute(q);
    }
    
    private void populateVBXTrainees(){
        String q = "SELECT employee.emp_id AS 'id',"
                + "CONCAT(employee.lastname,', ', employee.firstname, "
                + "' (',employee_position.position_name,')') AS 'NAME',  "
                + "department.department_code AS 'CODE' "
                + "FROM employee, employee_position, department "
                + "WHERE employee.position_id = employee_position.position_id "
                + "AND employee_position.department_code = "
                + "department.department_code;";
        
        rs = db.displayRecords(q);
        
        vbxTrainees.getChildren().clear();
        
        try {
            while(rs.next()){
                JFXCheckBox cbEmployee = new JFXCheckBox(rs.getString("NAME"));
                cbEmployee.setAccessibleText(rs.getString("id"));
                
                String qry = "SELECT * FROM training_participants WHERE training_id="+selectedTrainingId;
                
                ResultSet r = db.displayRecords(qry);
                while(r.next()){
                    if(rs.getString("id").equals(r.getString("emp_id"))){
                        cbEmployee.setSelected(true);
                    }
                }
                                
                vbxTrainees.getChildren().add(cbEmployee);
                
                cbEmployee.setOnAction((event) -> {
                    String empId = cbEmployee.getAccessibleText();
                    if(cbEmployee.isSelected()){
                        addToTraining(selectedTrainingId, empId);
                    }else{
                        removeFromTraining(selectedTrainingId, empId);
                    }
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void setSelectedTraining(MouseEvent event) {
        if(tblTrainingList.getSelectionModel().getSelectedItem() != null){
            vbxAddTrainees.setDisable(false);        
            Object o = tblTrainingList.getSelectionModel().getSelectedItem();
            String id = o.toString().split(",")[0].substring(1);
            
            String title = o.toString().split(",")[1].substring(1);
            String location = o.toString().split(",")[2].substring(1);
            String nOParticipants = o.toString().split(",")[3].substring(1);
            String time = o.toString().split(",")[4].substring(1);
            String date = o.toString().split(",")[5].substring(1);
            String type = o.toString().split(",")[6].substring(1);           
            selectedTrainingId = id;
            
            System.out.println(selectedTrainingId);
            
            String q = "SELECT training_id as 'ID', "
                + "title as 'TITLE', "
                + "participants as 'TRAINEES', "
                + "TIME as 'TIME', "
                + "target_date as 'DATE', "
                + "venue as 'VENUE', "
                + "TYPE FROM training "
                + "WHERE training_id="+selectedTrainingId;
            
            rs = db.displayRecords(q);
            try {
                if(rs.next()){
                    tfTrainingTitle.setText(rs.getString("TITLE"));
                    lblTraineesForTraining.setText(rs.getString("TITLE"));
                    
                    tfNumberOfParticipants.setText(rs.getString("TRAINEES"));
                    tfTrainingLocation.setText(rs.getString("VENUE"));
                    dpTrainingDate.setValue(rs.getDate("DATE").toLocalDate());
                    tpTrainingTime.setValue(rs.getTime("TIME").toLocalTime());
                    
                    cbTrainingType.getSelectionModel().select(rs.getString("TYPE").toLowerCase());
                    
                    btnUpdate.setDisable(false);
                    
                    populateVBXTrainees();                                        
                }
            } catch (SQLException ex) {
                Logger.getLogger(TrainingManagementController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }  
            
            
            btnNew.setDisable(true);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    private void searchTrainees(KeyEvent event) {
        String s = tfSearchTrainees.getText();
        String q = "SELECT employee.emp_id AS 'id',"
                + "CONCAT(employee.lastname,', ', employee.firstname, "
                + "' (',employee_position.position_name,')') AS 'NAME',  "
                + "department.department_code AS 'CODE' "
                + "FROM employee, employee_position, department "
                + "WHERE employee.position_id = employee_position.position_id "
                + "AND employee_position.department_code = "
                + "department.department_code "
                + "AND employee.lastname LIKE '%"+s+"%' ";
        
        rs = db.displayRecords(q);
        
        vbxTrainees.getChildren().clear();
        
        try {
            while(rs.next()){
                JFXCheckBox cbEmployee = new JFXCheckBox(rs.getString("NAME"));
                cbEmployee.setAccessibleText(rs.getString("id"));
                
                String qry = "SELECT * FROM training_participants WHERE training_id="+selectedTrainingId;
                
                ResultSet r = db.displayRecords(qry);
                while(r.next()){
                    if(rs.getString("id").equals(r.getString("emp_id"))){
                        cbEmployee.setSelected(true);
                    }
                }
                                
                vbxTrainees.getChildren().add(cbEmployee);
                
                cbEmployee.setOnAction((even) -> {
                    String empId = cbEmployee.getAccessibleText();
                    if(cbEmployee.isSelected()){
                        addToTraining(selectedTrainingId, empId);
                    }else{
                        removeFromTraining(selectedTrainingId, empId);
                    }
                    
                    
                    
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainingManagementController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }        
    }    

    @FXML
    private void generateReport(ActionEvent event) {        
    }
}
