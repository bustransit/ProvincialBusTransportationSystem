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
            this.applicantManagement = new File("").toURL();
            this.performanceManagement = new File("").toURL();
            this.newHireOnBoard = new File("").toURL();
            this.socialReconition = new File("").toURL();
            this.recruitment = new File("").toURL();
                        
            // HR 2
            this.learningManagement = new File("src/bus_transit/hr/learning/LearningManagement.fxml").toURL();
            this.trainingManagement = new File("src/bus_transit/hr/training/TrainingManagement.fxml").toURL();
            this.competencyManagement = new File("src/bus_transit/hr/competency/CompetencyManagement.fxml").toURL();
            this.successionPlanning = new File("src/bus_transit/hr/succession/SuccessionPlanning.fxml").toURL();
            this.ess = new File("src/bus_transit/hr/ess/EmployeeSelfService.fxml").toURL();
                        
            // HR 3
            this.shiftAndSchedule = new File("").toURL();
            this.timeSheetManagement = new File("").toURL();
            this.leaveManagement = new File("").toURL();
            this.claimsAndReimbursement = new File("").toURL();
            this.timeAndAttendance = new File("").toURL();
            
            // HR 4
            this.payroll = new File("").toURL();
            this.compensationPlanning = new File("").toURL();
            this.hrAnalytics = new File("").toURL();
            this.coreHumanCapital = new File("").toURL();
            
            // LOGISTIC 1
            this.procurement = new File("").toURL();
            this.assetManagement = new File("").toURL();
            this.projectManagement = new File("").toURL();
            this.warehousing = new File("").toURL();
            
            // LOGISTIC 2
            this.auditManagement = new File("").toURL();
            this.documentTracking = new File("").toURL();
            this.vehicleReservation = new File("").toURL();
            this.fleetManagementLog2 = new File("").toURL();
            
            // CORE 1
            this.fleetManagementCore = new File("").toURL();
            this.tripManagement = new File("").toURL();
            this.routeManagement = new File("").toURL();
            this.gpsTracking = new File("").toURL();
            
            // CORE 2
            this.tireAndBatteryManagement = new File("").toURL();
            this.fuelManagement = new File("").toURL();
            this.expenseManagement = new File("").toURL();
            this.driverManagement = new File("").toURL();
    
            // ADMIN
            this.documentManagement = new File("").toURL();
            this.legalManagement = new File("").toURL();
            this.visitorManagement = new File("").toURL();
            this.facilitiesReservation = new File("").toURL();

            // FINANCE
            this.accounts = new File("").toURL();
            this.collection = new File("").toURL();
            this.disbursement = new File("").toURL();
            this.budgetManagement = new File("").toURL();
            this.generalLedger = new File("").toURL();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Directories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
