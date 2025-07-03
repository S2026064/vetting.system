package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.service.EmployeeRoleServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingSubjectRecordBean extends BaseBean<Employee> {
 
    private String idNumberParam;
    private Employee retrieveEmployee;
    private List<EmployeeRole> employeeRoles = new ArrayList<>();
    private EmployeeRoleServiceLocal employeeRoleService;
    private EmployeeServiceLocal employeeService;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
    }
    public void searchEmployee() {
        employeeRoles.addAll(employeeRoleService.listAll());
        Employee persistentUser = employeeService.findByIdentityNumber(idNumberParam);
        if (persistentUser != null) {
            addEntity(persistentUser);
        } else {
            addError("The Employee with the ID Number", idNumberParam, "does not exist");
        }
    }
    public void initaiteVettingProcess(Employee employee) {
        reset().setAdd(true);
        if (employee == null) {
            addWarningMessage("The user you have selected is invalid, please check the user and try again");
            return;
        }
        addEntity(employee);
    }
    public void save(Employee user) {

    }
    public void cancel(Employee employee) {
        if (employee.getId() == null && getCollections().contains(employee)) {
            remove(employee);
        }
        reset().setList(true);
    }
    public List<Employee> getVettingRecords() {
        return this.getCollections();
    }
    public String getIdNumberParam() {
        return idNumberParam;
    }
    public void setIdNumberParam(String idNumberParam) {
        this.idNumberParam = idNumberParam;
    }

    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoles;
    }
    public void setEmployeeRoles(List<EmployeeRole> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }     

}
