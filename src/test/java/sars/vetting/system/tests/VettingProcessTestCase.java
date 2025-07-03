package sars.vetting.system.tests;

import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sars.vetting.system.common.DamageLevel;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.config.TestDataSourceConfiguration;
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdjustmentResponsibility;
import sars.vetting.system.domain.EmailNotification;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.Responsibility;
import sars.vetting.system.domain.SubArea;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.AdjustmentCategoryServiceLocal;
import sars.vetting.system.service.EmailNotificationServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.EmployeeRoleServiceLocal;
import sars.vetting.system.service.SubAreaServiceLocal;
import sars.vetting.system.service.JobRoleServiceLocal;

/**
 *
 * @author S2028398
 */
@EnableJpaAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestDataSourceConfiguration.class)
public class VettingProcessTestCase {

    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;

    @Autowired
    private EmployeeServiceLocal employeeService;

    @Autowired
    private VettingServiceLocal vettingService;

    @Autowired
    private EmailNotificationServiceLocal emailNotificationService;

    @Autowired
    private JobRoleServiceLocal jobRoleService;

    @Autowired
    private SubAreaServiceLocal subAreaService;

    @Autowired
    private AdjustmentCategoryServiceLocal adjustmentCategoryService;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testScenarioA() {
        EmployeeRole superAdmin = BootStrapHelper.getSuperAdminRole("Super Administrator");
        employeeRoleService.save(superAdmin);
        EmployeeRole administrator = BootStrapHelper.getAdminRole("Administrator");
        employeeRoleService.save(administrator);
        EmployeeRole initiator = BootStrapHelper.getInitiatorRole("Initiator");
        employeeRoleService.save(initiator);
        EmployeeRole subject = BootStrapHelper.getSubjectRole("Subject");
        employeeRoleService.save(subject);
        EmployeeRole analyst = BootStrapHelper.getAnalystRole("Analyst");
        employeeRoleService.save(analyst);
        EmployeeRole officer = BootStrapHelper.getOfficerRole("Officer");
        employeeRoleService.save(officer);
        EmployeeRole qualityAssurance = BootStrapHelper.getQARole("Quality Assurarer");
        employeeRoleService.save(qualityAssurance);
        EmployeeRole approver = BootStrapHelper.getApproverRole("Approver");
        employeeRoleService.save(approver);
        EmployeeRole secondLevelApprover = BootStrapHelper.getSecondApproverRole("Second Level Approver");
        employeeRoleService.save(secondLevelApprover);
        EmployeeRole manager = BootStrapHelper.getManagerRole("Manager");
        employeeRoleService.save(manager);
        
    }

