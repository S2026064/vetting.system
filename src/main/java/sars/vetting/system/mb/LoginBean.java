/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.util.Optional;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.EmployeeStatus;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.service.DOPIServiceLocal;
import sars.vetting.system.service.EmployeeInformationServiceLocal;
import sars.vetting.system.service.EmployeeRoleServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@RequestScoped
public class LoginBean extends BaseBean<Employee> {

    @Autowired
    private EmployeeServiceLocal userService;
    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;
    @Autowired
    private EmployeeInformationServiceLocal employeeInformationService;
    @Autowired
    private DOPIServiceLocal dOPIService;

    private String sidParam;

    public void signIn() {
        //for sso
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        sidParam = externalContext.getRemoteUser() != null ? externalContext.getRemoteUser() : "S2028398";
        // new login code
        Optional<Employee> optional = Optional.ofNullable(userService.findBySid(sidParam));
        if (optional.isPresent()) {
            Employee employee = optional.get();
            if (employee.getEmployeeStatus().equals(EmployeeStatus.ACTIVE)) {
                getActiveUser().setUserLoginIndicator(true);
                getActiveUser().setLogonUserSession(employee);
                redirect("home");
            } else {
                addErrorMessage("System user with SID number", sidParam, " is not active");
            }
        } else {
            Optional<Employee> sapOptional = Optional.ofNullable(employeeInformationService.getEmployeeUserBySid(sidParam, sidParam));
            if (sapOptional.isPresent()) {
                Employee sapEmployee = sapOptional.get();
                if (sapEmployee != null) {
                    sapEmployee.setEmployeeRole(employeeRoleService.findByDescription(EmployeeRoleType.SUBJECT.toString()));
                    sapEmployee.setEmployeeStatus(EmployeeStatus.ACTIVE);
                    if (StringUtils.isNotEmpty(sapEmployee.getIdentityNumber())) {
                        //pull information from DOPI and Public officer from IBR data using id number
                        sapEmployee.addPublicOfficerDetails(employeeInformationService.getPublicOfficer(sapEmployee.getIdentityNumber(), getActiveUser().getSid()));
                        sapEmployee.addQualifications(dOPIService.getQualificationByEmployeeIdnumber(sapEmployee.getIdentityNumber(), getActiveUser().getSid()));
                    }
                } else {
                    addErrorMessage("System user with SID number", sidParam, "does not exist");
                    return;
                }
                //Search manager on vettingDB
                if (StringUtils.isNotEmpty(sapEmployee.getManagerSID())) {
                    Employee manager = userService.findBySid(sapEmployee.getManagerSID());
                    if (manager == null) {
                        //If not found, Search Manager on SapDB
                        manager = employeeInformationService.getEmployeeUserBySid(sapEmployee.getManagerSID(), sapEmployee.getSid());
                        manager.setEmployeeRole(employeeRoleService.findByDescription(EmployeeRoleType.MANAGER.toString()));
                        manager.setEmployeeStatus(EmployeeStatus.ACTIVE);
                        if (StringUtils.isNotEmpty(sapEmployee.getIdentityNumber())) {
                            //pull information from DOPI and Public officer from IBR data using id number
                            manager.addPublicOfficerDetails(employeeInformationService.getPublicOfficer(manager.getIdentityNumber(), getActiveUser().getSid()));
                            manager.addQualifications(dOPIService.getQualificationByEmployeeIdnumber(manager.getIdentityNumber(), getActiveUser().getSid()));
                        }
                        userService.save(manager);
                    }
                }
                Employee persistentEmployee = userService.save(sapEmployee);
                if (persistentEmployee.getId() != null && persistentEmployee.getEmployeeStatus().equals(EmployeeStatus.ACTIVE)) {
                    getActiveUser().setUserLoginIndicator(true);
                    getActiveUser().setLogonUserSession(persistentEmployee);
                    redirect("home");
                } else {
                    addErrorMessage("Failed to persist employee retrieved from SAP with SID", sidParam, "into the vetting system");
                }
            } else {
                addErrorMessage("System user with SID", sidParam, "doest not exist");
            }
        }
    }

    public String getSidParam() {
        return sidParam;
    }

    public void setSidParam(String sidParam) {
        this.sidParam = sidParam;
    }
}
