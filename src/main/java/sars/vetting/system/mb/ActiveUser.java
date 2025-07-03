/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.Router;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;

/**
 *
 * @author S2028398
 */
@ManagedBean
@SessionScoped
@Getter
@Setter
public class ActiveUser implements Serializable {
    private String sid;
    private boolean userLoginIndicator;
    private EmployeeRole employeeRole;
    private String moduleWelcomeMessage;
    private String loggedOnUserFullName;
    private String fullName;
    private Router router = new Router();
    private Employee employee; //User to be replaced by required properties only
    private Integer activeIndex;

    private boolean declaration;
    private boolean screening;
    private boolean attachment;
    private boolean incomeExpenditure;
    private Integer activeStepIndex;
    private String deployedAppVersion;
    private String uploadUrl;
    private String downloadUrl;

    public ActiveUser() {
        userLoginIndicator = Boolean.FALSE;
    }
    public void setLogonUserSession(Employee employee) {
        if (employee.getId() != null) {
            this.setEmployeeRole(employee.getEmployeeRole());
            this.setLoggedOnUserFullName(employee.getFullNames());
            this.setFullName(employee.getFullNames());
            this.setSid(employee.getSid());
            this.setUserLoginIndicator(true);
            this.setEmployee(employee);
        }
    }
    public ActiveUser resetVettingWorkFlow() {
        this.setAttachment(false);
        this.setDeclaration(false);
        this.setScreening(false);
        this.setIncomeExpenditure(false);
        return this;
    }
}