    @Test
    public void testScenarioB() {

        EmployeeRole superAdmin = employeeRoleService.findByDescription("Super Administrator");
        Employee superAdministrator = BootStrapHelper.getEmployee(superAdmin, RandomStringUtils.randomNumeric(7), "s2028398", "South Africa", "Terry", "Ramurebiwa", "Terry Khudani", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0694292042", "Superadmin@sars.gov.za", "0694292042", "0694292042");
        employeeService.save(superAdministrator);

//        EmployeeRole adminRole = employeeRoleService.findByDescription("Administrator");
//        Employee admin = BootStrapHelper.getEmployee(adminRole, RandomStringUtils.randomNumeric(7), "s2030702", "South Africa", "Nicholas", "Rabalao", "", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669657728", "Admin@sars.gov.za", "0669657728", "0669657728");
//        employeeService.save(admin);
//
//        EmployeeRole initiatorRole = employeeRoleService.findByDescription("Initiator");
//        Employee initiator = BootStrapHelper.getEmployee(initiatorRole, RandomStringUtils.randomNumeric(7), "s2026080", "South Africa", "Andile", "Qumbisa", "Test Admin", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669657728", "Aqumbisa@sars.gov.za", "0669657728", "0669657738");
//        employeeService.save(initiator);
//
//        EmployeeRole subjectRole = employeeRoleService.findByDescription("Subject");
//        Employee subject0 = BootStrapHelper.getEmployee(subjectRole, RandomStringUtils.randomNumeric(7), "s2025758", "South Africa", "Chris", "Maponya", "Chris Maponya", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669687728", "maponya@sars.gov.za", "0669657728", "0669697728");
//        employeeService.save(subject0);
//
//        EmployeeRole analyistRole = employeeRoleService.findByDescription("Analyst");
//        Employee analyist = BootStrapHelper.getEmployee(analyistRole, RandomStringUtils.randomNumeric(7), "s2024776", "South Africa", "Mzamo", "Qumbisa", "Mzamo", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669657728", "mzamo@sars.gov.za", "0619657728", "0619657728");
//        employeeService.save(analyist);
//
//        EmployeeRole officerRole = employeeRoleService.findByDescription("Officer");
//        Employee officer = BootStrapHelper.getEmployee(officerRole, RandomStringUtils.randomNumeric(7), "s2024926", "South Africa", "Ntethe", "Qumbisa", "Ntethe", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669657728", "ntethe@sars.gov.za", "0609657728", "0609657728");
//        employeeService.save(officer);
//
//        EmployeeRole qaRole = employeeRoleService.findByDescription("Quality Assurarer");
//        Employee qa = BootStrapHelper.getEmployee(qaRole, RandomStringUtils.randomNumeric(7), "s2028758", "South Africa", "Aubrey", "Jiyeza", "Aubrey", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669607728", "aubrey@sars.gov.za", "0669607728", "0669690728");
//        employeeService.save(qa);
//
//        EmployeeRole approverRole = employeeRoleService.findByDescription("Approver");
//        Employee approver = BootStrapHelper.getEmployee(approverRole, RandomStringUtils.randomNumeric(7), "s2024736", "South Africa", "Sphetho", "Qumbisa", "Sphetho", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0669652728", "sphetho@sars.gov.za", "0619657768", "0619657768");
//        employeeService.save(approver);
//
//        EmployeeRole secondLevelApproverRole = employeeRoleService.findByDescription("Second Level Approver");
//        Employee secondLevelApprover = BootStrapHelper.getEmployee(secondLevelApproverRole, RandomStringUtils.randomNumeric(7), "s2025555", "South Africa", "Anathy", "Qumbisa", "Anathy", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0679652728", "anathy@sars.gov.za", "0610657768", "0610657768");
//        employeeService.save(secondLevelApprover);
//
//        EmployeeRole managerRole = employeeRoleService.findByDescription("Manager");
//        Employee manager = BootStrapHelper.getEmployee(managerRole, RandomStringUtils.randomNumeric(7), "s2024716", "South Africa", "Ntethe", "Qumbisa", "Ntethe", RandomStringUtils.randomNumeric(13), RandomStringUtils.randomNumeric(13), "0668657728", "ntethe@sars.gov.za", "0609657828", "0609657788");
//        employeeService.save(manager);
    }

    @Test
    @Ignore
    public void testScenarioC() {
        Employee employee = employeeService.findBySid("s2025758");
        Vetting vetting = new Vetting();
        vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        vetting.setCreatedBy("s2026080");
        vetting.setCreatedDate(new Date());
        vetting.setEmployee(employee);
//        vetting.setManagedIntegrityEvaluation(BootStrapHelper.getManagedIntegrityEvaluation("HKL Credit Check", "info@hkl.co.za", "Absa", "0722884766"));
        vetting.setScreening(BootStrapHelper.getScreening());
        vetting.setScreeningDeclaration(BootStrapHelper.getScreeningDeclaration("Accountant"));
        vetting.setIncomeExpenditure(BootStrapHelper.getIncomeExpenditure());
//        vetting.setZ204Form(BootStrapHelper.getZ204Form());
        vettingService.save(vetting);
    }

    @Test
    @Ignore
    public void testScenarioD() {
        Employee employee = employeeService.findBySid("s1015758");
        Vetting vetting = new Vetting();
        vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        vetting.setCreatedBy("s2026080");
        vetting.setCreatedDate(new Date());
        vetting.setEmployee(employee);
//        vetting.setManagedIntegrityEvaluation(BootStrapHelper.getManagedIntegrityEvaluation("HKL Credit Check", "info@hkl.co.za", "Vodacom", "0722884766"));
        vetting.setScreening(BootStrapHelper.getScreening());
        vetting.setScreeningDeclaration(BootStrapHelper.getScreeningDeclaration("Accountant"));
        vetting.setIncomeExpenditure(BootStrapHelper.getIncomeExpenditure());
//        vetting.setZ204Form(BootStrapHelper.getZ204Form());
        vettingService.save(vetting);
    }

    @Test
    @Ignore
    public void testScenarioE() {
        Employee employee = employeeService.findBySid("s1025758");
        Vetting vetting = new Vetting();
        vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        vetting.setCreatedBy("s2026080");
        vetting.setCreatedDate(new Date());
        vetting.setEmployee(employee);
//        vetting.setManagedIntegrityEvaluation(BootStrapHelper.getManagedIntegrityEvaluation("HKL Credit Check", "info@hkl.co.za", "SABC", "0722884766"));
        vetting.setScreening(BootStrapHelper.getScreening());
        vetting.setScreeningDeclaration(BootStrapHelper.getScreeningDeclaration("Accountant"));
        vetting.setIncomeExpenditure(BootStrapHelper.getIncomeExpenditure());
//        vetting.setZ204Form(BootStrapHelper.getZ204Form());
        vettingService.save(vetting);
    }

    @Test
    @Ignore
    public void testScenarioF() {
        Employee employee = employeeService.findBySid("s1035758");
        Vetting vetting = new Vetting();
        vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        vetting.setCreatedBy("s2026080");
        vetting.setCreatedDate(new Date());
        vetting.setEmployee(employee);
//        vetting.setManagedIntegrityEvaluation(BootStrapHelper.getManagedIntegrityEvaluation("HKL Credit Check", "info@hkl.co.za", "Capitec Bank", "0722884766"));
        vetting.setScreening(BootStrapHelper.getScreening());
        vetting.setScreeningDeclaration(BootStrapHelper.getScreeningDeclaration("Accountant"));
        vetting.setIncomeExpenditure(BootStrapHelper.getIncomeExpenditure());
//        vetting.setZ204Form(BootStrapHelper.getZ204Form());
        vettingService.save(vetting);
    }

    @Test
    @Ignore
    public void testScenarioG() {
//        EmailNotification emailNotification = BootStrapHelper.getEmailNotification(NotificationType.EMAIL_TO_SUBJECT, "Email To Subject", "You are required to complete and submit your security vetting forms and supporting documents within 30 calendar days of this communication.", "To proceed, click", "(this link must take you to your vetting portal)", "");
//        emailNotificationService.save(emailNotification);
//        EmailNotification emailNotification2 = BootStrapHelper.getEmailNotification(NotificationType.REMINDER_TO_SUBJECT, "Reminder to subject", "Please note that you are currently in contravention of the vetting policy due to non-submission of your vetting forms and documents as were required on", " ", " ", " ");
//        emailNotificationService.save(emailNotification2);
//        EmailNotification emailNotification3 = BootStrapHelper.getEmailNotification(NotificationType.REMINDER_TO_LINE_MANAGER, "Reminder to Line manager", "As line-manager of", "you are hereby requested to ensure compliance within five (5) working days from receipt of this reminder", "Should", "fail to submit his/her vetting forms and supporting documents on the extended deadline, you (line manager) will be required to initiate disciplinary action.");
//        emailNotificationService.save(emailNotification3);
//        EmailNotification emailNotification4 = BootStrapHelper.getEmailNotification(NotificationType.REMINDER_OF_EXPIRED_CLEARENCE, "Clearanc expiration Reminder", "Your security clearance will expire soon. You are required to complete and submit your security vetting forms and supporting documents within 30 calendar days.  To proceed, click HERE (this links must take you to your vetting portal)", " ", " ", " ");
//        emailNotificationService.save(emailNotification4);
//        EmailNotification emailNotification5 = BootStrapHelper.getEmailNotification(NotificationType.ALLOCATE_VETTING, "Vetting alloction", "This serves to notify you that", "has allocation vetting to you, on", " ", " ");
//        emailNotificationService.save(emailNotification5);
//        EmailNotification emailNotification6 = BootStrapHelper.getEmailNotification(NotificationType.SEND_TO_APPROVER, "Send for approval", "This serves to notify you that", "has send vetting for approval", " ", " ");
//        emailNotificationService.save(emailNotification6);
//        EmailNotification emailNotification7 = BootStrapHelper.getEmailNotification(NotificationType.VETTING_CONCLUDED, "Vetting concluded", "Your vetting investigation has been concluded.  Click HERE (when the employee clicks “HERE” they will be directed to their own profile and to the vetting outcomes folder) to view the outcome of your vetting process", "", " ", " ");
//        emailNotificationService.save(emailNotification7);
//        EmailNotification emailNotification8 = BootStrapHelper.getEmailNotification(NotificationType.DENIED_VETTING, "Vetting denied", "This serves to notify you that", "has sent a denial vetting to you", " ", " ");
//        emailNotificationService.save(emailNotification8);
//        EmailNotification emailNotification9 = BootStrapHelper.getEmailNotification(NotificationType.REWORK, "Vetting Rework", "This serves to notify you that", "has sent vetting back for rework", " ", " ");
//        emailNotificationService.save(emailNotification9);
//        EmailNotification emailNotification10 = BootStrapHelper.getEmailNotification(NotificationType.ESCALATION_MAIL, "Vetting Escalation", "Please note vetting processing has been escalated to you for allocation", "", " ", " ");
//        emailNotificationService.save(emailNotification10);
//        EmailNotification emailNotification11 = BootStrapHelper.getEmailNotification(NotificationType.SUBMITTED, "Vetting Submitted", "Kindly note that vetting forms/documents were submitted by the following employee:", "Please click", "here to access the report.", " ");
//        emailNotificationService.save(emailNotification11);
//        EmailNotification emailNotification12 = BootStrapHelper.getEmailNotification(NotificationType.ANALYST_REWORK, "Analyst Rework", "Kindly note that there are outstanding documents/information required of you, which need to be submitted, in order for you to be compliant with the Vetting Policy. ", "Please click", "here to access the report.", " ");
//        emailNotificationService.save(emailNotification12);
//        EmailNotification emailNotification13 = BootStrapHelper.getEmailNotification(NotificationType.DOCUMENTS, "Submission of outstanding documents", "Kindly note that vetting forms/documents were submitted by the following employee:", "Please click", "here to access the report.", " ");
//        emailNotificationService.save(emailNotification13);
//        EmailNotification emailNotification14 = BootStrapHelper.getEmailNotification(NotificationType.ALLOCATED_OFFICER, "Officer Allocation", "Kindly note that a vetting file has been submitted to you for further processing. ", "Please click", "here to access the report.", " ");
//        emailNotificationService.save(emailNotification14);
        EmailNotification emailNotification15 = BootStrapHelper.getEmailNotification(NotificationType.APPROVER_REWORK, "Additional information requested for approval of vetting investigations", "Kindly note that additional information is requested to process this vetting application.", "Please click on this link", "to access the application and attend to the relevant QA matters. ", " ");
        emailNotificationService.save(emailNotification15);
        EmailNotification emailNotification16 = BootStrapHelper.getEmailNotification(NotificationType.ADDITINAL_REQUEST, " Additional Infomation Requested from Subject", "This serves to notify you that", "This serves to notify you that", "has requested the following information for processing of your vetting investigation", "[Please click here to access the request]");
        emailNotificationService.save(emailNotification16);

    }

    @Test
    public void testScenarioK() {

        String message = "<p>Dear var_employee <br/><br/></p><p>You are required to complete and submit your security vetting forms and supporting documents within 30 calendar days of this communication.<br/><br/></p><p>To proceed,<a href='https://ecentralqa.sars.gov.za/vetting/'>click here</a> (this link must take you to your vetting portal)<br/><br/></p><p>Kind regards<br/>emp_var<br/><a href='https://ecentralqa.sars.gov.za/vetting/'>click to access vetting application</a> <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification = BootStrapHelper.getEnhanceEmailNotification(NotificationType.EMAIL_TO_SUBJECT, "Email To Subject", message);
        emailNotificationService.save(emailNotification);

        String message1 = "<p>Dear var_employee <br/><br/></p><p>Please note that you are currently in contravention of the vetting policy due to non-submission of your vetting forms and documents as were required on vatting_date<br/><br/></p><p>Please <a href='https://ecentralqa.sars.gov.za/vetting/'>click here</a> to access the report.<br/><br/></p><p>Kind regards<br/>SARS Vetting <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification1 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.REMINDER_TO_SUBJECT, "Reminder to subject", message1);
        emailNotificationService.save(emailNotification1);

        String message2 = "<p>Dear emp_var <br/><br/></p><p>As line-manager of vatted_emp you are hereby requested to ensure compliance within five (5) working days from receipt of this reminder<br/><br/></p><p>Should vatted_emp fail to submit his/her vetting forms and supporting documents on the extended deadline, you (line manager) will be required to initiate disciplinary action.<br/><br/></p><p>Please <a href='https://ecentralqa.sars.gov.za/vetting/'>click here</a> to access the report.<br/><br/></p><p>Kind regards<br/>SARS Vetting <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification2 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.REMINDER_TO_LINE_MANAGER, "Reminder to Line manager", message2);
        emailNotificationService.save(emailNotification2);

        String message3 = "<p>Dear emp_var <br/><br/></p><p>Your security clearance will expire soon. You are required to complete and submit your security vetting forms and supporting documents within 30 calendar days. To proceed, <a href='https://ecentralqa.sars.gov.za/vetting/'>click here</a><br/><br/></p><p>Kind regards<br/>SARS Vetting <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification3 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.REMINDER_OF_EXPIRED_CLEARENCE, "Clearanc expiration Reminder", message3);
        emailNotificationService.save(emailNotification3);

        String message4 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that a vetting file has been submitted to you for further processing.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click</a> here to access the report<br/><br/></p><p>Kind regards<br/>emp_var<br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification4 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.ALLOCATED_OFFICER, "Vetting alloction", message4);
        emailNotificationService.save(emailNotification4);

        String message5 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that vetting forms/documents were submitted by the following employee:<br/><br/></p><p><span style=\"color:black\">Employee/subject full names: vet_emp<br/>Employee SID number: emp_sid<br/>Employee ID number: empIdNum</span><br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report<br/><br/></p><p>Kind regards<br/>vet_emp <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification5 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.SUBMITTED, "Vetting Submitted", message5);
        emailNotificationService.save(emailNotification5);

        String message6 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that vetting forms/documents were submitted by the following employee:<br/><br/></p><p><span style=\"color:black\">Employee/subject full names: vet_emp<br/>Employee SID number: emp_sid<br/>Employee ID number: empIdNum</span><br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report<br/><br/></p><p>Kind regards<br/>vet_emp <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification6 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.DOCUMENTS, "Submission of outstanding documents", message6);
        emailNotificationService.save(emailNotification6);

//        String message7 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that a vetting file has been submitted to you for further processing.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report<br/><br/></p><p>Kind regards<br/>emp_var <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
//        EmailNotification emailNotification7 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.ALLOCATED_OFFICER, "Officer Allocation", message7);
//        emailNotificationService.save(emailNotification7);

        String message8 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that there are outstanding documents/information required of you, which need to be submitted, in order for you to be compliant with the Vetting Policy.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report<br/><br/></p><p>Kind regards<br/>vat_emp_officer <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification8 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.REWORK, "Vetting Rework", message8);
        emailNotificationService.save(emailNotification8);

