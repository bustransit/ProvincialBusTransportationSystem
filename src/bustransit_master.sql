-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 03, 2018 at 06:27 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bustransit_master`
--

-- --------------------------------------------------------

--
-- Table structure for table `barmoto`
--

CREATE TABLE `barmoto` (
  `id` int(11) NOT NULL,
  `employee_type` text,
  `employee_rate` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barmoto`
--

INSERT INTO `barmoto` (`id`, `employee_type`, `employee_rate`) VALUES
(1, 'Driver Consultant', 7),
(2, 'Marketing Manager', 15),
(3, 'Conductor', 12),
(4, 'Inspector', 17),
(5, 'HR Manager', 10);

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `plate_no` varchar(11) NOT NULL,
  `brand` text NOT NULL,
  `supplier` double NOT NULL,
  `fuel_capacity` double NOT NULL,
  `route` text NOT NULL,
  `date_purchase` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`plate_no`, `brand`, `supplier`, `fuel_capacity`, `route`, `date_purchase`) VALUES
('09877', 'EGM', 22, 11.4, 'baguio', '2018-08-08'),
('09878', 'Hyndai Phil', 12, 11.5, 'ilocos', '2018-08-10'),
('777', 'Holisitc', 23.4, 23.1, 'Bulacan', '2018-08-13'),
('BTS-4101', 'Mitsubishi', 12, 10.5, 'Camarines Sur', '2018-08-22');

-- --------------------------------------------------------

--
-- Table structure for table `competency_report`
--

CREATE TABLE `competency_report` (
  `rec_id` int(5) UNSIGNED ZEROFILL NOT NULL,
  `remarks` enum('competent','incompetent') DEFAULT NULL,
  `population` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `competency_report`
--

INSERT INTO `competency_report` (`rec_id`, `remarks`, `population`) VALUES
(00001, 'competent', 100),
(00002, 'competent', 70),
(00003, 'incompetent', 10),
(00004, 'competent', 80),
(00005, 'competent', 90),
(00006, 'competent', 98),
(00007, 'competent', 89),
(00008, 'incompetent', 6);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `department_code` enum('hr','finance','logistics','admin','core','it') NOT NULL,
  `department_name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `scope` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`department_code`, `department_name`, `description`, `scope`) VALUES
('hr', 'Human Resource', 'Human Resource', 'Jopb analytics'),
('finance', 'Finance', 'financials', 'Account Receivable'),
('logistics', 'Logistics', 'Logistics', 'Asset management'),
('admin', 'Administration', 'administrations', 'Document Tracking'),
('core', 'Core', 'core transactions', 'Ticketing'),
('it', 'Information Technology', 'Administer software system', 'Technology');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `emp_id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `middlename` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `immediate_supervisor_code` int(5) UNSIGNED ZEROFILL NOT NULL,
  `age` tinyint(3) NOT NULL,
  `gender` varchar(30) NOT NULL,
  `position_id` int(5) UNSIGNED ZEROFILL NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`emp_id`, `firstname`, `middlename`, `lastname`, `immediate_supervisor_code`, `age`, `gender`, `position_id`) VALUES
(0000000001, 'Nicolas', 'Spark', 'Smith', 00002, 0, '', 00002),
(0000000002, 'Lance', 'Lepersey', 'Lincoln', 02017, 0, '', 00002),
(0000000003, 'Don', 'Quiote', 'Duke', 00100, 0, '', 00003),
(0000000004, 'Danny', 'Collin', 'Kurt', 00000, 0, '', 00004),
(0000000005, 'Daniel', 'Pratts', 'Newman', 00000, 0, '', 00005),
(0000000006, 'Emily', 'Smith', 'Dickinson', 00000, 0, '', 00006),
(0000000007, 'Doney', 'Powel', 'Yan', 00000, 0, '', 00007),
(0000000008, 'Emma', 'Miller', 'Spring', 00000, 0, '', 00008),
(0000000009, 'Demi', 'Cloney', 'Lee', 00000, 0, '', 00009),
(0000000010, 'Ruzzel', 'Conner', 'Yap', 00000, 0, '', 00010),
(0000000011, 'Lharima', 'Karim', 'Amir Mizuari', 00000, 0, '', 00011),
(0000000012, 'Feona Lyn', 'Clintt', 'Torres', 00000, 0, '', 00012),
(0000000013, 'Nicole', 'Kidmann', 'Alvarez', 00000, 0, '', 00013),
(0000000014, 'Mickey', 'Dorre', 'Lim', 00000, 0, '', 00014),
(0000000015, 'Larry', 'Dee', 'Breed', 00004, 0, '', 00015),
(0000000016, 'Lory', 'Matiz', 'Paulilan', 00000, 0, '', 00015);

