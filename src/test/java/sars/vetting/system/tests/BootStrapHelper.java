/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sars.vetting.system.common.AddressType;
import sars.vetting.system.common.DamageLevel;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.EmployeeStatus;
import sars.vetting.system.common.ExpenditureDescription;
import sars.vetting.system.common.ExpenditureType;
import sars.vetting.system.common.Gender;
import sars.vetting.system.common.IncomeType;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.PersonType;
import sars.vetting.system.common.Province;
import sars.vetting.system.common.Relationship;
import sars.vetting.system.common.SpouseType;
import sars.vetting.system.domain.Address;
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdministrationSetting;
import sars.vetting.system.domain.Area;
import sars.vetting.system.domain.AttachmentChecklist;
import sars.vetting.system.domain.CompleteVettingSetting;
import sars.vetting.system.domain.ContactDetail;
import sars.vetting.system.domain.EmailNotification;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRelative;
import sars.vetting.system.domain.Expenditure;
import sars.vetting.system.domain.FinancialDocsResponse;
import sars.vetting.system.domain.Income;
import sars.vetting.system.domain.IncomeExpenditure;
import sars.vetting.system.domain.Permission;
import sars.vetting.system.domain.Reference;
import sars.vetting.system.domain.ReportSetting;
import sars.vetting.system.domain.Screening;
import sars.vetting.system.domain.ScreeningDeclaration;
import sars.vetting.system.domain.Spouse;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.domain.InitiateVettingSetting;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.ReviewVettingSetting;
import sars.vetting.system.domain.Responsibility;
import sars.vetting.system.domain.SubArea;
import sars.vetting.system.domain.VettingApprovalSetting;
import sars.vetting.system.domain.Z204Form;

/**
 *
 * @author S2028398
 */
public class BootStrapHelper {

