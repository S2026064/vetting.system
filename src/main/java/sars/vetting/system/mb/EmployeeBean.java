package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.CountryUtil;
import sars.vetting.system.common.EmployeeStatus;
import sars.vetting.system.common.Gender;
import sars.vetting.system.domain.ContactDetail;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.service.DOPIServiceLocal;
import sars.vetting.system.service.EmployeeInformationServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.EmployeeRoleServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class EmployeeBean extends BaseBean<Employee> {

    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;
    @Autowired
    private EmployeeInformationServiceLocal employeeInformationService;
    @Autowired
    private DOPIServiceLocal dOPIService;

    private List<EmployeeRole> employeeRoles = new ArrayList<>();
    private List<EmployeeStatus> employeeStatus = new ArrayList<>();
    private List<Gender> genders = new ArrayList<>();
    private Map<String, String> countryCode = new TreeMap<>();

    private static final Logger LOG = Logger.getLogger(EmployeeBean.class.getName());
    private String employeeTitle;
    private String searchParameter;
    private String sid;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        setCountryCode(CountryUtil.getCoutryCodes());
        genders.addAll(Arrays.asList(Gender.values()));
        employeeRoles.addAll(employeeRoleService.listAll());
        employeeStatus.addAll(Arrays.asList(EmployeeStatus.values()));
    }

    public void updateEmployee(Employee employee) {
        reset().setAdd(true);
        if (employee == null) {
            addWarningMessage("The user you have selected is invalid, please check the user and try again");
            return;
        }
        setPanelTitleName("Update User");
        employee.setUpdatedBy(getActiveUser().getSid());
        employee.setUpdatedDate(new Date());
        addEntity(employee);
    }

    //ADD service provider
    public void addOrUpdate(Employee employee) {
        reset().setStep2(true);
        employee = new Employee();
        employee.setCreatedBy(getActiveUser().getSid());
        employee.setLoggedOnUserFullName(getActiveUser().getLoggedOnUserFullName());
        employee.setCreatedDate(new Date());
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
        employee.setEmployeeRole(employeeRoleService.findByDescription("Subject"));

        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setCreatedBy(getActiveUser().getSid());
        contactDetail.setLoggedOnUserFullName(getActiveUser().getLoggedOnUserFullName());
        contactDetail.setCreatedDate(new Date());
        employee.setContactDetail(contactDetail);

        addEntity(employee);
    }

    public void addEmployee() {
        reset().setSearch(true);
    }

    public void searchEmployee() {
        if (this.sid.isEmpty() || this.sid == null || this.sid.equals("")) {
            addWarningMessage("Please enter the employee S-ID");
            return;
        }
        Employee existingEmployee = employeeService.findBySid(sid);
        if (existingEmployee != null) {
            addWarningMessage("An employee with an sid of " + sid + " already exist as a user");
            return;
        }
        Employee employee = employeeInformationService.getEmployeeUserBySid(sid, getActiveUser().getSid());
        if (employee == null) {
            addWarningMessage("An employee with an sid of " + sid + " does not exist as a SARS employee");
            return;
        }

        addCollection(employee);
        addEntity(employee);
        reset().setAdd(true);
    }

    public void onSystemUserSearchListener() {
        getCollections().clear();
        if (searchParameter.isEmpty()) {
            addInformationMessage("Enter search criteria");
            return;
        }

        addCollections(employeeService.findBySidOrFirstNameOrLastName(searchParameter));

        if (getCollections().isEmpty()) {
            addInformationMessage("The employee you are searching for is not registered as a user in this system");
            return;
        }
        setEmployeeTitle("Users");
    }

    public void save(Employee employee) {
        try {
            if (employee.getId() != null) {
                employeeService.update(employee);
            } else if (employee.getSid() == null) {

                if (StringUtils.isEmpty(employee.getIdentityNumber()) && StringUtils.isEmpty(employee.getPassportNumber())) {
                    addErrorMessage("Enter ID Number or Passport number");
                    return;
                }

                if (StringUtils.isNotEmpty(employee.getIdentityNumber())) {
                    if (employeeService.isExist(employee.getIdentityNumber())) {
                        addErrorMessage("Employee with ID", employee.getIdentityNumber(), "already exist in the system.");
                        return;
                    }
                }
                if (StringUtils.isNotEmpty(employee.getPassportNumber())) {
                    if (employeeService.isExist(employee.getPassportNumber())) {
                        addErrorMessage("Employee with Passport", employee.getPassportNumber(), "already exist in the system.");
                        return;
                    }
                }
                if (StringUtils.isNotEmpty(employee.getIdentityNumber())) {
                    //pull information from DOPI and Public officer from IBR data using id number
                    employee.addPublicOfficerDetails(employeeInformationService.getPublicOfficer(employee.getIdentityNumber(), getActiveUser().getSid()));
                    employee.addQualifications(dOPIService.getQualificationByEmployeeIdnumber(employee.getIdentityNumber(), getActiveUser().getSid()));
                } else {
                    //pull information from DOPI and Public officer from IBR data using passport number
//                employee.addPublicOfficerDetails(employeeInformationService.getPublicOfficer(employee.getPassportNumber(), getActiveUser().getSid()));
//                employee.addQualifications(dOPIService.getQualificationByEmployeeIdnumber(employee.getPassportNumber(), getActiveUser().getSid()));

                }
                employeeService.save(employee);
            } else {
                employeeService.save(employee);
            }
            reset().setList(true);
            addInformationMessage("User successfully saved");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public void cancel(Employee employee) {
        if (employee.getId() == null && getCollections().contains(employee)) {
            remove(employee);
        }
        reset().setList(true);
    }

    public void back() {
        reset().setList(true);
    }

    public List<Employee> getEmployees() {
        return this.getCollections();
    }

    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(List<EmployeeRole> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<EmployeeStatus> getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(List<EmployeeStatus> employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeTitle() {
        return employeeTitle;
    }

    public void setEmployeeTitle(String employeeTitle) {
        this.employeeTitle = employeeTitle;
    }

    public List<Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Gender> genders) {
        this.genders = genders;
    }

    public Map<String, String> getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Map<String, String> countryCode) {
        this.countryCode = countryCode;
    }

}
