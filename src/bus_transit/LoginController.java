/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author Llamera
 */
public class LoginController implements Initializable {

    @FXML
    private Label txt_time;
    @FXML
    private Label txt_date;
    @FXML
    private JFXButton btn_close;
    @FXML
    private CustomTextField txt_username;
    @FXML
    private CustomPasswordField txt_password;
    @FXML
    private JFXButton btn_signin;
    @FXML
    private Label btn_forgotPassword;
    @FXML
    private StackPane stackpane;

    private int hour;
    private int minute;
    private int second;
    private int am_pm;
    private int month;
    private int day;
    private int year;

    public String username;
    public String password;
    public String userLevel;
    public String positionID;
    public String empID;
    public String dept;
    
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;
    SidePaneController sidePane = new SidePaneController();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TimeDate();
    }

    @FXML
    private void Close(ActionEvent event) {
        // Shows dialog of close button is clicked
        stackpane.toFront();

        btn_signin.setDefaultButton(false);
        btn_close.setCancelButton(false);

        Label header = new Label("Exit Program?");
        header.setFont(new Font("SansSerif", 12));

        Label body = new Label("Are you sure you want to exit?");
        body.setFont(new Font("SansSerif", 14));

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(header);
        layout.setBody(body);
        layout.setPrefSize(300, 150);

        JFXDialog dialog = new JFXDialog(stackpane, layout, JFXDialog.DialogTransition.LEFT);
        dialog.setOverlayClose(false);

        // If YES, the program will close
        JFXButton btn_yes = new JFXButton("Yes");
        btn_yes.setButtonType(JFXButton.ButtonType.RAISED);
        btn_yes.setPrefSize(75, 26);
        btn_yes.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
        btn_yes.setDefaultButton(true);
        btn_yes.setOnAction((evt) -> {
            dialog.close();
            Stage stage = (Stage) btn_yes.getScene().getWindow();
            stage.close();
        });

        // If No, message dialog will close
        JFXButton btn_cancel = new JFXButton("Cancel");
        btn_cancel.setButtonType(JFXButton.ButtonType.RAISED);
        btn_cancel.setPrefSize(75, 26);
        btn_cancel.setCancelButton(true);
        btn_cancel.setOnAction((evt) -> {
            btn_signin.setDefaultButton(true);
            btn_close.setCancelButton(true);
            dialog.close();
            stackpane.toBack();
        });

        layout.setActions(btn_cancel, btn_yes);
        dialog.show();
    }

    @FXML
    private void SignIn(ActionEvent event) throws IOException {       
        username = txt_username.getText();
        password = txt_password.getText();
        
//        q = "SELECT user.emp_id, "
//                + "user.username, "
//                + "user.password,"
//                + "user.department_code, "
//                + "employee.position_id,"
//                + "employee_position.position_level "
//                + "FROM user,employee, employee_position "
//                + "WHERE user.emp_id = employee.emp_id "
//                + "AND employee.position_id = employee_position.position_id "
//                + "ANd user.username = '"+username+"' "
//                + "AND user.password = '"+password+"'";

        q = "SELECT user.*, "
                + "employee.*, "
                + "employee_position.* "
                + "FROM user, employee, employee_position "
                + "WHERE user.username = '"+username+"' "
                + "AND user.password='"+password+"' "
                + "AND user.emp_id = employee.emp_id "
                + "GROUP by user.emp_id";

        rs = db.displayRecords(q);
        
        try {
            if(rs.next()){
                
                userLevel = rs.getString("position_level");                
                empID = rs.getString("emp_id");
                positionID = rs.getString("position_id"); 
                dept = rs.getString("department_code");
                
                sidePane.department = dept;
                sidePane.userLevel = userLevel;
                
                filterUser(dept, userLevel);
                
                Stage stage = (Stage) btn_signin.getScene().getWindow();
                stage.close();
                Stage dash = new Stage(StageStyle.UNDECORATED);
                Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                Scene scene = new Scene(root);
                dash.setScene(scene);
                dash.setMaximized(true);
                dash.show();                                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    } // sign in end here
    
    private void filterUser(String dept, String usrLvl){
        /**
         * User level to be filtered
         * staff
         * supervisor
         * managerial
         * board
         * system_admin
         */
        
        /**
         * Department code to filtered
         * admin
         * it
         * hr
         * logistics
         * finance
         * core
         */

        // system_admin
        if (dept.toLowerCase().equals("system_admin")) {            
            
        }         
        
        // Logisitics
        if (dept.toLowerCase().equals("logistics")) {            
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }            
            if (usrLvl.equals("managerial")) {

            }            
        }  
        // hr
        if (dept.toLowerCase().equals("hr")) {            
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }            
            if (usrLvl.equals("managerial")) {

            }            
        }
        // finance
        if (dept.toLowerCase().equals("finance")) {            
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }            
            if (usrLvl.equals("managerial")) {

            }            
        }
        // finance
        if (dept.toLowerCase().equals("core")) {            
            if (usrLvl.equals("staff")) {

            }
            if (usrLvl.equals("supervisor")) {

            }            
            if (usrLvl.equals("managerial")) {

            }            
        }  
        
        System.out.println();
        
    } // filterUser end here
     

    @FXML
    private void ForgotPassword(MouseEvent event) throws IOException {
        Stage stage = (Stage) btn_signin.getScene().getWindow();
        stage.close();
        Stage dash = new Stage(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("system/ForgotPassword.fxml"));
        Scene scene = new Scene(root);
        dash.setScene(scene);
        dash.setMaximized(true);
        dash.show();        
    }

    private void TimeDate() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR);
            minute = cal.get(Calendar.MINUTE);
            second = cal.get(Calendar.SECOND);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DATE);
            year = cal.get(Calendar.YEAR);
            am_pm = cal.get(Calendar.AM_PM);

            String date = month + "-" + day + "-" + year;
            if (month == 0) {
                date = ("January " + day + ", " + year);
            } else if (month == 1) {
                date = ("Febuary " + day + ", " + year);
            } else if (month == 2) {
                date = ("March " + day + ", " + year);
            } else if (month == 3) {
                date = ("April " + day + ", " + year);
            } else if (month == 4) {
                date = ("May " + day + ", " + year);
            } else if (month == 5) {
                date = ("June " + day + ", " + year);
            } else if (month == 6) {
                date = ("July " + day + ", " + year);
            } else if (month == 7) {
                date = ("August " + day + ", " + year);
            } else if (month == 8) {
                date = ("September " + day + ", " + year);
            } else if (month == 9) {
                date = ("October " + day + ", " + year);
            } else if (month == 10) {
                date = ("November " + day + ", " + year);
            } else if (month == 11) {
                date = ("December " + day + ", " + year);
            }

            String day_night = "";
            if (am_pm == 1) {
                day_night = "PM";
            } else {
                day_night = "AM";
            }

            if (hour == 0) {
                txt_time.setText("12" + ":" + minute + ":" + second + " " + day_night);
                txt_date.setText(date);
            } else {
                txt_time.setText(hour + ":" + minute + ":" + second + " " + day_night);
                txt_date.setText(date);
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
