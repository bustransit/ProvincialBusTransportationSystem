/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr.learning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import utilities.DBUtilities;
import utilities.Validator;
import utilities.Notification;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class NewTestController extends Application implements Initializable {
    
    private DBUtilities db = new DBUtilities();
    private Test test;
    private Validator validator = new Validator();
    private Notification notif = new Notification();
    
    @FXML
    private AnchorPane container;
    @FXML
    private CustomTextField tfTitle;
    @FXML
    private TextArea taDescription;
    @FXML
    private JFXTabPane content;
    @FXML
    private CustomTextField tfNofParts;
    @FXML
    private ToggleButton btnOkParts;
    @FXML
    private VBox vbxTestParts;
    @FXML
    private JFXComboBox<String> cmbParts;

    
    @FXML
    private Tab testInfo;
    @FXML
    private AnchorPane heading11;
    @FXML
    private Tab testParts;
    @FXML
    private Tab testQuestionnaire;
    @FXML
    private JFXButton addQuestion;
    @FXML
    private StackPane stackpane;
    @FXML
    private VBox vbxQuestion;
    @FXML
    private JFXButton btnNewTest;        
    @FXML
    private CustomTextField tfDuration;
    @FXML
    private JFXButton btnPartBack;
    @FXML
    private JFXButton btnPartNext;
    @FXML
    private JFXButton btnQuestionBack;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSave;
    @FXML
    private ScrollPane scrpQuestions;
    @FXML
    private AnchorPane ancQuestion;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        tfNofParts.disableProperty().bind(btnOkParts.selectedProperty());
        vbxTestParts.disableProperty().bind(btnOkParts.selectedProperty());
        btnNewTest.setOnAction((event) -> {
            try{
                test = new Test();            
                test.testTitle = tfTitle.getText().trim();
                test.testDuration = Double.parseDouble(tfDuration.getText().trim()); 
                test.testDescription = taDescription.getText().trim();
                
                content.getSelectionModel().selectNext();
            }catch(Exception e){
                
            }
        });
        
        btnPartNext.setOnAction((event) -> {         
            vbxTestParts.getChildren().forEach((t) -> {
                HBox hbx = ((HBox) t);
                hbx.getChildren().forEach((tf) -> {
                    CustomTextField txtField = ((CustomTextField) tf);
                    if(!txtField.getText().isEmpty()){
                        content.getSelectionModel().selectNext();
                    }
                });
            });            
        });                
    }    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                                .getResource("NewTest.fxml"));        
        Scene scene = new Scene(root);        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    

    public void swapToTextField(Text t){
        String txId = t.getUserData().toString();
        String s = t.getText();
        Node n = t.getParent();
        n.lookup(txId);
        
        if(n instanceof Pane){            
            TextField tx = new TextField(s); 
            tx.setMinWidth(600);
            tx.setUserData(txId);
            tx.setOnAction((event) -> {                
                swapToLabeled((TextField) event.getSource());
            });            
            tx.requestFocus();            
            int index = ((Pane) n).getChildren().indexOf(t);
            ((Pane) n).getChildren().add(index, tx);
            ((Pane) n).getChildren().remove(t);            
        }
    }    
    
    public void swapToLabeled(TextField t){        
        String id = t.getUserData().toString();
        String s = t.getText();
        Node n = t.getParent();
        n.lookup(id);
        if(n instanceof Pane){
            Text tx = new Text(s);  
            tx.setWrappingWidth(600);            
            tx.setUserData(id);           
            int index = ((Pane) n).getChildren().indexOf(t);            
            ((Pane) n).getChildren().add(index, tx);
            tx.setOnMouseClicked((event) -> {                 
                swapToTextField(((Text) event.getSource()));
            });            
            ((Pane) n).getChildren().remove(t);            
        }        
    }
    
    //ObservableList<CustomTextField> arr = FXCollections.observableArrayList();
    //ObservableList<String> arrOfTestParts = FXCollections.observableArrayList();
    @FXML
    private void populateVbxParts(ActionEvent event) {       
        String s = tfNofParts.getText().trim();
        try{
            if(validator.isNumberOnly(s)){
                int n = Integer.parseInt(s);
                vbxTestParts.getChildren().clear();                                
                test.testParts.clear();
                
                for(int i = 1; i <= n; i++){                    
                    HBox hbx = new HBox();
                    hbx.setAlignment(Pos.CENTER_LEFT);
                    hbx.setSpacing(10.0);
                                       
                    CustomTextField tf = new CustomTextField();
                    tf.setOnAction((evt) -> {
                        vbxTestParts.getChildren().forEach((t) -> {
                            HBox hbxt = ((HBox) t);
                            hbxt.getChildren().forEach((txf) -> {
                                CustomTextField txtField = ((CustomTextField) txf);
                                if(!txtField.getText().isEmpty()){
                                    btnPartNext.setDisable(false);
                                }else{
                                    btnPartNext.setDisable(true);
                                }
                            });
                        });                         
                    });
                    // rn used by swapToTextField method
                    String rn = db.getRandom(6);
                    tf.setUserData(rn);                    
                    
                    tf.setMinWidth(300);
                    Label lbl = new Label("Part "+i);

                    hbx.getChildren().add(lbl);
                    hbx.getChildren().add(tf);            

                    vbxTestParts.getChildren().add(hbx);
                }                
            }else{
                validator.validateNumber(tfNofParts, "Input numbers only");
                tfNofParts.getStyleClass().add("required");
            }
        }catch(NumberFormatException ex){
            System.out.println(ex);
        }
    }
    
    @FXML
    private void togglePartsEditable(ActionEvent event) {                        
        try{
            if(btnOkParts.isSelected()){
                btnOkParts.setText("Edit");
            }else{
                btnOkParts.setText("Ok");
            }
            test.testParts.clear();
            vbxTestParts.getChildren().forEach((t) -> {
                HBox tf = ((HBox) t);               
                tf.getChildren().forEach((txtField) -> {
                    if(txtField instanceof CustomTextField){
                        String s = ((CustomTextField) txtField).getText().trim();
                        test.testParts.put(s, db.getRandom(6));
                        System.out.println(test.testParts.keySet());
                    }
                });
                
            });
            cmbParts.getItems().clear();            
            test.testParts.forEach((t, u) -> {
                cmbParts.getItems().add(t);
            });
            cmbParts.getSelectionModel().selectFirst();
        }catch(Exception e){
            System.out.println(e);
        }
    }           

    @FXML
    private void previouseTab(ActionEvent event) {
        content.getSelectionModel().selectPrevious();
    }
    private void nextTab(ActionEvent event) {
        content.getSelectionModel().selectNext();       
    }    


    @FXML
    private void showAddQuestionPane(ActionEvent event) {        
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
                
        HBox hb = new HBox();
        Text heading = new Text("Type qestion here: ");
        hb.getChildren().add(heading);
        
        dialogLayout.setHeading(hb);
        TextArea textArea = new TextArea();
        dialogLayout.setBody(textArea);
        
        JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        HBox hbx = new HBox();
        hbx.setSpacing(10);
        
        JFXButton cancel = new JFXButton("Cancel");
        cancel.getStyleClass().add("md-red-button");
        cancel.setOnAction((evt) -> {
            dialog.close();
        });
        
        JFXButton ok = new JFXButton("Ok");
        ok.getStyleClass().add("md-green-button");
        ok.setOnAction((evt) -> {
            String q = textArea.getText().trim();            
            vbxQuestion.getChildren().add(createQuestionPane(q));
            
            scrpQuestions.vvalueProperty().bind(ancQuestion.heightProperty());
            
            
            dialog.close();
        });  
        
        hbx.getChildren().add(cancel);
        hbx.getChildren().add(ok);
        dialogLayout.setActions(hbx);
        dialog.show();
    }
    
    
    //ObservableList<AnchorPane> arrOfQuestionPane = FXCollections.observableArrayList();
    private AnchorPane createQuestionPane(String q){
        // initialize txtQuestion instance
        Question questionClass = new Question();
        questionClass.question = q;
        questionClass.qId = db.getRandom(10);
        questionClass.partId = cmbParts.getSelectionModel().getSelectedItem();
        test.questions.add(questionClass);       

        // initialized question pane
        AnchorPane ancQuestionContainer = new AnchorPane();        
        ancQuestionContainer.getStyleClass().add("question");
        ancQuestionContainer.setPadding(new Insets(10));
        ancQuestionContainer.setPrefHeight(AnchorPane.USE_COMPUTED_SIZE);
        double d = ancQuestionContainer.getPrefHeight();
        
        // add HBox Question to container
        HBox hbxQuestion = new HBox();        
        hbxQuestion.setSpacing(10);
        hbxQuestion.setLayoutX(10);
        hbxQuestion.setLayoutY(10);
        hbxQuestion.setAlignment(Pos.CENTER_LEFT);
                        
        Text txtQuestion = new Text(q);
        txtQuestion.setWrappingWidth(800);
        txtQuestion.setStyle("-fx-font-size: 18px;");        
        hbxQuestion.getChildren().add(txtQuestion);                
        ancQuestionContainer.getChildren().add(hbxQuestion);        

        // add VBox Choices to container
        // initialize choices container
        VBox vbxChoices = new VBox();
        vbxChoices.setLayoutY(d + 40);
        vbxChoices.setSpacing(10);
        vbxChoices.setPadding(new Insets(10));
        vbxChoices.setAlignment(Pos.CENTER_LEFT);
        ancQuestionContainer.getChildren().add(vbxChoices);        
        
        // True or False CheckBox
        JFXCheckBox chbTrueOrFalse = new JFXCheckBox("True or False");
        chbTrueOrFalse.setSelected(false);
        hbxQuestion.getChildren().add(chbTrueOrFalse);
        
        chbTrueOrFalse.setOnAction((event) -> {
            if(chbTrueOrFalse.isSelected()){                
                questionClass.isMultipleChoice = false;               
                loadBooleanChoices(questionClass, vbxChoices);                
            }else{                
                questionClass.isMultipleChoice = true;
                loadChoices(questionClass, vbxChoices);
            }
        });        
        
        // Button addChoice
        JFXButton addChoice = new JFXButton("Add Choice");        
        hbxQuestion.getChildren().add(addChoice);        
        addChoice.getStyleClass().add("md-green-button");
        addChoice.disableProperty().bind(chbTrueOrFalse.selectedProperty());
        addChoice.setOnAction((event) -> {                      
            if((questionClass.choices.size() + 1) <= questionClass.MAX_CHOICES){                
                System.out.println("Question: "+questionClass.question);
                System.out.println("Length of Choices: "+questionClass.choices.size());
                questionClass.choices.forEach((t, u) -> {
                    System.out.println("Length of Choice: "+t);
                });                
                
                JFXDialogLayout dialogLayout = new JFXDialogLayout();

                HBox hb = new HBox();
                Text heading = new Text("Type choice here: ");
                hb.getChildren().add(heading);

                dialogLayout.setHeading(hb);
                TextArea textAreaChoice = new TextArea();
                dialogLayout.setBody(textAreaChoice);

                JFXDialog dialog = 
                        new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                HBox hbxAction = new HBox();
                hbxAction.setSpacing(10);

                JFXButton cancel = new JFXButton("Cancel");
                cancel.getStyleClass().add("md-red-button");
                cancel.setOnAction((evt) -> {
                    dialog.close();
                });

                // Add choices to txtQuestion from dialog
                JFXButton ok = new JFXButton("Ok");
                ok.getStyleClass().add("md-green-button");
                ok.setOnAction((evt) -> {   
                    //String ch = textAreaChoice.getText().trim();                                
                    vbxChoices
                            .getChildren()
                            .add(createChoicesPane(questionClass,textAreaChoice.getText().trim()));
                    dialog.close();
                });  

                hbxAction.getChildren().add(cancel);
                hbxAction.getChildren().add(ok);
                dialogLayout.setActions(hbxAction);
                dialog.show();             
            }else{
                notif.showDarkErrorNotif("Error",
                                         "Maximum of "+questionClass.MAX_CHOICES,
                                         Pos.BOTTOM_RIGHT, 2.0);
            }     
        });    
        
        // Add image button
        JFXButton addImage = new JFXButton("Add Image");
        addImage.getStyleClass().add("md-green-button");
        hbxQuestion.getChildren().add(addImage);
        addImage.setOnAction((event) -> {
            
        });        
        
        // Close button
        JFXButton btnClose = new JFXButton("X");
        btnClose.getStyleClass().add("md-red-button");
        hbxQuestion.getChildren().add(btnClose);
        btnClose.setOnAction((btnCls) -> {
            test.questions.remove(questionClass);
            vbxQuestion.getChildren().remove(ancQuestionContainer);
        });
                
        return ancQuestionContainer;
    }
    
    private void loadBooleanChoices(Question q, VBox vbx){      
        vbx.getChildren().clear();
        q.booleanChoices.forEach((t) -> {            
            JFXRadioButton rb = new JFXRadioButton(t.toUpperCase()+" Set as Correct Answer");
            rb.setUserData(t);
            rb.setOnAction((event) -> {q.correctAnswer = "false";});
            rb.setToggleGroup(q.tglGrp);               
            vbx.getChildren().add(rb);            
        });        
    }
    
    private void loadChoices(Question q, VBox vbx){     
        vbx.getChildren().clear();
        q.choices.forEach((choice, imgPath) -> {
            HBox hbx = new HBox();            
            hbx.setSpacing(10);
            hbx.setAlignment(Pos.CENTER_LEFT);
            
            Text ch = new Text(choice);
            hbx.getChildren().add(ch);
            ch.setWrappingWidth(600);
            ch.setUserData(db.getRandom(6));
            ch.setOnMouseClicked((event) -> {
                swapToTextField(((Text) event.getSource()));
            });
                        
            JFXRadioButton rb = new JFXRadioButton("Set as Correct Answer");
            rb.setToggleGroup(q.tglGrp);
            hbx.getChildren().add(rb);            
            rb.setOnAction((event) -> {
                q.correctAnswer = choice;
            });

            JFXButton btnImg = new JFXButton("Add Image");
            btnImg.getStyleClass().add("xsm-blue-button");
            hbx.getChildren().add(btnImg); 
            
            JFXButton btnRemove = new JFXButton("X");
            btnRemove.getStyleClass().add("xsm-red-button");
            btnRemove.setOnAction((event) -> {
                JFXButton b = (JFXButton) event.getSource();
                Pane p = (Pane) b.getParent();
                Pane pOfParent = (Pane) p.getParent();
                pOfParent.getChildren().remove(p);
                q.choices.remove(ch.getText().trim());            
            });
            hbx.getChildren().add(btnRemove);             
            
            vbx.getChildren().add(hbx);
        });        
    }    
    
    private HBox createChoicesPane(Question q, String chi){
        HBox hbx = new HBox();
        hbx.setSpacing(10);
        hbx.setAlignment(Pos.CENTER_LEFT);
        
        if(!q.choices.containsKey(chi)){
            q.choices.put(chi, "");

            Text ch = new Text(chi);
            ch.setWrappingWidth(600);
            ch.setUserData(db.getRandom(6));
            hbx.getChildren().add(ch);
            ch.setOnMouseClicked((event) -> {                 
                swapToTextField(((Text) event.getSource()));
            });

            JFXRadioButton rb = new JFXRadioButton("Set as Correct Answer");
            rb.setToggleGroup(q.tglGrp);
            hbx.getChildren().add(rb);
            rb.setOnAction((event) -> {
                q.correctAnswer = chi;
            });

            JFXButton btnImg = new JFXButton("Add Image");
            btnImg.getStyleClass().add("xsm-blue-button");
            hbx.getChildren().add(btnImg);        

            JFXButton btnRemove = new JFXButton("X");
            btnRemove.getStyleClass().add("xsm-red-button");
            btnRemove.setOnAction((event) -> {
                JFXButton b = (JFXButton) event.getSource();
                Pane p = (Pane) b.getParent();
                Pane pOfParent = (Pane) p.getParent();
                pOfParent.getChildren().remove(p);
                q.choices.remove(chi);            
            });
            hbx.getChildren().add(btnRemove);             
        }else{
            notif.showDarkErrorNotif("Error",
                                     "Duplicate entry",
                                     Pos.BOTTOM_RIGHT, 2.0);            
            return null;
        }                     
        return hbx;
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void save(ActionEvent event) {
    }

    
    @FXML
    private void setSelectedTestPart(ActionEvent event) {        
        String s = cmbParts.getSelectionModel().getSelectedItem().toLowerCase();               
        vbxQuestion.getChildren().clear();
        test.testParts.forEach((testPart,testPartID) -> {
            test.questions.forEach((qs) -> {
                if(s.equals(qs.partId)){                    
                    vbxQuestion.getChildren().add(loadQuestion(qs));
                }                
            });
        });
    }

    private AnchorPane loadQuestion(Question q){
        // initialized question pane
        AnchorPane ancQuestionContainer = new AnchorPane();        
        ancQuestionContainer.getStyleClass().add("question");
        ancQuestionContainer.setPadding(new Insets(10));
        ancQuestionContainer.setPrefHeight(AnchorPane.USE_COMPUTED_SIZE);
        double d = ancQuestionContainer.getPrefHeight();
        
        // add HBox Question to container
        HBox hbxQuestion = new HBox();        
        hbxQuestion.setSpacing(10);
        hbxQuestion.setLayoutX(10);
        hbxQuestion.setLayoutY(10);
        hbxQuestion.setAlignment(Pos.CENTER_LEFT);
                        
        Text txtQuestion = new Text(q.question);
        txtQuestion.setWrappingWidth(800);
        txtQuestion.setStyle("-fx-font-size: 18px;");        
        hbxQuestion.getChildren().add(txtQuestion);                
        ancQuestionContainer.getChildren().add(hbxQuestion);        

        // add VBox Choices to container
        // initialize choices container
        VBox vbxChoices = new VBox();
        vbxChoices.setLayoutY(d + 40);
        vbxChoices.setSpacing(10);
        vbxChoices.setPadding(new Insets(10));
        vbxChoices.setAlignment(Pos.CENTER_LEFT);
        ancQuestionContainer.getChildren().add(vbxChoices);   
       
        loadChoices(q, vbxChoices);
        
        // True or False CheckBox
        JFXCheckBox chbTrueOrFalse = new JFXCheckBox("True or False");
        chbTrueOrFalse.setSelected(false);
        hbxQuestion.getChildren().add(chbTrueOrFalse);
        
        chbTrueOrFalse.setOnAction((event) -> {
            if(chbTrueOrFalse.isSelected()){                
                q.isMultipleChoice = false;               
                loadBooleanChoices(q, vbxChoices);                
            }else{                
                q.isMultipleChoice = true;
                loadChoices(q, vbxChoices);
            }
        });        
        
        // Button addChoice
        JFXButton addChoice = new JFXButton("Add Choice");        
        hbxQuestion.getChildren().add(addChoice);        
        addChoice.getStyleClass().add("md-green-button");
        addChoice.disableProperty().bind(chbTrueOrFalse.selectedProperty());
        addChoice.setOnAction((event) -> {                      
            if((q.choices.size() + 1) <= q.MAX_CHOICES){                
                System.out.println("Question: "+q.question);
                System.out.println("Length of Choices: "+q.choices.size());
                q.choices.forEach((t, u) -> {
                    System.out.println("Length of Choice: "+t);
                });                
                
                JFXDialogLayout dialogLayout = new JFXDialogLayout();

                HBox hb = new HBox();
                Text heading = new Text("Type choice here: ");
                hb.getChildren().add(heading);

                dialogLayout.setHeading(hb);
                TextArea textAreaChoice = new TextArea();
                dialogLayout.setBody(textAreaChoice);

                JFXDialog dialog = 
                        new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                HBox hbxAction = new HBox();
                hbxAction.setSpacing(10);

                JFXButton cancel = new JFXButton("Cancel");
                cancel.getStyleClass().add("md-red-button");
                cancel.setOnAction((evt) -> {
                    dialog.close();
                });

                // Add choices to txtQuestion from dialog
                JFXButton ok = new JFXButton("Ok");
                ok.getStyleClass().add("md-green-button");
                ok.setOnAction((evt) -> {   
                    //String ch = textAreaChoice.getText().trim();                                
                    vbxChoices
                            .getChildren()
                            .add(createChoicesPane(q,textAreaChoice.getText().trim()));
                    dialog.close();
                });  

                hbxAction.getChildren().add(cancel);
                hbxAction.getChildren().add(ok);
                dialogLayout.setActions(hbxAction);
                dialog.show();             
            }else{
                notif.showDarkErrorNotif("Error",
                                         "Maximum of "+q.MAX_CHOICES,
                                         Pos.BOTTOM_RIGHT, 2.0);
            }     
        });    
        
        // Add image button
        JFXButton addImage = new JFXButton("Add Image");
        addImage.getStyleClass().add("md-green-button");
        hbxQuestion.getChildren().add(addImage);
        addImage.setOnAction((event) -> {
            
        });        
        
        // Close button
        JFXButton btnClose = new JFXButton("X");
        btnClose.getStyleClass().add("md-red-button");
        hbxQuestion.getChildren().add(btnClose);
        btnClose.setOnAction((btnCls) -> {
            test.questions.remove(q);
            vbxQuestion.getChildren().remove(ancQuestionContainer);
        });        
        
        return ancQuestionContainer;
    }
    
    private void toggleBtnNextDisabled() {        
        if(!tfTitle.getText().trim().isEmpty() &&
           !tfDuration.getText().trim().isEmpty() &&
           !taDescription.getText().trim().isEmpty()){       
           btnNewTest.setDisable(false);
        }else{
           btnNewTest.setDisable(true); 
        }
    }

    @FXML
    private void validateDuration(KeyEvent event) {
        String s = tfDuration.getText().trim();
        int l = 120; // Maximum Minutes of a Test
        double nDelay = 2.5;
        String msg = "";
        if(!s.isEmpty()){            
            if(!validator.isNumberOnly(s)){ // not a number
                msg = "Input positive integer only (Ex. 45)\n"+
                      "Maximum of "+l+" minutes.\n"+
                      "Wrong input '"+s+"'";
                notif.showErrorNotif("ERROR", msg, Pos.BOTTOM_RIGHT, nDelay);
                tfDuration.setText("");
            }else{                   
                int m = Integer.parseInt(s);
                if((m > l)){ // not more then 120 minutes
                    msg = "Maximum of "+l+" minutes only.\n"+
                          "Wrong input '"+s+"'";
                    notif.showErrorNotif("ERROR", msg, Pos.BOTTOM_RIGHT, nDelay);
                    tfDuration.setText("");
                }
                toggleBtnNextDisabled();
            }
        }else {
            toggleBtnNextDisabled();
        }
    }

    @FXML
    private void toggleBtnNext(KeyEvent event) {
        toggleBtnNextDisabled();
    }
    
    class Test{
        String testTitle = "";
        String testDescription = "";
        double testDuration = 0.0;
        ObservableList<Question> questions = FXCollections.observableArrayList();        
        //<String as TestPart, String as TestPart ID>
        ObservableMap<String, String> testParts = FXCollections.observableMap(new HashMap<String, String>());
        
        public void addQuestion(Question q){
            try{
                this.questions.add(q);
            }catch(Exception e){}
        }
        
        public void removeQuestion(Question q){
            try{
                this.questions.remove(q);
            }catch(Exception e){}
        }
    }
    
    class Question{       
        String question = "";
        String imgPath = "";
        String qId = "";
        String partId = "";
        String part = "";
        String correctAnswer = "";
        ToggleGroup tglGrp = new ToggleGroup();        
        final static int MAX_CHOICES = 5;
        
        // default question type TRUE or FALSE
        boolean isMultipleChoice;                                        
        
        //<String as Choice, ImagePath>
        ObservableMap<String, String> choices = FXCollections.observableMap(new HashMap<String, String>());
        ObservableList<String> booleanChoices = FXCollections.observableArrayList();
        
        Question(){
            booleanChoices.add("true");                    
            booleanChoices.add("false");
        }
        
        public void addChoice(String s, String i){
            try{
                this.choices.put(s, i);
            }catch(Exception e){}
        }
        
        public void removeChoice(String s, String i){
            try{
                this.choices.remove(s, i);
            }catch(Exception e){}            
        }
    }
}
