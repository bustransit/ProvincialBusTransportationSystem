/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.system;

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
}