-- --------------------------------------------------------

--
-- Table structure for table `employee_evaluation`
--

CREATE TABLE `employee_evaluation` (
  `rec_id` int(6) UNSIGNED ZEROFILL NOT NULL,
  `month` date DEFAULT NULL,
  `pass` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_evaluation`
--

INSERT INTO `employee_evaluation` (`rec_id`, `month`, `pass`) VALUES
(000001, '2018-04-12', 34.5),
(000002, '2018-05-12', 36.4),
(000003, '2018-06-12', 38.4),
(000004, '2018-07-12', 37.8),
(000005, '2018-08-12', 38.1);

-- --------------------------------------------------------

--
-- Table structure for table `employee_position`
--

CREATE TABLE `employee_position` (
  `position_id` int(5) UNSIGNED ZEROFILL NOT NULL,
  `position_name` text NOT NULL,
  `description` text NOT NULL,
  `scope` text NOT NULL,
  `population` int(4) NOT NULL,
  `vacancy` int(4) NOT NULL,
  `department_code` enum('hr','finance','logistics','core','admin','it') NOT NULL,
  `position_level` enum('staff','supervisor','managerial','board','system_admin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_position`
--

INSERT INTO `employee_position` (`position_id`, `position_name`, `description`, `scope`, `population`, `vacancy`, `department_code`, `position_level`) VALUES
(00001, 'HR Staff', '', '', 5, 2, 'hr', 'staff'),
(00002, 'HR Supervisor', '', '', 5, 1, 'hr', 'supervisor'),
(00003, 'HR Trainee', '', '', 5, 2, 'hr', 'staff'),
(00004, 'HR Manager', '', '', 1, 1, 'hr', 'managerial'),
(00005, 'IT Supervisor', 'Supervise IT Department', 'On It Department only', 2, 1, 'it', 'supervisor'),
(00006, 'Recruitment Specialist', '', '', 0, 0, 'hr', 'staff'),
(00007, 'HR Recruitment', '', '', 0, 0, 'hr', 'staff'),
(00008, 'Company Lawyer', '', '', 0, 0, 'admin', 'staff'),
(00009, 'Chief Purchaser', '', '', 0, 0, 'logistics', 'managerial'),
(00010, 'Liason Officer', '', '', 0, 0, 'logistics', 'staff'),
(00011, 'Purchaser', '', '', 0, 0, 'logistics', 'staff'),
(00012, 'Warehouse man', '', '', 0, 0, 'logistics', 'staff'),
(00013, 'Payroll Officer', '', '', 0, 0, 'hr', 'staff'),
(00014, 'Auditor', '', '', 0, 0, 'finance', 'staff'),
(00015, 'Conductor', '', '', 0, 0, 'core', 'staff'),
(00016, 'Bus Driver', '', '', 0, 0, 'core', 'staff'),
(00017, 'Checker', 'Bus Checker\r\nMonitor tickets', '', 1, 1, 'core', 'staff');

-- --------------------------------------------------------

--
-- Table structure for table `employee_promotion`
--

CREATE TABLE `employee_promotion` (
  `rec_id` int(4) UNSIGNED ZEROFILL NOT NULL,
  `state_promoton` double DEFAULT NULL,
  `month` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_promotion`
--

INSERT INTO `employee_promotion` (`rec_id`, `state_promoton`, `month`) VALUES
(0001, 25.6, '2018-04-12'),
(0002, 23.4, '2018-05-12'),
(0003, 28.8, '2018-06-12'),
(0004, 29.5, '2018-07-12'),
(0005, 26.5, '2018-08-12');

-- --------------------------------------------------------

--
-- Table structure for table `ess`
--

CREATE TABLE `ess` (
  `id` int(11) NOT NULL,
  `employee_type` text,
  `login_count` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ess`
--

INSERT INTO `ess` (`id`, `employee_type`, `login_count`) VALUES
(1, 'Driver', 25),
(2, 'Conductor', 20),
(3, 'Inspector', 17),
(4, 'Mechanic', 12);

-- --------------------------------------------------------

--
-- Table structure for table `functions`
--

CREATE TABLE `functions` (
  `function_id` int(2) NOT NULL,
  `function_name` text NOT NULL,
  `module_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `job_transition`
--

CREATE TABLE `job_transition` (
  `record_id` int(3) DEFAULT NULL,
  `emp_id` int(10) DEFAULT NULL,
  `job_transition_type` enum('promotion','demotion','retirement','in-charge') DEFAULT NULL,
  `efectivity_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `learning_modules`
--

CREATE TABLE `learning_modules` (
  `module_id` int(4) UNSIGNED ZEROFILL NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `author` text NOT NULL,
  `completion_rate` double NOT NULL,
  `satisfaction_rate` double DEFAULT NULL,
  `date_implemented` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `learning_modules`
--

INSERT INTO `learning_modules` (`module_id`, `title`, `content`, `author`, `completion_rate`, `satisfaction_rate`, `date_implemented`) VALUES
(0001, 'Defensive Driving Modules', 'sdfdf', 'LTO and LTFRB', 89.4, 97.5, '2018-02-12'),
(0002, 'Leadership Training Modules', 'adsfadf', 'Management', 98.9, 98.7, '2018-03-20'),
(0003, 'Managerial Skills Level Modules', 'Module content here', 'Nelson Dela Torre', 86.5, 80.6, '2018-06-27');

-- --------------------------------------------------------

--
-- Table structure for table `leave_application`
--

CREATE TABLE `leave_application` (
  `leave_rec_id` int(5) UNSIGNED ZEROFILL NOT NULL,
  `emp_id` int(10) DEFAULT NULL,
  `reason` text,
  `filing_date` date DEFAULT NULL,
  `efectivity_date` date DEFAULT NULL,
  `status` enum('pending','approved','disapproved') DEFAULT NULL,
  `care_of` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `log_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `emp_id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `activity` text NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `module_id` varchar(50) NOT NULL,
  `id` int(3) UNSIGNED ZEROFILL NOT NULL,
  `department` enum('HUMAN RESOURCE','LOGISTICS','ADMINISTRATOR','FINANCE','CORE','INFORMATION TECHNOLOGY') NOT NULL,
  `department_level` enum('HR1','HR2','HR3','HR4','LOG1','LOG2','CORE1','CORE2','ADMIN','FINANCE','IT') NOT NULL,
  `module` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`module_id`, `id`, `department`, `department_level`, `module`) VALUES
('app_mgt', 006, 'HUMAN RESOURCE', 'HR1', 'Applicant Managment'),
('asset_mgt', 021, 'LOGISTICS', 'LOG1', 'Asset Manement'),
('audit_mgt', 024, 'LOGISTICS', 'LOG2', 'Audit Management'),
('budget_mgt', 031, 'FINANCE', 'FINANCE', 'Budget Management'),
('claims_reim', 014, 'HUMAN RESOURCE', 'HR3', 'Claims And Reimbursement'),
('collection', 029, 'FINANCE', 'FINANCE', 'Collection'),
('compensation', 017, 'HUMAN RESOURCE', 'HR4', 'Compensation Planning And Administration'),
('comp_mgt', 005, 'HUMAN RESOURCE', 'HR2', 'Competency Management'),
('core_fleet', 038, 'CORE', 'CORE1', 'Fleet Management'),
('core_human', 019, 'HUMAN RESOURCE', 'HR4', 'Core Human Capital Management'),
('disbursement', 030, 'FINANCE', 'FINANCE', 'Disbursement'),
('doc_mgt', 037, 'ADMINISTRATOR', 'ADMIN', 'Document Management'),
('doc_tracking', 025, 'LOGISTICS', 'LOG2', 'Document Tracking System'),
('driver_mgt', 045, 'CORE', 'CORE2', 'Driver Management'),
('ess', 004, 'HUMAN RESOURCE', 'HR2', 'Employee Self Service'),
('expense_mgt', 044, 'CORE', 'CORE2', 'Expense Management'),
('facilitis_res', 035, 'ADMINISTRATOR', 'ADMIN', 'Facilities Reservation'),
('fleet_mgt', 027, 'LOGISTICS', 'LOG2', 'Fleet Management'),
('fuel_mgt', 043, 'CORE', 'CORE2', 'Fuel Management'),
('general_ldgr', 032, 'FINANCE', 'FINANCE', 'General Ledger'),
('gps', 041, 'CORE', 'CORE1', 'GPS Tracking and Live Monitoring'),
('hr_analytics', 018, 'HUMAN RESOURCE', 'HR4', 'HR Analytics'),
('learning_mgt', 001, 'HUMAN RESOURCE', 'HR2', 'Learning Management'),
('leave_mgt', 013, 'HUMAN RESOURCE', 'HR3', 'Leave Management'),
('legal_mgt', 034, 'ADMINISTRATOR', 'ADMIN', 'Legal Management'),
('newhire', 009, 'HUMAN RESOURCE', 'HR1', 'New Hire On Board'),
('passenger_mgt', 046, 'CORE', 'CORE2', 'Passenger Management and Ticketing'),
('payables', 047, 'FINANCE', 'FINANCE', 'Account Payable'),
('payroll', 016, 'HUMAN RESOURCE', 'HR4', 'Payroll'),
('per_mgt', 007, 'HUMAN RESOURCE', 'HR1', 'Performance Managment'),
('procurement', 020, 'LOGISTICS', 'LOG1', 'Procurement'),
('project_mgt', 022, 'LOGISTICS', 'LOG1', 'Project Management'),
('receivables', 033, 'FINANCE', 'FINANCE', 'Account Receivable'),
('recruitment', 008, 'HUMAN RESOURCE', 'HR1', 'Recruitment'),
('route_mgt', 040, 'CORE', 'CORE1', 'Route Management'),
('shift_sched', 011, 'HUMAN RESOURCE', 'HR3', 'Shift and Schedule'),
('social_rec', 010, 'HUMAN RESOURCE', 'HR1', 'Social Recognition'),
('succession_mgt', 003, 'HUMAN RESOURCE', 'HR2', 'Succession Planning'),
('time_attndnce', 015, 'HUMAN RESOURCE', 'HR3', 'Time And Attendance'),
('time_sheet', 012, 'HUMAN RESOURCE', 'HR3', 'Time Sheet Management'),
('tire_battery', 042, 'CORE', 'CORE2', 'Tire and Battery Management'),
('training_mgt', 002, 'HUMAN RESOURCE', 'HR2', 'Training Management'),
('trip_mgt', 039, 'CORE', 'CORE1', 'Trip Management'),
('vehicle_res', 026, 'LOGISTICS', 'LOG2', 'Vehicle Reservation'),
('vendor_portal', 028, 'LOGISTICS', 'LOG2', 'Vendor Portal'),
('visitor_mgt', 036, 'ADMINISTRATOR', 'ADMIN', 'Visitor Management'),
('warehousing', 023, 'LOGISTICS', 'LOG1', 'Warehousing');

-- --------------------------------------------------------

--
-- Table structure for table `stackmoto`
--

CREATE TABLE `stackmoto` (
  `id` int(11) NOT NULL,
  `Quarter` double DEFAULT NULL,
  `Quarter_rate` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stackmoto`
--

INSERT INTO `stackmoto` (`id`, `Quarter`, `Quarter_rate`) VALUES
(1, 20, 30),
(2, 40, 70),
(3, 75, 30),
(4, 20, 10);

-- --------------------------------------------------------

--
-- Table structure for table `succ`
--

CREATE TABLE `succ` (
  `id` int(11) NOT NULL,
  `succession_name` text,
  `succession_rate` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `succ`
--

INSERT INTO `succ` (`id`, `succession_name`, `succession_rate`) VALUES
(0, 'Internal Successor', 52),
(2, 'External Hires', 48);

-- --------------------------------------------------------

--
-- Table structure for table `succession`
--

CREATE TABLE `succession` (
  `rec_id` int(4) DEFAULT NULL,
  `population` int(5) DEFAULT NULL,
  `type` enum('demotion','promotion','tranfered') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `survey`
--

CREATE TABLE `survey` (
  `e_rec_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `timesheet`
--

CREATE TABLE `timesheet` (
  `record_id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `emp_id` int(10) UNSIGNED ZEROFILL DEFAULT NULL,
  `time_in` time DEFAULT NULL,
  `lunch_out` time DEFAULT NULL,
  `lunch_in` time DEFAULT NULL,
  `break_out` time DEFAULT NULL,
  `break_in` time DEFAULT NULL,
  `time_out` time DEFAULT NULL,
  `total_hours` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `training`
--

CREATE TABLE `training` (
  `record_id` int(5) UNSIGNED ZEROFILL NOT NULL,
  `participants` int(5) NOT NULL,
  `date` date NOT NULL,
  `type` enum('inhouse','outdoor') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `training`
--

INSERT INTO `training` (`record_id`, `participants`, `date`, `type`) VALUES
(00001, 100, '2018-01-12', 'inhouse'),
(00002, 78, '2018-02-12', 'inhouse'),
(00003, 89, '2018-03-12', 'outdoor'),
(00004, 60, '2018-04-12', 'outdoor'),
(00005, 80, '2018-05-12', 'outdoor'),
(00006, 106, '2018-06-12', 'outdoor'),
(00007, 60, '2018-07-12', 'inhouse'),
(00008, 60, '2018-04-12', 'outdoor'),
(00009, 50, '2017-12-12', 'inhouse'),
(00010, 67, '2017-11-12', 'inhouse'),
(00011, 90, '2017-10-12', 'outdoor'),
(00012, 40, '2017-09-12', 'outdoor');

-- --------------------------------------------------------

--
-- Table structure for table `training_assessment`
--

CREATE TABLE `training_assessment` (
  `record_id` int(5) DEFAULT NULL,
  `training_id` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `emp_id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `username` varchar(18) NOT NULL,
  `password` varchar(18) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`emp_id`, `username`, `password`) VALUES
(0000000001, 'hr2_training', 'hr2_training'),
(0000000002, 'hr2_learning', 'hr2_learning'),
(0000000003, 'hr2_succession', 'hr2_succession'),
(0000000004, 'hr2_ess', 'hr2_ess'),
(0000000009, 'hr2_competency', 'hr2_competency'),
(0000000010, 'hr3_shiftAndSched', 'hr3_shiftAndSched'),
(0000000011, 'hr3_timesheet', 'hr3_timesheet'),
(0000000012, 'hr3_leaveManagemen', 'hr3_leaveManagemen'),
(0000000013, 'hr3_claims', 'hr3_claims'),
(0000000014, 'hr3_attendance', 'hr3_attendance'),
(0000000015, 'hr4_', 'hr4_'),
(0000000016, 'log2', 'log2'),
(0000000017, 'hr2_supervisor', 'hr2_supervisor'),
(0000000018, 'adf', 'df'),
(0000000019, 'newuser', 'newuser');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `plate_no` varchar(11) NOT NULL,
  `brand` text NOT NULL,
  `supplier` text NOT NULL,
  `date_purchase` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `work_scope`
--

CREATE TABLE `work_scope` (
  `work_scope_id` int(3) UNSIGNED ZEROFILL NOT NULL,
  `user_id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `module_name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `work_scope`
--

INSERT INTO `work_scope` (`work_scope_id`, `user_id`, `module_name`) VALUES
(001, 0000000001, 'Certification'),
(002, 0000000001, 'Training'),
(003, 0000000001, 'HR Analytic');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barmoto`
--
ALTER TABLE `barmoto`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`plate_no`),
  ADD KEY `plate_no` (`plate_no`);

--
-- Indexes for table `competency_report`
--
ALTER TABLE `competency_report`
  ADD PRIMARY KEY (`rec_id`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`department_code`),
  ADD UNIQUE KEY `department_code` (`department_code`),
  ADD KEY `department_code_2` (`department_code`),
  ADD KEY `department_code_3` (`department_code`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`emp_id`),
  ADD UNIQUE KEY `emp_id` (`emp_id`),
  ADD KEY `emp_id_2` (`emp_id`),
  ADD KEY `emp_id_3` (`emp_id`),
  ADD KEY `position` (`position_id`),
  ADD KEY `position_2` (`position_id`),
  ADD KEY `emp_id_4` (`emp_id`),
  ADD KEY `position_3` (`position_id`),
  ADD KEY `position_4` (`position_id`),
  ADD KEY `position_id` (`position_id`);

--
-- Indexes for table `employee_evaluation`
--
ALTER TABLE `employee_evaluation`
  ADD PRIMARY KEY (`rec_id`);

--
-- Indexes for table `employee_position`
--
ALTER TABLE `employee_position`
  ADD PRIMARY KEY (`position_id`),
  ADD KEY `position_id` (`position_id`),
  ADD KEY `position_id_2` (`position_id`),
  ADD KEY `position_id_3` (`position_id`),
  ADD KEY `position_id_4` (`position_id`),
  ADD KEY `department_code` (`department_code`);

--
-- Indexes for table `employee_promotion`
--
ALTER TABLE `employee_promotion`
  ADD PRIMARY KEY (`rec_id`);

--
-- Indexes for table `ess`
--
ALTER TABLE `ess`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `functions`
--
ALTER TABLE `functions`
  ADD UNIQUE KEY `function_id` (`function_id`),
  ADD UNIQUE KEY `module_id` (`module_id`),
  ADD KEY `function_id_2` (`function_id`),
  ADD KEY `module_id_2` (`module_id`),
  ADD KEY `module_id_3` (`module_id`);

--
-- Indexes for table `learning_modules`
--
ALTER TABLE `learning_modules`
  ADD PRIMARY KEY (`module_id`),
  ADD UNIQUE KEY `record_id_2` (`module_id`),
  ADD KEY `record_id` (`module_id`);

--
-- Indexes for table `leave_application`
--
ALTER TABLE `leave_application`
  ADD PRIMARY KEY (`leave_rec_id`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`module_id`),
  ADD UNIQUE KEY `id_3` (`id`),
  ADD UNIQUE KEY `module_id_2` (`module_id`),
  ADD KEY `id` (`id`),
  ADD KEY `id_2` (`id`),
  ADD KEY `module_id` (`module_id`),
  ADD KEY `module_id_3` (`module_id`);

--
-- Indexes for table `stackmoto`
--
ALTER TABLE `stackmoto`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `succ`
--
ALTER TABLE `succ`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `timesheet`
--
ALTER TABLE `timesheet`
  ADD PRIMARY KEY (`record_id`),
  ADD KEY `FK_timesheet` (`emp_id`);

--
-- Indexes for table `training`
--
ALTER TABLE `training`
  ADD PRIMARY KEY (`record_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`emp_id`),
  ADD UNIQUE KEY `user_id_2` (`emp_id`),
  ADD KEY `user_id` (`emp_id`),
  ADD KEY `user_id_3` (`emp_id`),
  ADD KEY `user_id_4` (`emp_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`plate_no`),
  ADD KEY `plate_no` (`plate_no`);

--
-- Indexes for table `work_scope`
--
ALTER TABLE `work_scope`
  ADD PRIMARY KEY (`work_scope_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `competency_report`
--
ALTER TABLE `competency_report`
  MODIFY `rec_id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `employee_evaluation`
--
ALTER TABLE `employee_evaluation`
  MODIFY `rec_id` int(6) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `employee_position`
--
ALTER TABLE `employee_position`
  MODIFY `position_id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `employee_promotion`
--
ALTER TABLE `employee_promotion`
  MODIFY `rec_id` int(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `learning_modules`
--
ALTER TABLE `learning_modules`
  MODIFY `module_id` int(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `leave_application`
--
ALTER TABLE `leave_application`
  MODIFY `leave_rec_id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `module`
--
ALTER TABLE `module`
  MODIFY `id` int(3) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
--
-- AUTO_INCREMENT for table `timesheet`
--
ALTER TABLE `timesheet`
  MODIFY `record_id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `training`
--
ALTER TABLE `training`
  MODIFY `record_id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `work_scope`
--
ALTER TABLE `work_scope`
  MODIFY `work_scope_id` int(3) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `employee_position` (`position_id`);

--
-- Constraints for table `employee_position`
--
ALTER TABLE `employee_position`
  ADD CONSTRAINT `employee_position_ibfk_1` FOREIGN KEY (`department_code`) REFERENCES `department` (`department_code`);

--
-- Constraints for table `functions`
--
ALTER TABLE `functions`
  ADD CONSTRAINT `functions_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`);

--
-- Constraints for table `timesheet`
--
ALTER TABLE `timesheet`
  ADD CONSTRAINT `FK_timesheet` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
