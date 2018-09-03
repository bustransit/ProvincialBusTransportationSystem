/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus_transit.hr;

import java.net.URL;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.SegmentedButton;
import utilities.DBUtilities;

/**
 * FXML Controller class
 *
 * @author NelsonDelaTorre
 */
public class DailyTimeRecordController extends Application implements Initializable {
    
    DBUtilities db  = new DBUtilities();
    ResultSet rs;
    String qry;
    
    @FXML
    private SegmentedButton segmentedButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup group = new ToggleGroup();
        ToggleButton modena_b1 = new ToggleButton("Time in");
        ToggleButton modena_b2 = new ToggleButton("Lunch Out");
        ToggleButton modena_b3 = new ToggleButton("Lunch in");
        ToggleButton modena_b4 = new ToggleButton("Time out"); 
        
        segmentedButton.getButtons().addAll(modena_b1, modena_b2, modena_b3, modena_b4);
        segmentedButton.setToggleGroup(group);
        ToggleButton t = (ToggleButton) group.getSelectedToggle();        
        
        for (ToggleButton tgl : segmentedButton.getButtons()) {
            tgl.setOnMouseClicked(value->{                
                Date timeStamp = new Date();
                String strTime = new SimpleDateFormat("hh:mm").format(timeStamp).toString();
                String strDate = new SimpleDateFormat("yyyy-MM-dd").format(timeStamp).toString();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                //long start = timeStamp.getTime();
                //long start = System.nanoTime();
                long start = System.currentTimeMillis();
                //Thread.sleep(5*60*10);
                //long end = timeStamp.getTime();
                //long end = System.nanoTime();
                long end = System.currentTimeMillis();

                long elapse = end - start;
                String el = dateFormat.format(elapse);

//                System.out.println("Start: " + start);
//                System.out.println("End :" + end);
//                System.out.println("Elapse time:" + elapse);

                String g = tgl.getText();                
                System.out.println(g+": "+strTime);
                System.out.println("Date: "+strDate);                
                System.out.println("TimeStamp: "+timeStamp.toString());                
            });            
        }
    }    

    private void inputTime(String e, Time t){    
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
//        ModuleFunctions f = new ModuleFunctions();
        //f.loadFunctions("hr");
        Parent root = FXMLLoader.load(getClass().getResource("DailyTimeRecord.fxml"));
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
