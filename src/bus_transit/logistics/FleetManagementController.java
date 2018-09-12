/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.logistics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.HeadlessException;
import java.awt.Image;
import static java.awt.PageAttributes.MediaType.C;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.C;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;
import javax.management.Notification;
import javax.swing.JOptionPane;
import bus_transit.DashboardController;
import org.controlsfx.control.Notifications;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class FleetManagementController implements Initializable {

    @FXML
    private BorderPane container;

    DBUtilities dp = new DBUtilities();

    @FXML
    private JFXTextArea text_area;

    @FXML
    private JFXTextField vehicle_id;
    @FXML
    private JFXTextField plate_number;
    @FXML
    private JFXTextField capacity;
    @FXML
    private JFXTextField status;

    private String id;

    private ResultSet rs;
    @FXML
    private JFXTextField tov;
    @FXML
    private TableView<?> tbl_ds;

    @FXML
    private JFXDatePicker startdate;
    @FXML
    private JFXDatePicker enddates;
    @FXML
    private JFXButton ds;
    @FXML
    private TableView<?> tbl_monitoring;
    @FXML
    private JFXTextField alter1;
    @FXML
    private JFXTextField alter2;
    @FXML
    private JFXTextField alter3;
    @FXML
    private JFXTextField alter4;
    @FXML
    private JFXTextField alter5;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXTextField LF;
    @FXML
    private JFXTextField LT;
    @FXML
    private JFXTextField search_monitoring;

    Connection con = null;
    PreparedStatement pst = null;
    @FXML
    private JFXTextField STR;
    @FXML
    private JFXTextField FLTYP;
    @FXML
    private JFXDatePicker timepicker;

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
        dp.buildData("Select VEHICLE_ID, PLATE_NUMBER, TYPE_OF_VEHICLE,FUEL_CAPACITY,SEATER,FUEL_TYPE,STATUS from request_vehicle", tbl_ds);
        dp.buildData("Select VEHICLE_ID,"
                + " PLATE_NUMBER,"
                + " TYPE_OF_VEHICLE,"
                + "FUEL_CAPACITY,"
                + "SEATER,"
                + "FUEL_TYPE,"
                + "STATUS,"
                + "LOCATION_FROM,"
                + "LOCATION_TO,"
                + "ALTERNATIVE_ROUTES1,"
                + "ALTERNATIVE_ROUTES2,"
                + "ALTERNATIVE_ROUTES3,"
                + "ALTERNATIVE_ROUTES4,"
                + "ALTERNATIVE_ROUTES5,"
                + "STARTDATE,ENDDATE from monitoring", tbl_monitoring);

    }

    @FXML
    private void request(ActionEvent event) {
        stackpane.toFront();

        Label header = new Label("REQUEST");
        header.setFont(new Font("SansSerif", 14));

        Label body = new Label("Are you sure you want to request?");
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
            String q = "INSERT INTO request_vehicle (MESSAGE) "
                    + "VALUES ('"
                    + text_area.getText() + "')";

            dp.execute(q);
            dp.buildData("Select VEHICLE_ID,"
                    + " PLATE_NUMBER, "
                    + "TYPE_OF_VEHICLE,"
                    + "FUEL_CAPACITY,"
                    + "SEATER,FUEL_TYPE,"
                    + "STATUS from request_vehicle", tbl_ds);
            text_area.setText(null);

            Notifications notifications = Notifications.create()
                    .title("REQUEST")
                    .text("REQUEST DONE")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("CLICK ME");
                        }
                    });
            notifications.darkStyle();
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
// END .......................................................................................................................

    @FXML
    private void clicmoto(MouseEvent event) {
        ObservableList<String> rf = (ObservableList<String>) tbl_ds.getSelectionModel().getSelectedItem();
        id = rf.get(0);
        rs = dp.displayRecords("Select * from request_vehicle where VEHICLE_ID ='" + id + "'");
        try {
            if (rs.next()) {
                vehicle_id.setText(rs.getString("VEHICLE_ID"));
                plate_number.setText(rs.getString("PLATE_NUMBER"));
                tov.setText(rs.getString("TYPE_OF_VEHICLE"));
                capacity.setText(rs.getString("FUEL_CAPACITY"));
                STR.setText(rs.getString("SEATER"));
                FLTYP.setText(rs.getString("FUEL_TYPE"));
                status.setText(rs.getString("STATUS"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(FleetManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // END .............................................................................................................

    @FXML
    private void ds_save(ActionEvent event) {
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
            String q = "INSERT INTO monitoring (VEHICLE_ID, "
                    + "PLATE_NUMBER,"
                    + " TYPE_OF_VEHICLE,"
                    + "FUEL_CAPACITY,"
                    + "SEATER,"
                    + "FUEL_TYPE,"
                    + " STATUS,"
                    + "LOCATION_FROM,"
                    + "LOCATION_TO,"
                    + "ALTERNATIVE_ROUTES1,"
                    + "ALTERNATIVE_ROUTES2,"
                    + "ALTERNATIVE_ROUTES3,"
                    + "ALTERNATIVE_ROUTES4,"
                    + "ALTERNATIVE_ROUTES5,"
                    + "STARTDATE,ENDDATE) "
                    + "VALUES ('"
                    + vehicle_id.getText() + "', '"
                    + plate_number.getText() + "', '"
                    + tov.getText() + "', '"
                    + capacity.getText() + "', '"
                    + STR.getText() + "', '"
                    + FLTYP.getText() + "', '"
                    + status.getText() + "', '"
                    + LF.getText() + "', '"
                    + LT.getText() + "', '"
                    + alter1.getText() + "', '"
                    + alter2.getText() + "', '"
                    + alter3.getText() + "', '"
                    + alter4.getText() + "', '"
                    + alter5.getText() + "', '"
                    + startdate.getValue() + "','"
                    + enddates.getValue() + "')";
            dp.execute(q);
            dp.buildData("Select VEHICLE_ID, PLATE_NUMBER, TYPE_OF_VEHICLE,FUEL_CAPACITY,SEATER,FUEL_TYPE,STATUS,", tbl_ds);

            String a = "DELETE from request_vehicle where request_vehicle.VEHICLE_ID='" + id + "'";
            dp.execute(a);

            dp.buildData("Select VEHICLE_ID, PLATE_NUMBER, TYPE_OF_VEHICLE,FUEL_CAPACITY,SEATER,FUEL_TYPE,STATUS,"
                    + "LOCATION_FROM,"
                    + "LOCATION_TO,"
                    + "ALTERNATIVE_ROUTES1,"
                    + "ALTERNATIVE_ROUTES2,"
                    + "ALTERNATIVE_ROUTES3,"
                    + "ALTERNATIVE_ROUTES4,"
                    + "ALTERNATIVE_ROUTES5,"
                    + "STARTDATE,ENDDATE from monitoring", tbl_monitoring);

            vehicle_id.setText(null);
            plate_number.setText(null);
            tov.setText(null);
            capacity.setText(null);
            STR.setText(null);
            FLTYP.setText(null);
            status.setText(null);
            LF.setText(null);
            LT.setText(null);
            alter1.setText(null);
            alter2.setText(null);
            alter3.setText(null);
            alter4.setText(null);
            alter5.setText(null);
            startdate.setValue(null);
            enddates.setValue(null);
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
    private void search_mon(KeyEvent event) {
        tbl_monitoring.getItems().removeAll(tbl_monitoring.getItems());
        dp.buildData("Select VEHICLE_ID,"
                + " PLATE_NUMBER,"
                + " TYPE_OF_VEHICLE,"
                + "FUEL_CAPACITY,"
                + "SEATER,"
                + "FUEL_TYPE,"
                + "STATUS,"
                + "LOCATION_FROM,"
                + "LOCATION_TO,"
                + "ALTERNATIVE_ROUTES1,"
                + "ALTERNATIVE_ROUTES2,"
                + "ALTERNATIVE_ROUTES3,"
                + "ALTERNATIVE_ROUTES4,"
                + "ALTERNATIVE_ROUTES5,"
                + "STARTDATE,ENDDATE from monitoring "
                + "WHERE VEHICLE_ID LIKE '%"+search_monitoring.getText()+"%'", tbl_monitoring);
    }

}