        String message9 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that a vetting file has been submitted to you for approval.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report<br/><br/></p><p>Kind regards<br/>vet_officer <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification9 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.SEND_TO_APPROVER, "Send for approval", message9);
        emailNotificationService.save(emailNotification9);

        String message10 = "<p>Dear emp_var <br/><br/></p><p>This serves to notify you that vet_officer has sent a denial vetting to you.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report<br/><br/></p><p>Kind regards<br/>vet_officer <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification10 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.DENIED_VETTING, "Vetting denied", message10);
        emailNotificationService.save(emailNotification10);

        String message11 = "<p>Dear emp_var <br/><br/></p><p>Your vetting investigation has been concluded. <a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to view the outcome of your vetting process<br/><br/></p><p>Kind regards<br/>vet_officer <br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification11 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.VETTING_CONCLUDED, "Vetting concluded", message11);
        emailNotificationService.save(emailNotification11);

        String message12 = "<p>Dear Administrator <br/><br/></p><p>Please note vetting processing has been escalated to you for allocation.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click here</a> to access the report.<br/><br/></p><p>Kind regards<br/>SARS Vetting<br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification12 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.ESCALATION_MAIL, "Vetting Escalation", message12);
        emailNotificationService.save(emailNotification12);

        String message13 = "<p>Dear emp_var <br/><br/></p><p>Kindly note that a vetting file has been re-allocated to you for further processing.<br/><br/></p><p><a href='https://ecentralqa.sars.gov.za/vetting/'>Please click</a> here to access the report<br/><br/></p><p>Kind regards<br/>vet_officer<br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification13 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.RE_ALLOCATION, "Vetting alloction", message13);
        emailNotificationService.save(emailNotification13);

        String message14 = "<p>Dear emp_var <br/><br/></p><p>Your approved vetting investigation has a certificate feedback uploaded.<br/><br/></p><p>To proceed,<a href='https://ecentralqa.sars.gov.za/vetting/'>click here</a> to view the outcome of your vetting process<br/><br/></p><p>Kind regards<br/>vet_officer<br/><br/></p><p>NB: This is an automated email, do not reply.</p>";
        EmailNotification emailNotification14 = BootStrapHelper.getEnhanceEmailNotification(NotificationType.CERTIFICATE, "FeedBack SSA/SARS", message14);
        emailNotificationService.save(emailNotification14);
    }

    @Test
    public void testScenarioH() {

//Requires eligibility for access to classified information, areas classified as national key points
        JobRole jobRole = BootStrapHelper.getJobRole("Requires eligibility for access to classified information, areas classified as national key points");
        Responsibility responsibility1 = new Responsibility();
        responsibility1.setCreatedBy("S2028398");
        responsibility1.setCreatedDate(new Date());
        responsibility1.setDescription("Position requires access to Top Secret information and NKP");
        responsibility1.setResponsibilityValue(30);
        responsibility1.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole.addResponsibilities(responsibility1);

        Responsibility responsibility2 = new Responsibility();
        responsibility2.setCreatedBy("S2028398");
        responsibility2.setCreatedDate(new Date());
        responsibility2.setDescription("Position requires access to Secret and Confidential information");
        responsibility2.setResponsibilityValue(20);
        responsibility2.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole.addResponsibilities(responsibility2);

        Responsibility responsibility3 = new Responsibility();
        responsibility3.setCreatedBy("S2028398");
        responsibility3.setCreatedDate(new Date());
        responsibility3.setDescription("Position requires eligibility for access to private or proprietary information with adverse impact to national security");
        responsibility3.setResponsibilityValue(20);
        responsibility3.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole.addResponsibilities(responsibility3);

        Responsibility responsibility4 = new Responsibility();
        responsibility4.setCreatedBy("S2028398");
        responsibility4.setCreatedDate(new Date());
        responsibility4.setDescription("Position does not require eligibility for access to classified information");
        responsibility4.setResponsibilityValue(10);
        responsibility4.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole.addResponsibilities(responsibility4);

        Responsibility responsibility5 = new Responsibility();
        responsibility5.setCreatedBy("S2028398");
        responsibility5.setCreatedDate(new Date());
        responsibility5.setDescription("Position requires access to Secret and Confidential information");
        responsibility5.setResponsibilityValue(10);
        responsibility5.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole.addResponsibilities(responsibility4);
        jobRole.setJobRoleType(JobRoleType.NATIONAL);
        jobRoleService.save(jobRole);

        //Duties involving protecting the country’s borders, ports, critical infrastructure or key resources 
        JobRole jobRole1 = BootStrapHelper.getJobRole("Duties involving protecting the country’s borders, ports, critical infrastructure or key resources");
        Responsibility responsibility01 = new Responsibility();
        responsibility01.setCreatedBy("S2028398");
        responsibility01.setCreatedDate(new Date());
        responsibility01.setDescription("Independent responsibility for, and the ability to act without detection to compromise or exploit, the protection, control, and safety of the nation’s borders and ports");
        responsibility01.setResponsibilityValue(30);
        responsibility01.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole1.addResponsibilities(responsibility01);

        Responsibility responsibility02 = new Responsibility();
        responsibility02.setCreatedBy("S2028398");
        responsibility02.setCreatedDate(new Date());
        responsibility02.setDescription("Independent responsibility for protecting NKP");
        responsibility02.setResponsibilityValue(30);
        responsibility02.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole1.addResponsibilities(responsibility02);

        Responsibility responsibility03 = new Responsibility();
        responsibility03.setCreatedBy("S2028398");
        responsibility03.setCreatedDate(new Date());
        responsibility03.setDescription("Independent responsibility for critical systems");
        responsibility03.setResponsibilityValue(30);
        responsibility03.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole1.addResponsibilities(responsibility03);

        Responsibility responsibility04 = new Responsibility();
        responsibility04.setCreatedBy("S2028398");
        responsibility04.setCreatedDate(new Date());
        responsibility04.setDescription("Independent responsibility for, and the ability to act independently without detection to compromise or exploit, the design, installation, operation, or maintenance of NKP systems or programs");
        responsibility04.setResponsibilityValue(30);
        responsibility04.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole1.addResponsibilities(responsibility04);

        Responsibility responsibility05 = new Responsibility();
        responsibility05.setCreatedBy("S2028398");
        responsibility05.setCreatedDate(new Date());
        responsibility05.setDescription("Major and immediate responsibility for, and the ability to act independently without detection to compromise or exploit, the protection, control, and safety of the nation’s borders and ports, immigration or customs control or policies");
        responsibility05.setResponsibilityValue(30);
        responsibility05.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole1.addResponsibilities(responsibility05);

        Responsibility responsibility06 = new Responsibility();
        responsibility06.setCreatedBy("S2028398");
        responsibility06.setCreatedDate(new Date());
        responsibility06.setDescription("Major and immediate responsibility, with the ability to act independently, for protecting NKP");
        responsibility06.setResponsibilityValue(30);
        responsibility06.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole1.addResponsibilities(responsibility06);

        Responsibility responsibility07 = new Responsibility();
        responsibility07.setCreatedBy("S2028398");
        responsibility07.setCreatedDate(new Date());
        responsibility07.setDescription("Responsibility for the protection, control, and safety of the nation’s borders and ports, immigration or customs control or policies – with moderate autonomy or ability for independent action");
        responsibility07.setResponsibilityValue(20);
        responsibility07.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole1.addResponsibilities(responsibility07);

        Responsibility responsibility08 = new Responsibility();
        responsibility08.setCreatedBy("S2028398");
        responsibility08.setCreatedDate(new Date());
        responsibility08.setDescription("Responsibility for critical systems – with moderate autonomy or ability for independent action");
        responsibility08.setResponsibilityValue(20);
        responsibility08.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole1.addResponsibilities(responsibility08);

        Responsibility responsibility09 = new Responsibility();
        responsibility09.setCreatedBy("S2028398");
        responsibility09.setCreatedDate(new Date());
        responsibility09.setDescription("Involved in the design, installation, operation, or maintenance of critical infrastructure systems or programs – with moderate autonomy or ability for independent action");
        responsibility09.setResponsibilityValue(20);
        responsibility09.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole1.addResponsibilities(responsibility09);

        Responsibility responsibility10 = new Responsibility();
        responsibility10.setCreatedBy("S2028398");
        responsibility10.setCreatedDate(new Date());
        responsibility10.setDescription("Duties are of such a minor or inconsequential nature, with autonomy limited by supervision, and/or internal controls are so significant that there is no reasonable expectation that there could be significant or serious damage to national security");
        responsibility10.setResponsibilityValue(10);
        responsibility10.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole1.addResponsibilities(responsibility10);
        jobRole1.setJobRoleType(JobRoleType.NATIONAL);
        jobRoleService.save(jobRole1);

        //Access to critical facilities or information systems 
        JobRole jobRole2 = BootStrapHelper.getJobRole("Access to critical facilities or information systems ");
        Responsibility responsibility001 = new Responsibility();
        responsibility001.setCreatedBy("S2028398");
        responsibility001.setCreatedDate(new Date());
        responsibility001.setDescription("Unlimited or access to and/or control of access to designated restricted areas or facilities, property, or information systems");
        responsibility001.setResponsibilityValue(30);
        responsibility001.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole2.addResponsibilities(responsibility001);

        Responsibility responsibility002 = new Responsibility();
        responsibility002.setCreatedBy("S2028398");
        responsibility002.setCreatedDate(new Date());
        responsibility002.setDescription("Access to and/or controlling access to designated restricted areas, property, or information systems that house materials or information");
        responsibility002.setResponsibilityValue(30);
        responsibility002.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole2.addResponsibilities(responsibility002);

        Responsibility responsibility003 = new Responsibility();
        responsibility003.setCreatedBy("S2028398");
        responsibility003.setCreatedDate(new Date());
        responsibility003.setDescription("Access to and/or controlling access to designated restricted areas or facilities, property, information systems that maintain materials");
        responsibility003.setResponsibilityValue(20);
        responsibility003.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole2.addResponsibilities(responsibility003);

        Responsibility responsibility004 = new Responsibility();
        responsibility004.setCreatedBy("S2028398");
        responsibility004.setCreatedDate(new Date());
        responsibility004.setDescription("Duties are of such a minor or inconsequential nature and/or internal controls are so significant that there is no reasonable expectation that there could be significant or serious damage to national security");
        responsibility004.setResponsibilityValue(10);
        responsibility004.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole2.addResponsibilities(responsibility004);
        jobRole2.setJobRoleType(JobRoleType.NATIONAL);
        jobRoleService.save(jobRole2);

        //"Economic and financial policy-making or policy-determining responsibility 
        JobRole jobRole3 = BootStrapHelper.getJobRole("Duties involving protecting the country’s borders, ports, critical infrastructure or key resources");
        Responsibility responsibility0001 = new Responsibility();
        responsibility0001.setCreatedBy("S2028398");
        responsibility0001.setCreatedDate(new Date());
        responsibility0001.setDescription("Independent responsibility for economic policy-making or policy-determining");
        responsibility0001.setResponsibilityValue(30);
        responsibility0001.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole3.addResponsibilities(responsibility0001);

        Responsibility responsibility0002 = new Responsibility();
        responsibility0002.setCreatedBy("S2028398");
        responsibility0002.setCreatedDate(new Date());
        responsibility0002.setDescription("Direct involvement with economic and financial policy making or policy determining");
        responsibility0002.setResponsibilityValue(30);
        responsibility0002.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole3.addResponsibilities(responsibility0002);

        Responsibility responsibility0003 = new Responsibility();
        responsibility0003.setCreatedBy("S2028398");
        responsibility0003.setCreatedDate(new Date());
        responsibility0003.setDescription("Serves in advisory role");
        responsibility0003.setResponsibilityValue(30);
        responsibility0003.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole3.addResponsibilities(responsibility0003);

        Responsibility responsibility0004 = new Responsibility();
        responsibility0004.setCreatedBy("S2028398");
        responsibility0004.setCreatedDate(new Date());
        responsibility0004.setDescription("Serves in advisory role ");
        responsibility0004.setResponsibilityValue(20);
        responsibility0004.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole3.addResponsibilities(responsibility0004);

        Responsibility responsibility0005 = new Responsibility();
        responsibility0005.setCreatedBy("S2028398");
        responsibility0005.setCreatedDate(new Date());
        responsibility0005.setDescription("Substantial responsibility supporting individuals directly involved with economic and financial policy making or policy");
        responsibility0005.setResponsibilityValue(20);
        responsibility0005.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole3.addResponsibilities(responsibility0005);

        Responsibility responsibility0006 = new Responsibility();
        responsibility0006.setCreatedBy("S2028398");
        responsibility0006.setCreatedDate(new Date());
        responsibility0006.setDescription("Developing policies, regulations and/or rule-making for and financial policy making or policy determining");
        responsibility0006.setResponsibilityValue(20);
        responsibility0006.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole3.addResponsibilities(responsibility0006);

        Responsibility responsibility0007 = new Responsibility();
        responsibility0007.setCreatedBy("S2028398");
        responsibility0007.setCreatedDate(new Date());
        responsibility0007.setDescription("Serves in advisory role to senior officials who complete one or more of the above duties");
        responsibility0007.setResponsibilityValue(10);
        responsibility0007.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole3.addResponsibilities(responsibility0007);

        Responsibility responsibility0008 = new Responsibility();
        responsibility0008.setCreatedBy("S2028398");
        responsibility0008.setCreatedDate(new Date());
        responsibility0008.setDescription("Other duties related to policy with no potential to cause significant or serious damage");
        responsibility0008.setResponsibilityValue(10);
        responsibility0008.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole3.addResponsibilities(responsibility0008);

        Responsibility responsibility0009 = new Responsibility();
        responsibility0009.setCreatedBy("S2028398");
        responsibility0009.setCreatedDate(new Date());
        responsibility0009.setDescription("Policy and program responsibility is related to public trust in the efficiency and integrity of the service rather than national security ");
        responsibility0009.setResponsibilityValue(10);
        responsibility0009.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole3.addResponsibilities(responsibility0009);
        jobRole3.setJobRoleType(JobRoleType.NATIONAL);
        jobRoleService.save(jobRole3);

        //Unclassified information (e.g. private or proprietary information) significant to national security 
        JobRole jobRole4 = BootStrapHelper.getJobRole("Unclassified information (e.g. private or proprietary information) significant to national security");
        Responsibility responsibility00001 = new Responsibility();
        responsibility00001.setCreatedBy("S2028398");
        responsibility00001.setCreatedDate(new Date());
        responsibility00001.setDescription("Unlimited access to and control over unclassified information, which may include private or proprietary, but only where the unauthorized disclosure of that information could cause grave damage to national security");
        responsibility00001.setResponsibilityValue(30);
        responsibility00001.setDamageLevel(DamageLevel.EXCEPTION);
        jobRole4.addResponsibilities(responsibility00001);

        Responsibility responsibility00002 = new Responsibility();
        responsibility00002.setCreatedBy("S2028398");
        responsibility00002.setCreatedDate(new Date());
        responsibility00002.setDescription("Limited access to and control over unclassified information, which may include private or proprietary information, but only where the unauthorized disclosure of that information could cause significant or serious damage to national security ");
        responsibility00002.setResponsibilityValue(20);
        responsibility00002.setDamageLevel(DamageLevel.SIGNIFICANT);
        jobRole4.addResponsibilities(responsibility00002);

        Responsibility responsibility00003 = new Responsibility();
        responsibility00003.setCreatedBy("S2028398");
        responsibility00003.setCreatedDate(new Date());
        responsibility00003.setDescription("Access to unclassified information is minimal and is of such a minor or inconsequential nature and/or internal controls are so significant that there is no reasonable expectation that there could be significant or serious damage to national security");
        responsibility00003.setResponsibilityValue(10);
        responsibility00003.setDamageLevel(DamageLevel.NO_MATERIAL);
        jobRole4.addResponsibilities(responsibility00003);
        jobRole4.setJobRoleType(JobRoleType.NATIONAL);
        jobRoleService.save(jobRole4);

        //Role not Applicable. Move to Step 2
        JobRole jobRole5 = BootStrapHelper.getJobRole("Role not Applicable. Move to Step 2");
        Responsibility responsibilityrRole = new Responsibility();
        responsibilityrRole.setCreatedBy("S2028398");
        responsibilityrRole.setCreatedDate(new Date());
        responsibilityrRole.setDescription("Role not Applicable. Move to Step 2");
        responsibilityrRole.setResponsibilityValue(0);
        responsibilityrRole.setDamageLevel(DamageLevel.NONE);
        jobRole5.addResponsibilities(responsibilityrRole);
        jobRole5.setJobRoleType(JobRoleType.NATIONAL);
        jobRoleService.save(jobRole5);

        //SUSTAINABILITY
        // Government operations – rulemaking, policy, and major program responsibility
        JobRole sustainabilityRole = BootStrapHelper.getJobRole(" Government operations – rulemaking, policy, and major program responsibility");
        Responsibility responsibilityrSustainability = new Responsibility();
        responsibilityrSustainability.setCreatedBy("S2028398");
        responsibilityrSustainability.setCreatedDate(new Date());
        responsibilityrSustainability.setDescription("Senior management official for critical government/organisational programs, the compromise of which could result in grave damage to the public’s trust");
        responsibilityrSustainability.setResponsibilityValue(30);
        responsibilityrSustainability.setDamageLevel(DamageLevel.EXCEPTION);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability);

        Responsibility responsibilityrSustainability1 = new Responsibility();
        responsibilityrSustainability1.setCreatedBy("S2028398");
        responsibilityrSustainability1.setCreatedDate(new Date());
        responsibilityrSustainability1.setDescription("Senior management duties or assignments that do not rise to the level of an automatic High-Risk condition");
        responsibilityrSustainability1.setResponsibilityValue(30);
        responsibilityrSustainability1.setDamageLevel(DamageLevel.EXCEPTION);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability1);

        Responsibility responsibilityrSustainability2 = new Responsibility();
        responsibilityrSustainability2.setCreatedBy("S2028398");
        responsibilityrSustainability2.setCreatedDate(new Date());
        responsibilityrSustainability2.setDescription("Substantial responsibility for approving regulations and/or rule-making agendas for significant government/organisational programs impacting the public’s trust");
        responsibilityrSustainability2.setResponsibilityValue(30);
        responsibilityrSustainability2.setDamageLevel(DamageLevel.EXCEPTION);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability2);

        Responsibility responsibilityrSustainability3 = new Responsibility();
        responsibilityrSustainability3.setCreatedBy("S2028398");
        responsibilityrSustainability3.setCreatedDate(new Date());
        responsibilityrSustainability3.setDescription("Independent responsibility for planning or approving continuity of government/organisational operations");
        responsibilityrSustainability3.setResponsibilityValue(30);
        responsibilityrSustainability3.setDamageLevel(DamageLevel.EXCEPTION);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability3);

        Responsibility responsibilityrSustainability4 = new Responsibility();
        responsibilityrSustainability4.setCreatedBy("S2028398");
        responsibilityrSustainability4.setCreatedDate(new Date());
        responsibilityrSustainability4.setDescription("Sets policy for significant organisational/government programs impacting the public’s trust");
        responsibilityrSustainability4.setResponsibilityValue(30);
        responsibilityrSustainability4.setDamageLevel(DamageLevel.EXCEPTION);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability4);

        Responsibility responsibilityrSustainability5 = new Responsibility();
        responsibilityrSustainability5.setCreatedBy("S2028398");
        responsibilityrSustainability5.setCreatedDate(new Date());
        responsibilityrSustainability5.setDescription("Serves in advisory role to senior management who complete one or more of the above duties");
        responsibilityrSustainability5.setResponsibilityValue(30);
        responsibilityrSustainability5.setDamageLevel(DamageLevel.EXCEPTION);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability5);

        Responsibility responsibilityrSustainability6 = new Responsibility();
        responsibilityrSustainability6.setCreatedBy("S2028398");
        responsibilityrSustainability6.setCreatedDate(new Date());
        responsibilityrSustainability6.setDescription("Mid-level management duties or assignments");
        responsibilityrSustainability6.setResponsibilityValue(20);
        responsibilityrSustainability6.setDamageLevel(DamageLevel.IMPACT);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability6);

        Responsibility responsibilityrSustainability7 = new Responsibility();
        responsibilityrSustainability7.setCreatedBy("S2028398");
        responsibilityrSustainability7.setCreatedDate(new Date());
        responsibilityrSustainability7.setDescription("Participates in policy development that have   significant impact in an influential way");
        responsibilityrSustainability7.setResponsibilityValue(20);
        responsibilityrSustainability7.setDamageLevel(DamageLevel.IMPACT);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability7);

        Responsibility responsibilityrSustainability8 = new Responsibility();
        responsibilityrSustainability8.setCreatedBy("S2028398");
        responsibilityrSustainability8.setCreatedDate(new Date());
        responsibilityrSustainability8.setDescription("Responsible for independent or semi-independent action with moderate impact on efficiency and integrity or the organisation");
        responsibilityrSustainability8.setResponsibilityValue(20);
        responsibilityrSustainability8.setDamageLevel(DamageLevel.IMPACT);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability8);

        Responsibility responsibilityrSustainability9 = new Responsibility();
        responsibilityrSustainability9.setCreatedBy("S2028398");
        responsibilityrSustainability9.setCreatedDate(new Date());
        responsibilityrSustainability9.setDescription("Significant public contact about important government/organisational programs impacting the public’s trust");
        responsibilityrSustainability9.setResponsibilityValue(20);
        responsibilityrSustainability9.setDamageLevel(DamageLevel.IMPACT);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability9);

        Responsibility responsibilityrSustainability10 = new Responsibility();
        responsibilityrSustainability10.setCreatedBy("S2028398");
        responsibilityrSustainability10.setCreatedDate(new Date());
        responsibilityrSustainability10.setDescription("Less than moderate impact on programs");
        responsibilityrSustainability10.setResponsibilityValue(10);
        responsibilityrSustainability10.setDamageLevel(DamageLevel.LIMITED);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability10);

        Responsibility responsibilityrSustainability11 = new Responsibility();
        responsibilityrSustainability11.setCreatedBy("S2028398");
        responsibilityrSustainability11.setCreatedDate(new Date());
        responsibilityrSustainability11.setDescription("Regulation or policy input is limited to contributing to working groups or providing technical input");
        responsibilityrSustainability11.setResponsibilityValue(10);
        responsibilityrSustainability11.setDamageLevel(DamageLevel.LIMITED);
        sustainabilityRole.addResponsibilities(responsibilityrSustainability11);
        sustainabilityRole.setJobRoleType(JobRoleType.SUSTAINABILITY);
        jobRoleService.save(sustainabilityRole);
    }

    @Test
    public void testScenarioI() {
        SubArea subArea = BootStrapHelper.getSubArea("Position requires eligibility for access to private or proprietary information with adverse impact to national security ");
        subAreaService.save(subArea);
    }

    @Test
    public void testScenarioJ() {
        AdjustmentCategory adjustment = BootStrapHelper.getAdjustmentCategory("Program Scope");
        AdjustmentResponsibility responsibility = new AdjustmentResponsibility();
        responsibility.setCreatedBy("S2028398");
        responsibility.setCreatedDate(new Date());
        responsibility.setDescription("Worldwide or government wide impact");
        responsibility.setResponsibilityValue(0);
        adjustment.addResponsibilities(responsibility);

        AdjustmentResponsibility responsibility1 = new AdjustmentResponsibility();
        responsibility1.setCreatedBy("S2028398");
        responsibility1.setCreatedDate(new Date());
        responsibility1.setDescription("Multi departmental impact");
        responsibility1.setResponsibilityValue(5);
        adjustment.addResponsibilities(responsibility1);

        AdjustmentResponsibility responsibility2 = new AdjustmentResponsibility();
        responsibility2.setCreatedBy("S2028398");
        responsibility2.setCreatedDate(new Date());
        responsibility2.setDescription("Agency impact");
        responsibility2.setResponsibilityValue(10);
        adjustment.addResponsibilities(responsibility2);
        adjustmentCategoryService.save(adjustment);

        AdjustmentCategory adjustment1 = BootStrapHelper.getAdjustmentCategory("Supervision Controls");
        AdjustmentResponsibility responsibility3 = new AdjustmentResponsibility();
        responsibility3.setCreatedBy("S2028398");
        responsibility3.setCreatedDate(new Date());
        responsibility3.setDescription("Act independently in almost all areas almost all of the time ");
        responsibility.setResponsibilityValue(0);
        adjustment1.addResponsibilities(responsibility3);

        AdjustmentResponsibility responsibility4 = new AdjustmentResponsibility();
        responsibility4.setCreatedBy("S2028398");
        responsibility4.setCreatedDate(new Date());
        responsibility4.setDescription("Periodic Supervision ongoing review ability to act independently a lot of the time");
        responsibility4.setResponsibilityValue(5);
        adjustment1.addResponsibilities(responsibility4);

        AdjustmentResponsibility responsibility5 = new AdjustmentResponsibility();
        responsibility5.setCreatedBy("S2028398");
        responsibility5.setCreatedDate(new Date());
        responsibility5.setDescription("Close technical supervision ability to act independently infrequently");
        responsibility5.setResponsibilityValue(10);
        adjustment1.addResponsibilities(responsibility5);
        adjustmentCategoryService.save(adjustment1);
    }
}
