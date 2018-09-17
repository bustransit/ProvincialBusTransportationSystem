package utilities;

import com.jfoenix.controls.JFXComboBox;
import java.math.BigInteger;
import java.security.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DBUtilities {
    private String address = "";
    public String database_name;
    private String DATABASE_URL = "jdbc:mysql://localhost/bustransit_master";
    private String username = "root";
    private String password = "071325";
    public Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;    

    // Connect with modified database
    public DBUtilities(String a, String db, String u, String p) {
        this.DATABASE_URL = "jdbc:mysql://" + a + "/" + db;
        this.username = u;
        this.password = p;
        try {
            this.connection = (Connection) DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
        }
    }

    // Connect with default database
    public DBUtilities() {
        try {
            this.connection = (Connection) DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
        }
    }

    public void disconnect() {
        try {
            this.resultSet.close();
            this.statement.close();
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
        }
    }

    public ResultSet displayRecords(String sql_stmt) {
        try {
            this.statement = connection.createStatement();
            this.resultSet = statement.executeQuery(sql_stmt);
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
        }
        return resultSet;
    }

    public void execute(String sql_stmt) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate(sql_stmt);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
        }
    }

    public void populatePanel(String q, JPanel pnl) {
        try {
            this.resultSet = this.displayRecords(q);
            int i = 0;
            while (resultSet.next()) {
                i++;
                JLabel l = new JLabel(resultSet.getString(i));

                pnl.setLayout(new java.awt.GridLayout(i, 5, 8, 8));
                pnl.add(l);
            }
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateComboBox(String q, JComboBox cb) {
        cb.removeAll();
        try {
            this.resultSet = this.displayRecords(q);
            while (resultSet.next()) {
                cb.addItem(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateComboBox(String q, JFXComboBox cb) {
        cb.getItems().clear();
        try {
            this.resultSet = this.displayRecords(q);
            while (resultSet.next()) {
                cb.getItems().add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void appendToJFXComboBox(String q, JFXComboBox cb) {        
        try {
            this.resultSet = this.displayRecords(q);
            while (resultSet.next()) {
                cb.getItems().add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    /**
     * Archiving record
     * @param columnNameForID
     * @param deletingID
     * @param sourceTbl
     * @param archiveTbl 
     */
    public void archive(String columnNameForID, String deletingID, String sourceTbl, String archiveTbl){                        
        String q = "INSERT INTO " +archiveTbl+" "+
                   "SELECT * FROM "+sourceTbl+" WHERE "+sourceTbl+"."+columnNameForID+" = '"+deletingID+"'";
        this.execute(q);
        q = "DELETE FROM "+sourceTbl+" WHERE "+sourceTbl+"."+columnNameForID+" = '"+deletingID+"'";
        this.execute(q);
        new Alert(Alert.AlertType.INFORMATION,"Record is on archive now.").show();
    }    
    
    
    
    public void populateComboBox(String q, ComboBox cb) {
        cb.getItems().clear();
        try {
            this.resultSet = this.displayRecords(q);
            while (resultSet.next()) {
                cb.getItems().add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Populating Table in JavaFX
     * @param q
     * @param t 
     */
    public void populateTable(String q, TableView t) {
        //TABLE VIEW AND DATA
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            //SQL FOR SELECTING ALL OF CUSTOMER
            //ResultSet
            this.resultSet = this.displayRecords(q);

            t.getItems().clear();
            t.getColumns().clear();

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < this.resultSet.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(this.resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        SimpleStringProperty s = new SimpleStringProperty(param.getValue().get(j).toString());
                        return s;
                    }
                });
                t.getColumns().addAll(col);
            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            while (this.resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= this.resultSet.getMetaData().getColumnCount(); i++) {
                    String d;
                    if(this.resultSet.getString(i) == null){
                        d = "";
                    }else{
                        d = this.resultSet.getString(i);
                    }
                    //Iterate Column
                    row.add(d);
                }
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            t.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }    
    
    public void buildData(String q, TableView t) {
        //TABLE VIEW AND DATA
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            //SQL FOR SELECTING ALL OF CUSTOMER
            //ResultSet
            this.resultSet = this.displayRecords(q);

            t.getItems().clear();
            t.getColumns().clear();

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < this.resultSet.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(this.resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                t.getColumns().addAll(col);
            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            while (this.resultSet.next() || this.resultSet.equals(null)) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= this.resultSet.getMetaData().getColumnCount(); i++) {
                    String d;
                    if(this.resultSet.getString(i) == null){
                        d = "";
                    }else{
                        d = this.resultSet.getString(i);
                    }
                    //Iterate Column
                    row.add(d);
                }
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            t.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    // encrypt
    public String toSHA1(String s) {
        try {
            return Base64.getEncoder().encodeToString((MessageDigest.getInstance("SHA-1").digest(s.getBytes())));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toMD5(String input) {
        try {
            String hashText = new BigInteger(1, MessageDigest.getInstance("MD5").digest(input.getBytes())).toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String s) throws NoSuchAlgorithmException {
        return Base64.getDecoder().decode(s).toString();
    }

    // to SQL Date
    public java.sql.Date toSQLDate(java.util.Date d) {
        if (d != null) {
            return new java.sql.Date(d.getTime());
        }
        return null;
    }

    // to SQL Date
    public java.sql.Date toSQLDate(String d) {
        try {
            if (d != null) {
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dte = dt.parse(d);
                //return new java.sql.Date();            
            }
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * Charts
     */
    @FXML
    public void createPieChart(String title, String q, PieChart c) {
        c.getData().clear();

        this.resultSet = this.displayRecords(q);
        ObservableList data = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                data.add(new PieChart.Data(resultSet.getString(1), resultSet.getDouble(2)));
            }
            c.getData().addAll(data);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }

        final Label caption = new Label("");
        //caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        for (final PieChart.Data d : c.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(d.getPieValue())
                            + "%");
                }
            });
        }

        c.setTitle(title);

        for (PieChart.Data d : c.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(d.getPieValue())
                            + "%");
                }
            });
        }
    } // end of PieChart

    
    public void createBarChart(String title, String seriesTitle, String q, BarChart c) {
        c.getData().clear();

        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        int i = 1;
        int j = 2;

        c.setTitle(title);

        xAxis.setLabel("Values");
        xAxis.setTickLabelRotation(45);
        yAxis.setTickLabelRotation(45);
        yAxis.setLabel("Menus");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesTitle);
        try {
            this.resultSet = this.displayRecords(q);
            while (this.resultSet.next()) {
                XYChart.Data<String, Number> data = new XYChart.Data<String, Number>(this.resultSet.getString(i), this.resultSet.getDouble(j));
                series1.getData().add(data);
//                data.nodeProperty().addListener(new ChangeListener<Node>() {
//                    public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
//                        if (node != null) {
//                            if (data.getYValue().doubleValue() > 15) {
//                                node.setStyle("-fx-bar-fill:green");
//                            } else if (data.getYValue().doubleValue() > 10) {
//                                node.setStyle("-fx-bar-fill:navy");
//                            } else if (data.getYValue().doubleValue() > 5) {
//                                node.setStyle("-fx-bar-fill:red");
//                            } else if (data.getYValue().doubleValue() > 0) {
//                                node.setStyle("-fx-bar-fill:black");
//                            }
//                        }
//                    }
//                });

            }
            i++;
            j++;

        } catch (Exception e) {
            System.out.println(e);
        }
        c.getData().addAll(series1);
    } // End of BarChart

    @FXML
    public void createLineChart(String title, String seriesTitle, String q, LineChart c) {
        c.getData().clear();

        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        int i = 1;
        int j = 2;

        c.setTitle(title);

        xAxis.setLabel("Values");
        xAxis.setTickLabelRotation(45);
        yAxis.setTickLabelRotation(45);
        yAxis.setLabel("Menus");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesTitle);
        try {
            this.resultSet = this.displayRecords(q);
            while (this.resultSet.next()) {
                XYChart.Data<String, Number> data = new XYChart.Data<String, Number>(this.resultSet.getString(i), this.resultSet.getDouble(j));
                series1.getData().add(data);                
            }
            
            i++;
            j++;

        } catch (Exception e) {
            System.out.println(e);
        }
        c.getData().addAll(series1);
    }

    ;   
    
    @FXML
    public void createStackedAreaChart(String title, String seriesTitle, String q, StackedAreaChart c) {
        c.getData().clear();

        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        int i = 1;
        int j = 2;

        c.setTitle(title);

        xAxis.setLabel("Values");
        xAxis.setTickLabelRotation(45);
        yAxis.setTickLabelRotation(45);
        yAxis.setLabel("Menus");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesTitle);
        try {
            this.resultSet = this.displayRecords(q);
            while (this.resultSet.next()) {
                XYChart.Data<Number, Number> data = new XYChart.Data<Number, Number>(this.resultSet.getDouble(i), this.resultSet.getDouble(j));
                series1.getData().add(data);
            }
            i++;
            j++;

        } catch (Exception e) {
            System.out.println(e);
        }
        c.getData().addAll(series1);
    }
    
    @FXML
    public void createStackedAreaChartLap(String title, String seriesTitle, String q, StackedAreaChart c) {        
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        int i = 1;
        int j = 2;

        c.setTitle(title);

        xAxis.setLabel("Values");
        xAxis.setTickLabelRotation(45);
        yAxis.setTickLabelRotation(45);
        yAxis.setLabel("Menus");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesTitle);
        try {
            this.resultSet = this.displayRecords(q);
            while (this.resultSet.next()) {
                XYChart.Data<Number, Number> data = new XYChart.Data<Number, Number>(this.resultSet.getDouble(i), this.resultSet.getDouble(j));
                series1.getData().add(data);
            }
            i++;
            j++;

        } catch (Exception e) {
            System.out.println(e);
        }
        c.getData().addAll(series1);
    }    

    /**
     * @return the database_name
     */
    public String getDatabase_name() {
        return database_name;
    }

    /**
     * @param database_name the database_name to set
     */
    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }
}
