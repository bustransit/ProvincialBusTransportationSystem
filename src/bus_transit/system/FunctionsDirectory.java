/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.system;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import javafx.collections.ObservableList;

/**
 *
 * @author NelsonDelaTorre
 */

public class FunctionsDirectory {
    public ObservableList<String> functions;            
    public ObservableList<String> directory;
    
    public FunctionsDirectory(){        
        directory.add("hr");
        functions.add("../hr/Certification.fxml");
        functions.add("../hr/Training.fxml");                                
        
        directory.add("logistics");
        functions.add("../hr/ProjectManagement.fxml");                                               
    }        

    /**
     * @return the functions
     */
    public ObservableList<String> getFunctions() {
        return functions;
    }

    /**
     * @param functions the functions to set
     */
    public void setFunctions(ObservableList<String> functions) throws PropertyVetoException {
        javafx.collections.ObservableList<java.lang.String> oldFunctions = this.functions;
        vetoableChangeSupport.fireVetoableChange(PROP_FUNCTIONS, oldFunctions, functions);
        this.functions = functions;
        propertyChangeSupport.firePropertyChange(PROP_FUNCTIONS, oldFunctions, functions);
    }

    /**
     * @return the directory
     */
    public ObservableList<String> getDirectory() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(ObservableList<String> directory) throws PropertyVetoException {
        javafx.collections.ObservableList<java.lang.String> oldDirectory = this.directory;
        vetoableChangeSupport.fireVetoableChange(PROP_DIRECTORY, oldDirectory, directory);
        this.directory = directory;
        propertyChangeSupport.firePropertyChange(PROP_DIRECTORY, oldDirectory, directory);
    }
    
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private final transient VetoableChangeSupport vetoableChangeSupport = new java.beans.VetoableChangeSupport(this);
    public static final String PROP_FUNCTIONS = "functions";
    public static final String PROP_DIRECTORY = "directory";
}
