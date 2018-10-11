package bus_transit;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NelsonDelaTorre
 */

public class Directories {
    // MAIN
    public URL login;
    public URL dashboard;
    public URL sidePane;
    
    // SYSTEM
    public URL forgotPassword;
    
    // HR 1
    public URL applicantManagement,
               performanceManagement,
               newHireOnBoard,
               socialReconition,
               recruitment;
    
    // HR 2
    public URL learningManagement,
               trainingManagement,
               competencyManagement,
               successionPlanning,
               ess; 
    
    // HR 3
    public URL shiftAndSchedule,
               timeSheetManagement,
               leaveManagement,
               claimsAndReimbursement,
               timeAndAttendance;
        
    // HR 4
    public URL payroll,
               compensationPlanning,
               hrAnalytics,
               coreHumanCapital;
    
    // LOGISTIC 1
    public URL procurement,
               assetManagement,
               projectManagement,
               warehousing;
    
    // LOGISTIC 2
    public URL auditManagement,
               documentTracking,
               vehicleReservation,
               fleetManagementLog2;
    
    // CORE 1
    public URL fleetManagementCore,
               tripManagement,
               routeManagement,
               gpsTracking;
        
    // CORE 2
    public URL tireAndBatteryManagement,
               fuelManagement,
               expenseManagement,
               driverManagement;
    
    // ADMIN
    public URL documentManagement,
               legalManagement,
               visitorManagement,
               facilitiesReservation;
    
    // FINANCE
    public URL accounts,
               collection,
               disbursement,
               budgetManagement,
               generalLedger;
        
    public Directories() {
        try {
            // MAIN
            this.login = new File("src/bus_transit/Login.fxml").toURL();
            this.dashboard = new File("src/bus_transit/Dashboard.fxml").toURL();
            this.sidePane = new File("src/bus_transit/Login.fxml").toURL();
                        
            // SYSTEM
            this.forgotPassword = new File("src/bus_transit/system/ForgotPassword.fxml").toURL();
            
            // HR 1
            
            
            // HR 2
            this.learningManagement = new File("src/bus_transit/hr/learning/LearningManagement.fxml").toURL();
            this.trainingManagement = new File("src/bus_transit/hr/training/TrainingManagement.fxml").toURL();
            this.competencyManagement = new File("src/bus_transit/hr/competency/CompetencyManagement.fxml").toURL();
            this.successionPlanning = new File("src/bus_transit/hr/succession/SuccessionPlanning.fxml").toURL();
            this.ess = new File("src/bus_transit/hr/ess/EmployeeSelfService.fxml").toURL();
            
            
            // HR 3
            
            // HR 4
            
            // LOGISTIC 1
            // LOGISTIC 2
            // CORE 1
            // CORE 2
            // ADMIN
            // FINANCE
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Directories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
