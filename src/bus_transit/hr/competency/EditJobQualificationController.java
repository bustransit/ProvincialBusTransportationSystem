/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.competency;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class EditJobQualificationController extends Application implements Initializable {

    @FXML
    private JFXTabPane tbJobDetails;
    @FXML
    private Tab tbQualifications;
    @FXML
    private JFXButton btnAddToQualification;
    @FXML
    private Tab tbResponsibilities;
    @FXML
    private JFXButton btnAddToResponsibilities;
    @FXML
    private Tab tbBenefits;
    @FXML
    private JFXButton btnAddToBenefits;

    /**
     * @return the departmentCode
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    /**
     * @param departmentCode the departmentCode to set
     */
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    /**
     * @return the positionCode
     */
    public String getPositionCode() {
        return positionCode;
    }

    /**
     * @param positionCode the positionCode to set
     */
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
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
     * @return the position_name
     */
    public String getPosition_name() {
        return position_name;
    }

    /**
     * @param position_name the position_name to set
     */
    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    /**
     * @return the positionId
     */
    public String getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the salaryGrade
     */
    public int getSalaryGrade() {
        return salaryGrade;
    }

    /**
     * @param salaryGrade the salaryGrade to set
     */
    public void setSalaryGrade(int salaryGrade) {
        this.salaryGrade = salaryGrade;
    }

    /**
     * @return the manpower
     */
    public int getManpower() {
        return manpower;
    }

    /**
     * @param manpower the manpower to set
     */
    public void setManpower(int manpower) {
        this.manpower = manpower;
    }

    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnText;
    @FXML
    private VBox vbxJobInfo;
    @FXML
    private VBox vbxResponsibilities;
    @FXML
    private VBox vbxQualification;
    @FXML
    private VBox vbxBenefits;
    @FXML
    private JFXComboBox<?> cbSalaryGrade;

    /**
     * @return the btnSave
     */
    public JFXButton getBtnSave() {
        return btnSave;
    }

    /**
     * @param btnSave the btnSave to set
     */
    public void setBtnSave(JFXButton btnSave) {
        this.btnSave = btnSave;
    }
    DBUtilities db = new DBUtilities();
    ResultSet rs;
    
    @FXML
    private TextField txtJobTitle;
    @FXML
    private TextField txtVacancies;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextArea txtQualification;
    public static JFXButton btnSave;
    @FXML
    private TextArea txtResponsibilities;
    @FXML
    private TextArea txtBenefits;
    @FXML
    private JFXComboBox<String> cbDepartment;

    String departmentCode = null;
    String positionCode = null;
    String description = null;
    String position_name = null;
    String positionId = null;
    int salaryGrade = 0;
    int manpower = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        vbxBenefits.getChildren().clear();
        vbxResponsibilities.getChildren().clear();
        vbxQualification.getChildren().clear();
        db.populateComboBox("SELECT UPPER(CONCAT(department_name,'(', department_code,')')) FROM department", cbDepartment);
        cbDepartment.getSelectionModel().selectFirst();
        db.populateComboBox("SELECT rec_id FROM salary_grade", cbSalaryGrade);
        
        //txtJobTitle = TextFields.createClearableTextField();        
//        // replace all textfield into clearable textfield
//        vbxJobInfo.getChildren().forEach((t) -> {
//            if(t instanceof TextField){
//                TextField b = TextFields.createClearableTextField();
//                b.setId(t.getId());    
//                int i = vbxJobInfo.getChildren().indexOf(t);
//                vbxJobInfo.getChildren().remove(t);
//                vbxJobInfo.getChildren().add(i, b);
//                b.requestFocus();                
//            }
//        });
                        
        txtJobTitle.setOnKeyReleased((event) -> {           
            TextField node = (TextField) event.getSource();
            String[] p = node.getText().trim().split("\\s");
            positionCode = "";   
            try{
                for (String i : p) {
                    this.positionCode = positionCode.concat(i.charAt(0)+"").toUpperCase();
                }                
                System.out.println("Employee code: "+positionCode);
            }catch(java.lang.StringIndexOutOfBoundsException e){
                System.out.println(e);
            }                      
        });
        
        txtVacancies.setOnKeyReleased((event) -> {
            TextField node = (TextField) event.getSource();          
            if(!(node.getText().trim().isEmpty())){
                setManpower(Integer.parseInt(txtVacancies.getText()));
                System.out.println("Vacancies: "+txtVacancies.getText());                
            }
        });
        
        cbSalaryGrade.setOnAction((event) -> {
            setSalaryGrade(Integer.parseInt(cbSalaryGrade.getSelectionModel().getSelectedItem().toString()));
            System.out.println(getSalaryGrade());
        });
        
        cbDepartment.setOnAction((event) -> {             
            Matcher m = Pattern.compile("\\(([^)]+)\\)")
                               .matcher(cbDepartment.getSelectionModel().getSelectedItem().toLowerCase());
            while(m.find()){
                System.out.println(m.group(1));
            }
            setDepartmentCode(cbDepartment.getSelectionModel().getSelectedItem().toString());            
        });
        
        txtDescription.setOnKeyReleased((event) -> {
            if(!txtDescription.getText().trim().isEmpty()){
                setDescription(txtDescription.getText());
                System.out.println(txtDescription.getText());                
            }
        });                
    }    


    
    public void save(){                   
        try {            
            String q = "INSERT INTO position "+
                       "(position_code, departement_code, description, n_of_manpower, position_name, salary_grade) "+
                       "VALUES('"+positionCode+
                       "','"+departmentCode+
                       "','"+txtDescription.getText()+
                       "','"+txtVacancies.getText()+
                       "','"+txtJobTitle.getText()+
                       "','"+cbSalaryGrade.getSelectionModel().getSelectedItem().toString()+"')";                  
            db.execute(q);
            
            q = "SELECT MAX(rec_id) as 'position_id' FROM position";
            
            rs = db.displayRecords(q);            
            
            if(rs.next()){
                positionId = rs.getString("position_id");
                System.out.println("positionId");
                
                vbxQualification.getChildren().forEach((t) -> {
                    String s = vbxQualification.getAccessibleText();
                    if(t instanceof JFXButton){
                        String txt = ((JFXButton) t).getText();                        
                        String qry = "INSERT INTO position_qualification ("+s+", position_code) "+
                                   "VALUES ('"+txt+"', '"+positionCode+"')";
                        db.execute(qry);
                    }
                });
                
                vbxBenefits.getChildren().forEach((t) -> {
                    String s = vbxBenefits.getAccessibleText();
                    if(t instanceof JFXButton){
                        String txt = ((JFXButton) t).getText();                        
                        String qry = "INSERT INTO position_benefits ("+s+", position_code) "+
                                   "VALUES ('"+txt+"', '"+positionCode+"')";
                        db.execute(qry);
                    }
                });
                
                vbxResponsibilities.getChildren().forEach((t) -> {
                    String s = vbxResponsibilities.getAccessibleText(); 
                    if(t instanceof JFXButton){
                        String txt = ((JFXButton) t).getText();                        
                        String qry = "INSERT INTO position_responsibilities ("+s+", position_code) "+
                                   "VALUES ('"+txt+"', '"+positionCode+"')";
                        db.execute(qry);
                    }
                });                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EditJobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex){
            Logger.getLogger(EditJobQualificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
      @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("NewJobQualification.fxml"));        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();        
    }   
       
    public static void main(String[] args) {        
        launch(args);
    }       

   
//    @FXML
//    private void setClearableText(Event event) {
//        VBox vbx = vbxJobInfo;
//        int i = vbx.getChildren().indexOf(event.getSource());
//        Node node = (Node) event.getSource();
//        if(node instanceof TextField){
//            vbx.getChildren().remove(i);
//            TextField b = TextFields.createClearableTextField();
//            b.setId(node.getId());            
//            vbx.getChildren().add(i, b);
//            b.requestFocus();
//        }
//    }
    
    @FXML
    private void setDepartment(ActionEvent event) {
        setDepartmentCode((String) cbDepartment.getSelectionModel().getSelectedItem());        
    }

    @FXML
    private void setSalaryCategory(ActionEvent event) {
    }

        
    /**
     * Function that create list item
     * @param txt
     * @param fromTextArea
     * @param toTextFlow
     * @return JFXButton with text and icon
     */
    private JFXButton createListItem(TextArea fromTextArea, VBox toTextFlow){                
        JFXButton b = null;
        try{
            if(!fromTextArea.getText().trim().isEmpty()){
                String txt = fromTextArea.getText();
                // List item icon style
                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CHECKBOX_BLANK_CIRCLE);
                icon.setSize("10");
                icon.setFill(Color.GREY);

                // Button Style
                b = new JFXButton(txt,icon);
                b.setCursor(Cursor.HAND);
                b.setStyle("-fx-background-color: white");
                b.setButtonType(JFXButton.ButtonType.RAISED);
                double w = 400;
                b.setMinWidth(w);
                b.setMaxWidth(w);
                b.setPrefWidth(w);
                b.setWrapText(true);
                b.setAlignment(Pos.CENTER_LEFT);

                b.setOnMouseClicked((bEvt) -> {                
                    if(bEvt.getClickCount() == 2){
                        toTextFlow.getChildren().remove(bEvt.getSource());
                        fromTextArea.setText(null);
                    }                
                    fromTextArea.setText(((JFXButton) bEvt.getSource()).getText());
                });
                toTextFlow.getChildren().add(b);
                fromTextArea.setText(null);                          
            }             
        } catch(NullPointerException e){
            System.out.println(e);
        }
        return b;
    }
    
    @FXML
    private void addToList(ActionEvent event) {                                
        String listOf = ((JFXButton) event.getSource()).getAccessibleText().toLowerCase();
        TextArea from = null;
        VBox to = null;
                        
        if(listOf.equals("qualifications")){
            System.out.println(listOf);
            from = txtQualification;
            to = vbxQualification;
        }
        
        if(listOf.equals("responsibilities")){
            System.out.println(listOf);
            from = txtResponsibilities;
            to = vbxResponsibilities;
        }
                
        if(listOf.equals("benefits")){
            System.out.println(listOf);
            from = txtBenefits;
            to = vbxBenefits;
        }
        createListItem(from, to);
    }         

    @FXML
    private void iconClicked(MouseEvent event) {
    }

    @FXML
    private void editJobQualification(ActionEvent event) {
    }

    @FXML
    private void keyPressed(KeyEvent event) {
    }
}