/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class TestController implements Initializable {
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    String q;
    
    @FXML private JFXButton btnEdit;
    @FXML private JFXButton btnView;
    @FXML private JFXButton btnPrint;
    @FXML private FlowPane flpTest;
    @FXML private Label lblTitle;
    @FXML private Text txtDescription;
    @FXML private Label lblDate;    

    private String title;
    private static String description;
    private static String lastUpdate;
    public static String testId;
    private final String id = testId;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        loadTest();
    }    

    public void loadTest(){  
        System.out.println(id);
        System.out.println(this.testId);
        System.out.println(getTestId());
        
        System.out.println("From loadTest:"+id);        
        String q = "SELECT * FROM test WHERE test_id="+id;
        rs = db.displayRecords(q);
        try {
            if(rs.next()){
                lblTitle.setText(rs.getString("test_name"));
                lblDate.setText(rs.getString("last_update"));
                txtDescription.setText(rs.getString("description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the testId
     */
    public String getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public void setTestId(String testId) {
        this.testId = testId;
    }

    @FXML
    private void toHoverState(MouseEvent event) {
    }

    /**
     * @return the lblTitle
     */
    public Label getLblTitle() {
        return lblTitle;
    }

    /**
     * @param lblTitle the lblTitle to set
     */
    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    /**
     * @return the txtDescription
     */
    public Text getTxtDescription() {
        return txtDescription;
    }

    /**
     * @param txtDescription the txtDescription to set
     */
    public void setTxtDescription(Text txtDescription) {
        this.txtDescription = txtDescription;
    }

    /**
     * @return the lblDate
     */
    public Label getLblDate() {
        return lblDate;
    }

    /**
     * @param lblDate the lblDate to set
     */
    public void setLblDate(Label lblDate) {
        this.lblDate = lblDate;
    }    
    
    @FXML
    private void runTest(ActionEvent event) throws IOException {
        JFXButton b = (JFXButton) event.getSource();
        String file = b.getAccessibleText().toString();       
        Parent root;        
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource(file));
            TestViewerController testViewerController = new TestViewerController();
            
            testViewerController.setTestId(id);
            
            System.out.println("From TestController");
            System.out.println(id);
            System.out.println(this.testId);
            System.out.println(getTestId());            
            
            root = l.load();
            
            l.setController(testViewerController);
            
            Stage stage = new Stage();            
            stage.setTitle("Test");
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.show();            
        }catch(IOException e){
            e.printStackTrace();
        }        
    } // runTest ends here    
}
