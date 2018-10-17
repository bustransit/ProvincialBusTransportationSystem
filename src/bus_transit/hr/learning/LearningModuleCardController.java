/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class LearningModuleCardController implements Initializable {

    @FXML
    private FlowPane modulePane;
    @FXML
    private Label lblTitle;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnView;
    @FXML
    private JFXButton btnView2;
    @FXML
    private JFXButton btnAttachFile;
    @FXML
    private Text txtDescription;
    @FXML
    private Label lblType;
    @FXML
    private Label lblLastUpdate;
    @FXML
    private VBox vbxAttachments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void loadFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        JFXButton b = (JFXButton) event.getSource();
        Stage s = (Stage) b.getScene().getWindow();
        
        File file = fileChooser.showOpenDialog(s);
            
        if(file != null){            
            attach(file.getAbsoluteFile());
        }                
    }    

    private void attach(File f){
        System.out.println(f.getAbsolutePath());
        System.out.println(f);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            FileInputStream fileData = new FileInputStream(f.getAbsolutePath());            
            String fileName = f.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
            System.out.println("Filename: "+fileName);    
            System.out.println("File extenstion: "+ext);                
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bustransit_master","root", "071325");            
            String s = "INSERT INTO learning_module_files (module_id,attachment, filename, extension_name) VALUES (?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(s);
            ps.setString(1, "1");
            ps.setBinaryStream(2, fileData,(int) f.length());
            ps.setString(3, fileName);
            ps.setString(4, ext);
            ps.executeUpdate();
            
            ps.close();
            fileData.close();
            con.close();
            
            getModules("1", fileName);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LearningModuleCardController.class.getName()).log(Level.SEVERE, null, ex);
        }               
    }    

    private void getModules(String id, String fName){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bustransit_master","root", "071325");
            

            String s = "SELECT attachment,filename FROM learning_module_files WHERE module_id="+id;
            PreparedStatement ps=con.prepareStatement(s); 
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                File file = new File("C:\\"+rs.getString("filename").toString()+"\\");
                file.setWritable(true);
                file.setExecutable(true);
                file.setReadable(true);
                FileOutputStream fos = new FileOutputStream(file);            
                byte b[];
                Blob blob;              
                blob=rs.getBlob("attachment");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);
                fos.close();
            }
            
            ps.close();
            
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    
}