    public static EmployeeRole getAdminRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.TRUE);
        administrationSetting.setEmployeeRole(Boolean.TRUE);
        administrationSetting.setUsers(Boolean.TRUE);
        administrationSetting.setEscalation(Boolean.TRUE);
        administrationSetting.setNotification(Boolean.TRUE);
        administrationSetting.setSubArea(Boolean.TRUE);
        administrationSetting.setAdjustmentCategory(Boolean.TRUE);
        administrationSetting.setJobRole(Boolean.TRUE);
        administrationSetting.setReroute(Boolean.TRUE);
        administrationSetting.setVettingFiles(Boolean.TRUE);
        administrationSetting.setSuperAdministrator(Boolean.TRUE);
        administrationSetting.setUploadSsaSars(Boolean.TRUE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getSuperAdminRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.TRUE);
        administrationSetting.setEmployeeRole(Boolean.TRUE);
        administrationSetting.setUsers(Boolean.TRUE);
        administrationSetting.setEscalation(Boolean.TRUE);
        administrationSetting.setNotification(Boolean.TRUE);
        administrationSetting.setSubArea(Boolean.TRUE);
        administrationSetting.setAdjustmentCategory(Boolean.TRUE);
        administrationSetting.setJobRole(Boolean.TRUE);
        administrationSetting.setReroute(Boolean.TRUE);
        administrationSetting.setVettingFiles(Boolean.TRUE);
        administrationSetting.setSuperAdministrator(Boolean.TRUE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getInitiatorRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.TRUE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getQARole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.TRUE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getSubjectRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.TRUE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getAnalystRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.TRUE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getOfficerRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.TRUE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getApproverRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.TRUE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static EmployeeRole getSecondApproverRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.FALSE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.TRUE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }
    
     public static EmployeeRole getManagerRole(String description) {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setCreatedBy("s2028398");
        employeeRole.setCreatedDate(new Date());
        employeeRole.setDescription(description);

        AdministrationSetting administrationSetting = new AdministrationSetting();
        administrationSetting.setCreatedBy("s2028398");
        administrationSetting.setCreatedDate(new Date());
        administrationSetting.setAdministrator(Boolean.FALSE);
        administrationSetting.setEmployeeRole(Boolean.FALSE);
        administrationSetting.setUsers(Boolean.FALSE);
        administrationSetting.setEscalation(Boolean.FALSE);
        administrationSetting.setNotification(Boolean.FALSE);
        administrationSetting.setSubArea(Boolean.FALSE);
        administrationSetting.setAdjustmentCategory(Boolean.FALSE);
        administrationSetting.setJobRole(Boolean.FALSE);
        administrationSetting.setReroute(Boolean.FALSE);
        administrationSetting.setVettingFiles(Boolean.FALSE);
        administrationSetting.setSuperAdministrator(Boolean.FALSE);
        employeeRole.setAdminSetting(administrationSetting);

        CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
        completeVettingSetting.setCreatedBy("s2028398");
        completeVettingSetting.setCreatedDate(new Date());
        completeVettingSetting.setCompleteVetting(Boolean.FALSE);
        employeeRole.setCompleteVettingSetting(completeVettingSetting);

        InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
        initiateVettingSetting.setCreatedBy("s2028398");
        initiateVettingSetting.setCreatedDate(new Date());
        initiateVettingSetting.setVettingInitiation(Boolean.FALSE);
        employeeRole.setInitiateVettingSetting(initiateVettingSetting);

        ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
        reviewVettingSetting.setCreatedBy("s2028398");
        reviewVettingSetting.setCreatedDate(new Date());
        reviewVettingSetting.setAnalistReview(Boolean.FALSE);
        reviewVettingSetting.setOfficerReview(Boolean.FALSE);
        reviewVettingSetting.setQaReview(Boolean.FALSE);
        reviewVettingSetting.setManagerReview(Boolean.TRUE);
        employeeRole.setReviewVettingSetting(reviewVettingSetting);

        VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
        approvalSetting.setCreatedBy("s2028398");
        approvalSetting.setCreatedDate(new Date());
        approvalSetting.setApproveVetting(Boolean.FALSE);
        approvalSetting.setApproveSecondLevel(Boolean.FALSE);
        employeeRole.setVettingApprovalSetting(approvalSetting);

        ReportSetting reportSetting = new ReportSetting();
        reportSetting.setCreatedBy("s2028398");
        reportSetting.setCreatedDate(new Date());
        reportSetting.setReport(Boolean.TRUE);
        employeeRole.setReportSetting(reportSetting);

        Permission permission = new Permission();
        permission.setCreatedBy("s2028398");
        permission.setCreatedDate(new Date());
        permission.setAdd(Boolean.TRUE);
        permission.setDelete(Boolean.TRUE);
        permission.setRead(Boolean.TRUE);
        permission.setUpdate(Boolean.TRUE);
        permission.setWrite(Boolean.TRUE);
        employeeRole.setPermission(permission);
        return employeeRole;
    }

    public static Employee getEmployee(EmployeeRole employeeRole, String employeeNumber, String sid, String countryName, String firstname, String lastName, String fullNames, String passportNumber, String identityNumber, String cellphone, String email, String fax, String telephone) {
        Employee employee = new Employee();
        employee.setCreatedBy("s2028398");
        employee.setCreatedDate(new Date());
        employee.setEmployeeRole(employeeRole);
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);

        employee.setSid(sid);
        employee.setEmployeeNumber(employeeNumber);
        employee.setCountryName(countryName);
        employee.setFirstName(firstname);
        employee.setLastName(lastName);
        employee.setFullNames(fullNames);
        employee.setGender("Male");
        employee.setIdentityNumber(identityNumber);

        employee.setPassportNumber(passportNumber);
        employee.setBirthDate(new Date());
        employee.setProvince(Province.GAUTENG);

        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setCreatedBy("s2028398");
        contactDetail.setCreatedDate(new Date());
        contactDetail.setEmailAddress(email);
        contactDetail.setFaxNumber(fax);
        contactDetail.setCellPhoneNumber(cellphone);
        contactDetail.setTelephoneNumber(telephone);
        employee.setContactDetail(contactDetail);

        Address physicalAddress = new Address();
        physicalAddress.setCreatedBy("s2028398");
        physicalAddress.setCreatedDate(new Date());
        physicalAddress.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress.setLine1("Unit 1 Estate View");
        physicalAddress.setLine2("123 Street Road");
        physicalAddress.setLine3("Zambezi Juntion");
        physicalAddress.setArea("Pretoria");
        physicalAddress.setCode("0002");
        //employee.setResidentialAddress(physicalAddress);
        employee.addAddress(physicalAddress);

        Address postalAddress = new Address();
        postalAddress.setCreatedBy("s2028398");
        postalAddress.setCreatedDate(new Date());
        postalAddress.setAddressType(AddressType.POSTAL);
        postalAddress.setLine1("P.O Box 123");
        postalAddress.setArea("Zambezi");
        postalAddress.setCode("0002");
        //employee.setPostalAddress(postalAddress);
        employee.addAddress(postalAddress);

        Address businessAddress = new Address();
        businessAddress.setCreatedBy("s2028398");
        businessAddress.setCreatedDate(new Date());
        businessAddress.setAddressType(AddressType.BUSINESS);
        businessAddress.setLine1("P.O Box 123");
        businessAddress.setArea("Zambezi");
        businessAddress.setCode("0002");
        //employee.setBusinessAddress(businessAddress);
        employee.addAddress(businessAddress);

        return employee;
    }

    public static Screening getScreening() {
        Screening screening = new Screening();
        screening.setCreatedBy("s2026080");
        screening.setCreatedDate(new Date());

//        screening.addCriminalRecord(new CriminalRecord("Fraud", RandomStringUtils.randomNumeric(5), "Limbombo border", "Stop stealing please", Result.CLEARANCE_ISSUED, new Date()));
//        screening.addCriminalRecord(new CriminalRecord("Murder", RandomStringUtils.randomNumeric(5), "Limbombo border", "Stop stealing please", Result.CLEARANCE_ISSUED, new Date()));
//        screening.addCriminalRecord(new CriminalRecord("Attempted Murder", RandomStringUtils.randomNumeric(5), "Limbombo border", "Stop stealing please", Result.CLEARANCE_ISSUED, new Date()));
//        screening.addCriminalRecord(new CriminalRecord("Robbery", RandomStringUtils.randomNumeric(5), "Limbombo border", "Stop stealing please", Result.CLEARANCE_ISSUED, new Date()));
//        screening.addCreditRecord(new CreditRecord("FNB", 500.00, "arrangement", "Paying", "", new Date()));
//        screening.addCreditRecord(new CreditRecord("Standard Bank", 500.00, "arrangement", "Paying", Category.YES, new Date()));
//        screening.addCreditRecord(new CreditRecord("Ubank", 500.00, "arrangement", "Paying", Category.YES, new Date()));
//        screening.addCreditRecord(new CreditRecord("Absa", 500.00, "arrangement", "Paying", Category.YES, new Date()));
//        screening.addDisciplinaryRecord(new DisciplinaryRecord("Not guilty", "Don't do it again", new Date(), new Date(), VettingStatus.SSA_PROCESSING));
//        screening.addDisciplinaryRecord(new DisciplinaryRecord("Not guilty", "Don't do it again", new Date(), new Date(), VettingStatus.SSA_PROCESSING));
//        screening.addDisciplinaryRecord(new DisciplinaryRecord("Not guilty", "Don't do it again", new Date(), new Date(), VettingStatus.SSA_PROCESSING));
//        screening.addDisciplinaryRecord(new DisciplinaryRecord("Not guilty", "Don't do it again", new Date(), new Date(), VettingStatus.SSA_PROCESSING));
//        screening.addInternalInvestigationRecord(new InternalInvestigationRecord("Not Guilty", RandomStringUtils.randomNumeric(5), "Never again", VettingStatus.SSA_PROCESSING, new Date()));
//        screening.addInternalInvestigationRecord(new InternalInvestigationRecord("Not Guilty", RandomStringUtils.randomNumeric(5), "Never again", VettingStatus.SSA_PROCESSING, new Date()));
//        screening.addInternalInvestigationRecord(new InternalInvestigationRecord("Not Guilty", RandomStringUtils.randomNumeric(5), "Never again", VettingStatus.SSA_PROCESSING, new Date()));
//        screening.addInternalInvestigationRecord(new InternalInvestigationRecord("Not Guilty", RandomStringUtils.randomNumeric(5), "Never again", VettingStatus.SSA_PROCESSING, new Date()));
//        screening.setTaxDetails(new TaxDetails(RandomStringUtils.randomNumeric(8), 6598.32, 5879.20, new Date(), YesNo.YES, TaxStatus.YES));
//        screening.setTaxDetails(new TaxDetails(RandomStringUtils.randomNumeric(8), 6598.32, 5879.20, new Date(), YesNo.YES, TaxStatus.YES));
//        screening.setTaxDetails(new TaxDetails(RandomStringUtils.randomNumeric(8), 6598.32, 5879.20, new Date(), YesNo.YES, TaxStatus.YES));
//        screening.setTaxDetails(new TaxDetails(RandomStringUtils.randomNumeric(8), 6598.32, 5879.20, new Date(), YesNo.YES, TaxStatus.YES));
        return screening;
    }

    public static IncomeExpenditure getIncomeExpenditure() {
        IncomeExpenditure incomeExpenditure = new IncomeExpenditure();
        incomeExpenditure.setCreatedBy("s2026080");
        incomeExpenditure.setCreatedDate(new Date());
        incomeExpenditure.setEmployeeTotalIncome(595.00);
        incomeExpenditure.setSpouseTotalIncome(256879.35);
        incomeExpenditure.setIncome(new Income(3000000.00, 10000000.00, 10000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.BOND_RENT, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 1000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.CREDIT_CARD, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.GROCERY, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.LEVY, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.LOANS, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.SCHOOL_FEES, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.TRANSPORT, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.TELEPHONE_BILL, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.UTILITY, ExpenditureType.EXPENDITURE, null, IncomeType.EMPLOYEE, 6000.00));

        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.BOND_RENT, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 1000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.CREDIT_CARD, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.GROCERY, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.LEVY, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.LOANS, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.SCHOOL_FEES, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.TRANSPORT, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.TELEPHONE_BILL, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));
        incomeExpenditure.addExpenditure(new Expenditure(ExpenditureDescription.UTILITY, ExpenditureType.EXPENDITURE, null, IncomeType.SPOUSE, 6000.00));

        incomeExpenditure.addExpenditure(new Expenditure(null, ExpenditureType.OTHER_EXPENDITURE, "Child Support", IncomeType.SPOUSE, 8000.00));
        incomeExpenditure.addExpenditure(new Expenditure(null, ExpenditureType.OTHER_EXPENDITURE, "Spousal Support", IncomeType.SPOUSE, 10000.00));
        incomeExpenditure.addExpenditure(new Expenditure(null, ExpenditureType.OTHER_EXPENDITURE, "Medical Aid", IncomeType.SPOUSE, 12000.00));
        incomeExpenditure.addExpenditure(new Expenditure(null, ExpenditureType.OTHER_EXPENDITURE, "Gym Membership", IncomeType.SPOUSE, 600.00));

        return incomeExpenditure;
    }

    public static Z204Form getZ204Form() {
        Z204Form z204Form = new Z204Form();
        z204Form.setCreatedBy("s2026080");
        z204Form.setCreatedDate(new Date());
        //   z204Form.setClearanceLevel(ClearanceLevel.CONFIDENTIAL);
        z204Form.setAttachmentChecklist(new AttachmentChecklist(true, true, true, true, true, true, true));

        EmployeeRelative employeeRelative = new EmployeeRelative();
        employeeRelative.setCreatedBy("s2026080");
        employeeRelative.setCreatedDate(new Date());
        employeeRelative.setBirthDate(employeeRelative.getCreatedDate());
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setCreatedBy("s2028398");
        contactDetail.setCreatedDate(new Date());
        contactDetail.setEmailAddress("aqumbisa@sars.gov.za");
        contactDetail.setFaxNumber("0123654987");
        contactDetail.setCellPhoneNumber("0733366310");
        contactDetail.setTelephoneNumber("0123658794");
        contactDetail.setBusinessNumber("(011)482 8263");
        employeeRelative.setContactDetail(contactDetail);

        Address physicalAddress = new Address();
        physicalAddress.setCreatedBy("s2028398");
        physicalAddress.setCreatedDate(new Date());
        physicalAddress.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress.setLine1("Unit 1 Estate View");
        physicalAddress.setLine2("123 Street Road");
        physicalAddress.setLine3("Zambezi Juntion");
        physicalAddress.setArea("Pretoria");
        physicalAddress.setCode("0002");
        //employeeRelative.setResidentialAddress(physicalAddress);
        employeeRelative.addAddress(physicalAddress);

        employeeRelative.setRelationship(Relationship.SIBLING);
        employeeRelative.setFirstName("S'phetho");
        employeeRelative.setFullNames("S'phetho Qumbisa");
        employeeRelative.setGenderType(Gender.MALE);
        employeeRelative.setLastName("Qumbisa");
        employeeRelative.setPersonType(PersonType.RELATIVE);
        employeeRelative.setCountryName("South Africa");
        employeeRelative.setNationality("South African");
        employeeRelative.setIdentityNumber("9901205527089");
        //   z204Form.addEmployeeRelative(employeeRelative);

        Spouse spouse = new Spouse();
        spouse.setCreatedBy("s2026080");
        spouse.setCreatedDate(new Date());

        ContactDetail contactDetail1 = new ContactDetail();
        contactDetail1.setCreatedBy("s2028398");
        contactDetail1.setCreatedDate(new Date());
        contactDetail1.setCellPhoneNumber("0733366310");
        contactDetail1.setTelephoneNumber("0123658794");
        contactDetail1.setBusinessNumber("(011)482 8263");
        spouse.setContactDetail(contactDetail1);

        Address physicalAddress1 = new Address();
        physicalAddress1.setCreatedBy("s2028398");
        physicalAddress1.setCreatedDate(new Date());
        physicalAddress1.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress1.setLine1("Unit 1 Estate View");
        physicalAddress1.setLine2("123 Street Road");
        physicalAddress1.setLine3("Zambezi Juntion");
        physicalAddress1.setArea("Pretoria");
        physicalAddress1.setCode("0002");
        //spouse.setResidentialAddress(physicalAddress1);
        spouse.addAddress(physicalAddress1);

        spouse.setFirstName("Khombi");
        spouse.setFullNames("Khombi Qumbisa");
        spouse.setGenderType(Gender.FEMALE);
        spouse.setLastName("Qumbisa");
        spouse.setPersonType(PersonType.SPOUSE);
        spouse.setCountryName("South Africa");
        spouse.setNationality("South African");
        spouse.setDateOfSeparation(new Date());
        spouse.setIdentityNumber("9301205527089");
        spouse.setSpouseType(SpouseType.PREVIOUS);
        //  z204Form.addSpouse(spouse);

        Spouse spouse2 = new Spouse();
        spouse2.setCreatedBy("s2026080");
        spouse2.setCreatedDate(new Date());

        ContactDetail contactDetail111 = new ContactDetail();
        contactDetail111.setCreatedBy("s2028398");
        contactDetail111.setCreatedDate(new Date());
        contactDetail111.setCellPhoneNumber("0733366310");
        contactDetail111.setTelephoneNumber("0123658794");
        contactDetail111.setBusinessNumber("(011)482 8263");
        spouse2.setContactDetail(contactDetail111);

        Address physicalAddress4 = new Address();
        physicalAddress4.setCreatedBy("s2028398");
        physicalAddress4.setCreatedDate(new Date());
        physicalAddress4.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress4.setLine1("Unit 1 Estate View");
        physicalAddress4.setLine2("123 Street Road");
        physicalAddress4.setLine3("Zambezi Juntion");
        physicalAddress4.setArea("Pretoria");
        physicalAddress4.setCode("0002");
        //spouse.setResidentialAddress(physicalAddress1);
        spouse2.addAddress(physicalAddress4);

        spouse2.setFirstName("Khombi");
        spouse2.setFullNames("Khombi Qumbisa");
        spouse2.setGenderType(Gender.FEMALE);
        spouse2.setLastName("Qumbisa");
        spouse2.setPersonType(PersonType.SPOUSE);
        spouse2.setCountryName("South Africa");
        spouse2.setNationality("South African");
        spouse2.setDateOfSeparation(new Date());
        spouse2.setIdentityNumber("9301205527089");
        spouse2.setSpouseType(SpouseType.PREVIOUS);
        //  z204Form.addSpouse(spouse2);

        Spouse spouse1 = new Spouse();
        spouse1.setCreatedBy("s2026080");
        spouse1.setCreatedDate(new Date());

        ContactDetail contactDetail11 = new ContactDetail();
        contactDetail11.setCreatedBy("s2028398");
        contactDetail11.setCreatedDate(new Date());
        contactDetail11.setCellPhoneNumber("0733366310");
        contactDetail11.setTelephoneNumber("0123658794");
        contactDetail11.setBusinessNumber("(011)482 8263");
        spouse1.setContactDetail(contactDetail11);

        Address physicalAddress3 = new Address();
        physicalAddress3.setCreatedBy("s2028398");
        physicalAddress3.setCreatedDate(new Date());
        physicalAddress3.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress3.setLine1("Unit 1 Estate View");
        physicalAddress3.setLine2("123 Street Road");
        physicalAddress3.setLine3("Zambezi Juntion");
        physicalAddress3.setArea("Pretoria");
        physicalAddress3.setCode("0002");
        //spouse.setResidentialAddress(physicalAddress1);
        spouse1.addAddress(physicalAddress3);

        spouse1.setFirstName("Amogelang");
        spouse1.setFullNames("Mdau");
        spouse1.setGenderType(Gender.FEMALE);
        spouse1.setLastName("Modau");
        spouse1.setPersonType(PersonType.SPOUSE);
        spouse1.setCountryName("South Africa");
        spouse1.setNationality("South African");
        spouse1.setIdentityNumber("9301205527089");
        spouse1.setSpouseType(SpouseType.PRESENT);
        //  z204Form.addSpouse(spouse1);

        Reference reference = new Reference();
        reference.setCreatedBy("s2026080");
        reference.setCreatedDate(new Date());
        reference.setTitle("MR");

        ContactDetail contactDetail2 = new ContactDetail();
        contactDetail2.setCreatedBy("s2028398");
        contactDetail2.setCreatedDate(new Date());
        contactDetail2.setCellPhoneNumber("0733366310");
        contactDetail2.setTelephoneNumber("0123658794");
        contactDetail2.setBusinessNumber("(011)482 8263");
        reference.setContactDetail(contactDetail2);

        Address physicalAddress2 = new Address();
        physicalAddress2.setCreatedBy("s2028398");
        physicalAddress2.setCreatedDate(new Date());
        physicalAddress2.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress2.setLine1("Unit 1 Estate View");
        physicalAddress2.setLine2("123 Street Road");
        physicalAddress2.setLine3("Zambezi Juntion");
        physicalAddress2.setArea("Pretoria");
        physicalAddress2.setCode("0002");
        //reference.setResidentialAddress(physicalAddress2);
        reference.addAddress(physicalAddress2);

        reference.setFirstName("Sxova");
        reference.setLastName("Qumbisa");
        reference.setPersonType(PersonType.REFERENCE);
        reference.setIdentityNumber("9301205527089");
        reference.setOccupation("Software Dev");
        reference.setYearsKnown(10);

        //   z204Form.addReference(reference);
        EmployeeRelative empRelative = new EmployeeRelative();
        empRelative.setCreatedBy("s2026080");
        empRelative.setCreatedDate(new Date());
        empRelative.setTitle("MR");

        ContactDetail contactDetail21 = new ContactDetail();
        contactDetail21.setCreatedBy("s2028398");
        contactDetail21.setCreatedDate(new Date());
        contactDetail21.setCellPhoneNumber("0733366310");
        contactDetail21.setTelephoneNumber("0123658794");
        contactDetail21.setBusinessNumber("(011)482 8263");
        empRelative.setContactDetail(contactDetail21);

        Address physicalAddress5 = new Address();
        physicalAddress5.setCreatedBy("s2028398");
        physicalAddress5.setCreatedDate(new Date());
        physicalAddress5.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress5.setLine1("Unit 1 Estate View");
        physicalAddress5.setLine2("123 Street Road");
        physicalAddress5.setLine3("Zambezi Juntion");
        physicalAddress5.setArea("Pretoria");
        physicalAddress5.setCode("0002");
        //reference.setResidentialAddress(physicalAddress2);
        empRelative.addAddress(physicalAddress5);

        empRelative.setFirstName("Sxova");
        empRelative.setLastName("Qumbisa");
        empRelative.setRelationship(Relationship.SIBLING);
        empRelative.setIdentityNumber("9301205527089");
        empRelative.setFullNames("Khombi Qumbisa");
        empRelative.setGenderType(Gender.FEMALE);
        empRelative.setCountryName("South Africa");
        empRelative.setNationality("South African");
        empRelative.setIdentityNumber("9301205527089");

        //    z204Form.addEmpRelative(empRelative);
        EmployeeRelative empRelative1 = new EmployeeRelative();
        empRelative1.setCreatedBy("s2026080");
        empRelative1.setCreatedDate(new Date());
        empRelative1.setTitle("MR");

        ContactDetail contactDetail211 = new ContactDetail();
        contactDetail211.setCreatedBy("s2028398");
        contactDetail211.setCreatedDate(new Date());
        contactDetail211.setCellPhoneNumber("0733366310");
        contactDetail211.setTelephoneNumber("0123658794");
        contactDetail211.setBusinessNumber("(011)482 8263");
        empRelative1.setContactDetail(contactDetail211);

        Address physicalAddress6 = new Address();
        physicalAddress6.setCreatedBy("s2028398");
        physicalAddress6.setCreatedDate(new Date());
        physicalAddress6.setAddressType(AddressType.RESIDENTIAL);
        physicalAddress6.setLine1("Unit 1 Estate View");
        physicalAddress6.setLine2("123 Street Road");
        physicalAddress6.setLine3("Zambezi Juntion");
        physicalAddress6.setArea("Pretoria");
        physicalAddress6.setCode("0002");
        //reference.setResidentialAddress(physicalAddress2);
        empRelative1.addAddress(physicalAddress5);

        empRelative1.setFirstName("Sxova");
        empRelative1.setLastName("Qumbisa");
        empRelative1.setRelationship(Relationship.SIBLING);
        empRelative1.setIdentityNumber("9301205527089");
        empRelative1.setFullNames("Khombi Qumbisa");
        empRelative1.setGenderType(Gender.FEMALE);
        empRelative1.setCountryName("South Africa");
        empRelative1.setNationality("South African");
        empRelative1.setIdentityNumber("9301205527089");

        // z204Form.addEmpRelative(empRelative1);
        // z204Form.addEmploymentHistory(new EmploymentHistory("Accountant", "s2026080", "SARS", "Andile", "0733366310", "172 Smith Street", new Date(), new Date()));
        //   z204Form.addForeignIntelligence(new ForeignIntelligence("CIA", "3564", "UNITED STATE","Tebogo Makau", new Date(), new Date()));
        // z204Form.addHealthInfo(new HealthInfo("Siyakha Against Substance Abuse", "Andile Qumbisa", "073366310", "Alcohol Abuse"));
        //   z204Form.addLegalAction(new LegalAction("PMB", "Not Guilty", "", "South Africa", "Fraud","","","","", new Date(),new Date(),new Date()));
        // z204Form.addServiceInStateSecurity(new StateSecurityService(new Date(), new Date(), "p2656", "South Africa", "SANDF"));
        //  z204Form.addVisitReisidenceOutOfSA(new VisitedCountry(new Date(), new Date(), "China", "Vacation"));
        //  z204Form.setImmigrant(new Immigrant(new Date(), new Date(), new Date(), "Belarus", "ORTIA", "C254989", "P65959", "373942474924r", "Egypt", YesNo.YES));
        // z204Form.addReference(reference);
        //  z204Form.addEmploymentHistory(new EmploymentHistory("Accountant", "s2026080", "SARS", "Andile", "0733366310", "172 Smith Street", new Date(), new Date()));
        //   z204Form.addForeignIntelligence(new ForeignIntelligence("CIA", "3564", "UNITED STATE","The othe person", new Date(), new Date()));
        // z204Form.addHealthInfo(new HealthInfo("Siyakha Against Substance Abuse", "Andile Qumbisa", "073366310", "Alcohol Abuse",PsychiatricTreatment.YES, DrugAbuse.YES, AlcoholAbuse.YES));
        //  z204Form.addLegalAction(new LegalAction("PMB", "Not Guilty", "", "South Africa", "Fraud","","","","", new Date(),new Date(),new Date()));
        //  z204Form.addServiceInStateSecurity(new StateSecurityService(new Date(), new Date(), "p2656", "South Africa", "SANDF"));
        //  z204Form.addVisitReisidenceOutOfSA(new VisitedCountry(new Date(), new Date(), "China", "Vacation"));
//        z204Form.setImmigrant(new Immigrant(new Date(), new Date(), new Date(), "Belarus", "ORTIA", "C254989", "P65959", "395660472473", "Congo", YesNo.YES));
        z204Form.setFinancialDocsResponse(new FinancialDocsResponse(true, true, true, true, true, true, true, true, true, true, true));
        return z204Form;
    }

    public static ScreeningDeclaration getScreeningDeclaration(String jobTitle) {
        ScreeningDeclaration screeningDeclaration = new ScreeningDeclaration();
        screeningDeclaration.setCreatedBy("s2026080");
        screeningDeclaration.setCreatedDate(new Date());
        screeningDeclaration.setJobTitle(jobTitle);
        screeningDeclaration.setScreeningDate(new Date());
        List<String> vowels = new ArrayList<>();
        vowels.add("South Africa"); // [A]
        vowels.add("ENgland"); // [A, E]
        vowels.add("UK"); // [A, E, U]
//        screeningDeclaration.setScreeningDeclarationResponse(new ScreeningDeclarationResponse(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, null, null, null, null, null, null, null, null, null, null, null, null, null));
//        screeningDeclaration.setScreeningDeclarationResponse(new ScreeningDeclarationResponse(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, "1149562365", "", ""));
        return screeningDeclaration;
    }

    public static EmailNotification getEmailNotification(NotificationType notificationType, String subject, String line1, String line2, String line3, String line4) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setCreatedBy("S2028398");
        emailNotification.setCreatedDate(new Date());
        emailNotification.setEmailSubject(subject);
        emailNotification.setLine1(line1);
        emailNotification.setLine2(line2);
        emailNotification.setLine3(line3);
        emailNotification.setLine4(line4);
        emailNotification.setNotificationType(notificationType);
        return emailNotification;
    }

    public static EmailNotification getEnhanceEmailNotification(NotificationType notificationType, String subject, String description) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setCreatedBy("S2028398");
        emailNotification.setCreatedDate(new Date());
        emailNotification.setEmailSubject(subject);
        emailNotification.setDescription(description);
        emailNotification.setNotificationType(notificationType);
        return emailNotification;
    }

    public static JobRole getJobRole(String description) {

        JobRole jobRole = new JobRole();
        jobRole.setCreatedBy("S2028398");
        jobRole.setDescription(description);
        jobRole.setCreatedDate(new Date());

        return jobRole;
    }

    public static AdjustmentCategory getAdjustmentCategory(String description) {

        AdjustmentCategory adjustmentCategory = new AdjustmentCategory();
        adjustmentCategory.setCreatedBy("S2028398");
        adjustmentCategory.setDescription(description);
        adjustmentCategory.setCreatedDate(new Date());

        return adjustmentCategory;
    }

    public static SubArea getSubArea(String description) {
        SubArea subArea = new SubArea();
        subArea.setCreatedBy("S2028398");
        subArea.setCreatedDate(new Date());
        subArea.setDescription(description);

        Area area = new Area();
        area.setCreatedBy("S2028398");
        area.setCreatedDate(new Date());
        area.setSubAreaDescription("Operational Escalation & Complaints – Refund Risk, Profiling & Screening");
        area.setAreaDescription("");
        subArea.addAreas(area);

        return subArea;
    }
}
