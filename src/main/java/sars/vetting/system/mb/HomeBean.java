/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sars.vetting.system.domain.Employee;

/**
 *
 * @author S2028398
 */
@ManagedBean
@RequestScoped
public class HomeBean extends BaseBean<Employee> {

    private static final String LANDING_PAGE = "/landing.xhtml";
    private static final String EXPIRY_PAGE = "/expired.xhtml?faces-redirect=true";
    private static final String ADMINISTRATION_PAGE = "vettingEscalation.xhtml";
    private static final String SUPER_ADMINISTRATION_PAGE = "employeeRoles.xhtml";
    private static final String INITIATE_VETTING_PAGE = "vettingInitiation.xhtml";
    private static final String COMPLETE_VETTING_PAGE = "vettingCompletion.xhtml";
    private static final String ANALYST_REVIEW_PAGE = "vettingReview.xhtml";
    private static final String OFFICER_REVIEW_PAGE = "processVetting.xhtml";
    private static final String QA_REVIEW_PAGE = "qaReview.xhtml";
    private static final String MANAGER_REVIEW_PAGE = "vettingManager.xhtml";
    private static final String FIRST_APPROVAL_PAGE = "vettingApproval.xhtml";
    private static final String SECOND_APPROVAL_PAGE = "vettingSecondLevelApproval.xhtml";
    private static final String DRAW_REPORTS_PAGE = "reports.xhtml";
    private static final String VETTING_FILE_PAGE = "vettingFiles.xhtml";

    public String routeToAdministration() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setAdministrator(true);
            return ADMINISTRATION_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToSuperAdministration() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setSuperAdmin(true);
            return SUPER_ADMINISTRATION_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToInitiateVetting() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setVettingInitiation(true);
            return INITIATE_VETTING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToVettingCompletion() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setVettingCompletion(true);
            return COMPLETE_VETTING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToVettingManager() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setManagerReview(true);
            return MANAGER_REVIEW_PAGE;
        }
        getActiveUser().setModuleWelcomeMessage("Welcome to Manager Page");
        return LANDING_PAGE;
    }

    public String routeToVettingReview() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setVettingReview(true);
            switch (getActiveUser().getEmployeeRole().getDescription()) {
                case "Analyst":
                    return ANALYST_REVIEW_PAGE;
                case "Officer":
                    return OFFICER_REVIEW_PAGE;
                case "Quality Assurarer":
                    return QA_REVIEW_PAGE;
                default:
                    break;
            }
            getActiveUser().setModuleWelcomeMessage("Welcome to Vetting Review Page");
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToVettingApproval() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setVettingApproval(true);
            switch (getActiveUser().getEmployeeRole().getDescription()) {
                case "Approver":
                    return FIRST_APPROVAL_PAGE;
                case "Second Level Approver":
                    return SECOND_APPROVAL_PAGE;
                default:
                    break;
            }
            getActiveUser().setModuleWelcomeMessage("Welcome to Vetting Approval Page");
            return LANDING_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToReports() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setReports(true);
            return DRAW_REPORTS_PAGE;
        }
        return EXPIRY_PAGE;
    }

    public String routeToVettingFile() {
        if (getActiveUser() != null) {
            getActiveUser().getRouter().reset().setVettingFiles(true);
            return VETTING_FILE_PAGE;
        }
        return EXPIRY_PAGE;
    }

}
