/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.system;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class UserManagementController extends
             Application implements
             Initializable {

    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String qry;

    @FXML private JFXTextField tfEmpCode;
    @FXML private JFXTextField tfUsername;
    @FXML private JFXButton btnAddUser;
    @FXML private JFXPasswordField pfPassword;
    @FXML private TableView<?> tblUser;
    @FXML private VBox newUser;
    @FXML private JFXButton btnAddNewUser;
    @FXML private VBox editUserInfo;
    @FXML private JFXTextField tfEmpCode1;
    @FXML private JFXTextField tfUsername1;
    @FXML private JFXPasswordField pfPassword1;
    @FXML private JFXButton btnAddUser1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qry = "SELECT emp_id, username FROM user";
        
        btnAddNewUser.setOnAction(e->{
            BooleanProperty b = newUser.visibleProperty();
            if(b.getValue()){
                newUser.setVisible(false);                
                System.out.println(b.getValue());
            }else{
                newUser.setVisible(true);
                System.out.println(b.getValue());
            }                        
        });
        
        db.buildData(qry, tblUser);
        
        btnAddUser.setOnAction(e -> {
            addUser(tfEmpCode.getText(),                    
                    tfUsername.getText(),
                    pfPassword.getText());
        });
        
        tblUser.setOnMouseClicked(ev->{
            int r = tblUser.getSelectionModel().getSelectedIndex();
            Object i = tblUser.getSelectionModel().getSelectedItem();
            System.out.println(i);                        
        });        
    }

    private void addUser(String e, String u, String p) {
        if (!(e.isEmpty() && u.isEmpty() && p.isEmpty())) {

            qry = "select emp_id from user where emp_id=" + e;

            rs = db.displayRecords(qry);

            try {
                if (!rs.next()) {
                    qry = "INSERT INTO "
                            + "`user` (`emp_id`, `username`, `password`) "
                            + "VALUES ('" + e + "', '" + u + "', '" + p + "')";

                    db.execute(qry);

                    JOptionPane.showMessageDialog(null, 
                            "Successfully added new user");
                }else{
                    JOptionPane.showMessageDialog(null, 
                            "System User Already Registered!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserManagementController
                        .class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass()
                                .getResource("UserManagement.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}