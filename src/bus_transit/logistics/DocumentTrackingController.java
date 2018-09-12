/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.logistics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import bus_transit.DashboardController;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class DocumentTrackingController implements Initializable {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    DBUtilities db = new DBUtilities();
    @FXML
    private BorderPane container;
    @FXML
    private JFXTextField name_of_documents;
    @FXML
    private JFXComboBox<String> document_type;
    @FXML
    private JFXTextField storage;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<String> status;
    @FXML
    private JFXTextField reference;
    @FXML
    private JFXTextField name_of_document1;
    @FXML
    private JFXTextField document_type1;
    @FXML
    private JFXTextField storage1;
    @FXML
    private JFXTextField date1;
    @FXML
    private JFXTextField status1;
    @FXML
    private TableView<?> table1;
    @FXML
    private JFXTextField encoder;
    @FXML
    private Label user;
    @FXML
    private JFXButton update;
    @FXML
    private JFXTextField reuquested;
    @FXML
    private JFXTextField department;
    @FXML
    private JFXTextField date_of_request;
    @FXML
    private TableView<?> table2;
    @FXML
    private JFXTextField reference2;
    @FXML
    private JFXTextField name_of_document2;
    @FXML
    private JFXTextField document_type2;
    @FXML
    private JFXTextField storage2;
    @FXML
    private JFXTextField date2;
    @FXML
    private JFXTextField status2;
    @FXML
    private JFXTextField encoder2;
    @FXML
    private JFXTextField reuquested2;
    @FXML
    private JFXTextField department2;
    @FXML
    private JFXDatePicker date_of_request2;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db.buildData("SELECT * FROM registration", table1);
        db.buildData("SELECT * FROM registration", table2);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(DashboardController.root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        
        document_type.getItems().addAll(
                "INVOICE RECEIPT",
                "OFFICIAL RECEIPT",
                "PURCHASE ORDER",
                "OTHER"
        );
        
        status.getItems().addAll(
                "APPROVED",
                "DISAPPROVED",
                "PENDING"
        );
    }

    @FXML
    private void save(ActionEvent event) {

        try {
            String sql = "INSERT INTO registration"
                    + "(name_of_document,document_type,storage,status,date)"
                    + "VALUES ( ?, ?, ?, ?, '" + date.getValue() + "')";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bustransit_master", "root", "");
            pst = con.prepareStatement(sql);
            pst.setString(1, name_of_documents.getText());
            pst.setString(2, document_type.getValue());
            pst.setString(3, storage.getText());
            pst.setString(4, status.getValue());

            pst.execute();

            JOptionPane.showMessageDialog(null, "New Document saved Successfully!");
            db.buildData("SELECT * FROM registration", table1);

        } catch (Exception e) {
        }

    }

    @FXML
    private void update(ActionEvent event) {
        String q = "UPDATE registration SET name_of_document = '" + name_of_document1.getText() + "',document_type = '" + document_type1.getText() + "',storage = '" + storage1.getText() + "',status = '" + status1.getText() + "',date = '" + date1.getText() + "' WHERE reference_no ='" + reference.getText() + "'";
        db.execute(q);
        update.setDisable(true);
        name_of_document1.setEditable(false);
        document_type1.setEditable(false);
        storage1.setEditable(false);
        status1.setEditable(false);
        date1.setEditable(true);
        JOptionPane.showMessageDialog(null, "Document Edit Saved Successfully!");
        db.buildData("SELECT * FROM registration", table1);
    }

    @FXML
    private void table1(MouseEvent event) {

        ObservableList<String> r = (ObservableList<String>) table1.getSelectionModel().getSelectedItem();
        String id = r.get(0);

        rs = db.displayRecords("SELECT * FROM registration WHERE reference_no = '" + id + "'");
        try {
            while (rs.next()) {
                try {
                    reference.setText(rs.getString("reference_no"));
                    name_of_document1.setText(rs.getString("name_of_document"));
                    document_type1.setText(rs.getString("document_type"));
                    storage1.setText(rs.getString("storage"));
                    status1.setText(rs.getString("status"));
                    date1.setText(rs.getString("date"));
                    encoder.setText(rs.getString("encoder"));
                    reuquested.setText(rs.getString("requested_by"));
                    department.setText(rs.getString("department"));
                    date_of_request.setText(rs.getString("date_of_request"));
                } catch (Exception e) {
                }

            }
        } catch (SQLException ex) {

        }
    }

    @FXML
    private void edit(ActionEvent event) {
        update.setDisable(false);
        name_of_document1.setEditable(true);
        document_type1.setEditable(true);
        storage1.setEditable(true);
        status1.setEditable(true);
        date1.setEditable(true);

    }
    @FXML
    private void table2(MouseEvent event) {
        ObservableList<String> r = (ObservableList<String>) table2.getSelectionModel().getSelectedItem();
        String id = r.get(0);
        rs = db.displayRecords("SELECT * FROM registration WHERE reference_no = '" + id + "'");
        try {
            while (rs.next()) {
                try {
                    reference2.setText(rs.getString("reference_no"));
                    name_of_document2.setText(rs.getString("name_of_document"));
                    document_type2.setText(rs.getString("document_type"));
                    storage2.setText(rs.getString("storage"));
                    status2.setText(rs.getString("status"));
                    date2.setText(rs.getString("date"));
                    encoder2.setText(rs.getString("encoder"));
                    reuquested2.setText(rs.getString("requested_by"));
                    department2.setText(rs.getString("department"));
                    date_of_request2.setValue(rs.getDate("date_of_request").toLocalDate());

                } catch (Exception e) {
                }

            }
        } catch (SQLException ex) {

        }
    }

    @FXML
    private void submit(ActionEvent event) {
        String q = "UPDATE registration SET requested_by = '" + reuquested2.getText() + "',derpartment = '" + department2.getText() + "',date_of_request = '" + date_of_request2.getValue() + "' WHERE reference_no ='" + reference2.getText() + "'";
        db.execute(q);
        reference2.setText(null);
        name_of_document2.setText(null);
        document_type2.setText(null);
        storage2.setText(null);
        date2.setText(null);
        status2.setText(null);
        encoder2.setText(null);
        reuquested2.setText(null);
        department2.setText(null);
        date_of_request2.setValue(null);
        JOptionPane.showMessageDialog(null, "Request Document Saved Successfully!");
        db.buildData("SELECT * FROM registration", table1);
        db.buildData("SELECT * FROM registration", table2);   
        
    }

}
