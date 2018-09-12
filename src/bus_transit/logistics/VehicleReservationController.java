/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.logistics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import bus_transit.DashboardController;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class VehicleReservationController implements Initializable {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    DBUtilities db = new DBUtilities();
    private String t;
    private int quan;
    private int quan1;
    private int total;
    private int total1;
    private String q;
    private InputStream is;
    private FileChooser filechooser;
    private FileInputStream fis;
    private File file;
    @FXML
    private BorderPane container;
    @FXML
    private BarChart<?, ?> available;
    @FXML
    private BarChart<?, ?> reserved;
    @FXML
    private JFXTextField type1;
    @FXML
    private JFXTextField number1;
    @FXML
    private JFXTextField seaters1;
    @FXML
    private JFXTextField ID1;
    @FXML
    private TextArea textarea;
    @FXML
    private TableView<?> table1;
    @FXML
    private ImageView imageview;
    @FXML
    private Button browse;
    @FXML
    private JFXButton SAVE;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private ImageView imageview1;
    @FXML
    private JFXTextField type;
    @FXML
    private JFXTextField number;
    @FXML
    private JFXTextField seaters;
    @FXML
    private JFXTextField ID;
    @FXML
    private JFXTextField department;
    @FXML
    private JFXTextField purpose;
    @FXML
    private JFXDatePicker start;
    @FXML
    private JFXDatePicker end;
    @FXML
    private JFXTextField availability;
    @FXML
    private TableView<?> table4;
    @FXML
    private JFXButton submit;
    @FXML
    private JFXButton DONE;
    @FXML
    private ComboBox<?> request;
    @FXML
    private JFXTextArea area;
    @FXML
    private JFXTextField fuel_capacity1;
    @FXML
    private JFXTextField fuel_type1;
    @FXML
    private JFXTextField fuel_capacity;
    @FXML
    private JFXTextField fuel_type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(DashboardController.root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        db.populateComboBox("Select REQUEST from REQUEST_VEHICLE",request);
        db.buildData("SELECT * FROM new_vehicle", table1);
        q = "SELECT type_of_vehicle, quantity FROM vacant_vehicle";
        db.createBarChart("Available Vehicles", "2018 series", q, available);
        q = "SELECT type_of_vehicle, quantity FROM reserved_vehicle";
        db.createBarChart("Reserved Vehicles", "2018 series", q, reserved);
        db.buildData("SELECT vehicle_id,plate_number,type_of_vehicle,fuel_capacity,fuel_type,seaters,availability,Department,purpose FROM available_vehicle", table4);
        
        
        table1.setOnMousePressed(value -> {

            ObservableList<String> r = (ObservableList<String>) table1.getSelectionModel().getSelectedItem();
            String id = r.get(0);

            rs = db.displayRecords("SELECT * FROM new_vehicle WHERE vehicle_id = '" + id + "'");
            try {
                while (rs.next()) {
                    try {
                        ID1.setText(rs.getString("vehicle_id"));
                        number1.setText(rs.getString("plate_number"));
                        type1.setText(rs.getString("type_of_vehicle"));
                        seaters1.setText(rs.getString("seaters"));
                        fuel_capacity1.setText(rs.getString("fuel_capacity"));
                        fuel_type1.setText(rs.getString("fuel_type"));
                        

                    } catch (Exception e) {
                    }
                }
            } catch (SQLException ex) {

            }
            table1.getSelectionModel().getSelectedItem();
            t = type1.getText();
            rs = db.displayRecords("SELECT quantity FROM vacant_vehicle WHERE type_of_vehicle = '" + type1.getText() + "'");
            try {
                while (rs.next()) {
                    try {

                        quan = rs.getInt("quantity");
                        

                    } catch (Exception e) {
                    }
                }
            } catch (SQLException ex) {

            }

        });
        
        table4.setOnMousePressed(value -> {

            ObservableList<String> r = (ObservableList<String>) table4.getSelectionModel().getSelectedItem();
            String id = r.get(0);

            rs = db.displayRecords("SELECT * FROM available_vehicle WHERE vehicle_id = '" + id + "'");
            try {
                while (rs.next()) {
                    try {
                        
                        ID.setText(rs.getString("vehicle_id"));
                        number.setText(rs.getString("plate_number"));
                        type.setText(rs.getString("type_of_vehicle"));
                        fuel_capacity.setText(rs.getString("fuel_capacity"));
                        fuel_type.setText(rs.getString("fuel_type"));
                        seaters.setText(rs.getString("seaters"));
                        is = rs.getBinaryStream("Image");
                        department.setText(rs.getString("department"));
                        purpose.setText(rs.getString("purpose"));
                        start.setValue(rs.getDate("start").toLocalDate());
                        end.setValue(rs.getDate("end").toLocalDate());
                        availability.setText(rs.getString("availability"));
                        String a = rs.getString("availability");
                        OutputStream os = new FileOutputStream(new File("photo.jpg"));
                        byte[] content = new byte[1024];
                        int size = 0;

                        while ((size = is.read(content)) != -1) {
                            os.write(content, 0, size);
                        }
                        os.close();
                       

                        Image image = new Image("file:photo.jpg");
                        imageview1.setImage(image);
                        
                        
                        if(a.equals("RESERVED")){
                        submit.setDisable(true);
                        DONE.setDisable(false);
                        }else if(a.equals("AVAILABLE")){
                        submit.setDisable(false);
                        DONE.setDisable(true);
                        }

                    } catch (Exception e) {
                    }
                }
            } catch (SQLException ex) {

            }
            table4.getSelectionModel().getSelectedItem();
            String i = r.get(0);
            t = type.getText();
            rs = db.displayRecords("SELECT quantity FROM vacant_vehicle WHERE type_of_vehicle = '" + type.getText() + "'");
            try {
                while (rs.next()) {
                    try {

                        quan = rs.getInt("quantity");
                  

                    } catch (Exception e) {
                    }
                }
            } catch (SQLException ex) {

            }
            table4.getSelectionModel().getSelectedItem();
            String d = r.get(2);
            t = type.getText();
            rs = db.displayRecords("SELECT quantity FROM reserved_vehicle WHERE type_of_vehicle = '" + d + "'");
            try {
                while (rs.next()) {
                    try {

                        quan1 = rs.getInt("quantity");
                       
                    } catch (Exception e) {
                    }
                }
            } catch (SQLException ex) {

            }

        });


               submit.setOnAction(value -> {
            ObservableList<String> r = (ObservableList<String>) table4.getSelectionModel().getSelectedItem();
            String id = r.get(2);
             if (purpose.equals("NONE") || department.equals("NONE")) {
                    JOptionPane.showMessageDialog(null, "Fill the blank");
                }
            try {
              if(type.equals(type)){
                   
                    total = quan - 1;
                    total1 = quan1 + 1;

                    String reques = "UPDATE `request_vehicle` SET"
                            + "`VEHICLE_ID`= '"+ID.getText()+"',"
                            + "`PLATE_NUMBER`='"+number.getText()+"',"
                            + "`TYPE_OF_VEHICLE`='"+type.getText()+"',"
                            + "`FUEL_CAPACITY`='"+fuel_capacity.getText()+"',"
                            + "`FUEL_TYPE`='"+fuel_type.getText()+"',"
                            + "`SEATER`='"+seaters.getText()+"',"
                            + "`STATUS`='APPROVED'"
                            + " WHERE `request_vehicle`.`request`='"+request.getValue()+"';";
                    db.execute(reques);
                    
                    String q = "UPDATE `available_vehicle` SET `availability`= 'RESERVED' ,`department`='"+department.getText()+"',`purpose`='"+purpose.getText()+"',`start`='"+start.getValue()+"',`end`='"+end.getValue()+"' WHERE `available_vehicle`.`vehicle_id`='"+ID.getText()+"';";
                    db.execute(q);
                    
                    JOptionPane.showMessageDialog(null, "Reservation Successfully!");

                    String y = "UPDATE vacant_vehicle SET quantity = '" + total + "'  WHERE type_of_vehicle ='" + type.getText() + "'";
                    db.execute(y);

                    String k = "UPDATE reserved_vehicle SET quantity = '" + total1 + "'  WHERE type_of_vehicle ='" + type.getText() + "'";
                    db.execute(k);

                    q = "SELECT type_of_vehicle, quantity FROM vacant_vehicle";
                    db.createBarChart("Available Vehicles", "2018 series", q, available);
                    q = "SELECT type_of_vehicle, quantity FROM reserved_vehicle";
                    db.createBarChart("Reserved Vehicles", "2018 series", q, reserved);
                    db.buildData("SELECT vehicle_id,plate_number,type_of_vehicle,fuel_capacity,fuel_type,seaters,availability,Department,purpose FROM available_vehicle", table4);
                    
                    number.setText(null);
                    ID.setText(null);
                    type.setText(null);
                    fuel_type.setText(null);
                    fuel_capacity.setText(null);
                    seaters.setText(null);
                    department.setText(null);
                    purpose.setText(null);
                    start.setValue(null);
                    end.setValue(null);
                    availability.setText(null);
 
                
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fill the blank");
            }
        });
      
    }    


    @FXML
    private void SAVE(ActionEvent event) {
        
        ObservableList<String> r = (ObservableList<String>) table1.getSelectionModel().getSelectedItem();
        String id = r.get(0);
        try {
            if (type1.equals(type1)) {
                total = quan + 1;
                String q = "UPDATE vacant_vehicle SET quantity = '" + total + "'  WHERE type_of_vehicle ='" + type1.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bustransit_master", "root", "");
                pst = con.prepareStatement(q);
                pst.execute();

                String sql = "INSERT INTO available_vehicle"
                        + "(vehicle_id, plate_number,TYPE_OF_VEHICLE,fuel_capacity,fuel_type,seaters,image)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, ID1.getText());
                pst.setString(2, number1.getText());
                pst.setString(3, type1.getText());
                pst.setString(4, fuel_capacity1.getText());
                pst.setString(5, fuel_type1.getText());
                pst.setString(6, seaters1.getText());
                fis = new FileInputStream(file);
                pst.setBinaryStream(7, (InputStream) fis, (int) file.length());
                pst.execute();

                JOptionPane.showMessageDialog(null, "New Vehicle, Saved Successfully!");

                String a = "DELETE FROM `new_vehicle` WHERE `new_vehicle`.`vehicle_id`='" + id + "'";
                db.execute(a);

                q = "SELECT type_of_vehicle, quantity FROM vacant_vehicle";
                db.createBarChart("Available Vehicles", "2018 series", q, available);
                q = "SELECT type_of_vehicle, quantity FROM reserved_vehicle";
                db.createBarChart("Reserved Vehicles", "2018 series", q, reserved);
                
                db.buildData("SELECT vehicle_id,plate_number,type_of_vehicle,fuel_capacity,fuel_type,seaters,availability,Department,purpose FROM available_vehicle", table4);
                db.buildData("SELECT * FROM new_vehicle", table1);
                number1.setText(null);
                ID1.setText(null);
                type1.setText(null);
                seaters1.setText(null);
                
                textarea.setText(null);
                imageview.setImage(null);
            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VehicleReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void browse(ActionEvent event) {
        
        filechooser = new FileChooser();
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        filechooser.setTitle("Open File Dialog");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        file = filechooser.showOpenDialog(stage);

        if (file != null) {
            textarea.setText(file.getAbsolutePath());
            Image image = new Image(file.toURI().toString(), 235, 192, true, true);
            imageview.setFitWidth(235);
            imageview.setFitHeight(192);
            imageview.setPreserveRatio(true);
            imageview.setImage(image);

        }
    }

    @FXML
    private void buttonsubmit(ActionEvent event) {
  try {
            if (type.equals(type)) {
                total = quan - 1;
                total1 = quan1 + 1;
                String q = "UPDATE vacant_vehicle SET quantity = '" +total + "'  WHERE type_of_vehicle ='" + type.getText() + "'";
                db.execute(q);
                
                String s = "UPDATE reserved_vehicle SET quantity = '" + total1 + "'  WHERE type_of_vehicle ='" + type.getText() + "'";
                db.execute(s);
                
                String sql = "UPDATE available_vehicle SET availability = 'RESERVED',department = '" + department.getText() + "',purpose = '" + purpose.getText() + "',start = '" + start.getValue() + "',end = '" + end.getValue() + "'  WHERE  vehicle_id='" + ID.getText() + "'";
                db.execute(sql);
                
                JOptionPane.showMessageDialog(null, "Reservation Saved Successfully!");
                q = "SELECT type_of_vehicle, quantity FROM vacant_vehicle";
                db.createBarChart("Available Vehicles", "2018 series", q, available);
                q = "SELECT type_of_vehicle, quantity FROM reserved_vehicle";
                db.createBarChart("Reserved Vehicles", "2018 series", q, reserved);
                
                db.buildData("SELECT vehicle_id,plate_number,type_of_vehicle,seaters,availability,Department,purpose FROM available_vehicle", table4);
                db.buildData("SELECT * FROM new_vehicle", table1);
               
            }}catch(Exception e){
                
            }             
    }
    

    @FXML
    private void DONE(ActionEvent event) {
                total = quan + 1;
                total1 = quan1 - 1;
                String q = "UPDATE vacant_vehicle SET quantity = '" +total + "'  WHERE type_of_vehicle ='" + type.getText() + "'";
                db.execute(q);
                
                String s = "UPDATE reserved_vehicle SET quantity = '" + total1 + "'  WHERE type_of_vehicle ='" + type.getText() + "'";
                db.execute(s);
                
                String sql = "UPDATE available_vehicle SET availability = 'AVAILABLE',department = 'NONE',purpose = 'NONE',start = '2018-01-01',end = '2018-01-01'  WHERE  vehicle_id='" + ID.getText() + "'";
                db.execute(sql);
                
                JOptionPane.showMessageDialog(null, "Done");
                q = "SELECT type_of_vehicle, quantity FROM vacant_vehicle";
                db.createBarChart("Available Vehicles", "2018 series", q, available);
                q = "SELECT type_of_vehicle, quantity FROM reserved_vehicle";
                db.createBarChart("Reserved Vehicles", "2018 series", q, reserved);
                
                db.buildData("SELECT vehicle_id,plate_number,type_of_vehicle,fuel_capacity,fuel_type,seaters,availability,Department,purpose FROM available_vehicle", table4);
                db.buildData("SELECT * FROM new_vehicle", table1);
               
        
    }

    @FXML
    private void request(ActionEvent event) {
            table4.getSelectionModel().getSelectedItem();
            rs = db.displayRecords("SELECT message FROM request_vehicle WHERE request = '" + request.getValue() + "'");
            try {
                while (rs.next()) {
                    try {

                        area.setText(rs.getString("message"));
                       
                        
                    } catch (Exception e) {
                    }
                }
            } catch (SQLException ex) {

            }
    }
    
}
    