package sars.vetting.system.mb;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sars.vetting.system.domain.AdministrationSetting;
import sars.vetting.system.domain.CompleteVettingSetting;
import sars.vetting.system.domain.Permission;
import sars.vetting.system.domain.ReportSetting;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.domain.InitiateVettingSetting;
import sars.vetting.system.domain.ReviewVettingSetting;
import sars.vetting.system.domain.VettingApprovalSetting;
import sars.vetting.system.service.EmployeeRoleServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class EmployeeRoleBean extends BaseBean<EmployeeRole> {
    
    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;
    
    private Slice slices;
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        slices = employeeRoleService.findAll(pageable);
//        addCollections(slices.toList());
        addCollections(employeeRoleService.listAll());
    }
    
    public void addOrUpdate(EmployeeRole employeeRole) {
        reset().setAdd(true);
        if (employeeRole != null) {
            employeeRole.setUpdatedBy(getActiveUser().getSid());
            employeeRole.setUpdatedDate(new Date());
        } else {
            employeeRole = new EmployeeRole();
            employeeRole.setCreatedBy(getActiveUser().getSid());
            employeeRole.setCreatedDate(new Date());
            
            AdministrationSetting administrationSetting = new AdministrationSetting();
            administrationSetting.setCreatedBy(getActiveUser().getSid());
            administrationSetting.setCreatedDate(new Date());
            employeeRole.setAdminSetting(administrationSetting);
            
            CompleteVettingSetting completeVettingSetting = new CompleteVettingSetting();
            completeVettingSetting.setCreatedBy(getActiveUser().getSid());
            completeVettingSetting.setCreatedDate(new Date());
            employeeRole.setCompleteVettingSetting(completeVettingSetting);
            
            InitiateVettingSetting initiateVettingSetting = new InitiateVettingSetting();
            initiateVettingSetting.setCreatedBy(getActiveUser().getSid());
            initiateVettingSetting.setCreatedDate(new Date());
            employeeRole.setInitiateVettingSetting(initiateVettingSetting);
            
            ReviewVettingSetting reviewVettingSetting = new ReviewVettingSetting();
            reviewVettingSetting.setCreatedBy(getActiveUser().getSid());
            reviewVettingSetting.setCreatedDate(new Date());
            employeeRole.setReviewVettingSetting(reviewVettingSetting);
            
            VettingApprovalSetting approvalSetting = new VettingApprovalSetting();
            approvalSetting.setCreatedBy(getActiveUser().getSid());
            approvalSetting.setCreatedDate(new Date());
            employeeRole.setVettingApprovalSetting(approvalSetting);
            
            ReportSetting reportSetting = new ReportSetting();
            reportSetting.setCreatedBy(getActiveUser().getSid());
            reportSetting.setCreatedDate(new Date());
            employeeRole.setReportSetting(reportSetting);
            
            Permission permission = new Permission();
            permission.setCreatedBy(getActiveUser().getSid());
            permission.setCreatedDate(new Date());
            employeeRole.setPermission(permission);
            
            addCollection(employeeRole);
        }
        addEntity(employeeRole);
    }
    
    public void save(EmployeeRole employeeRole) {
        if (employeeRole.getId() != null) {
            employeeRoleService.update(employeeRole);
            addInformationMessage("User Role was successfully updated.");
        } else {
            employeeRoleService.save(employeeRole);
            addInformationMessage("User Role was successfully created.");
        }
        reset().setList(true);
    }
    
    public void cancel(EmployeeRole employeeRole) {
        if (employeeRole.getId() == null && getEmployeeRoles().contains(employeeRole)) {
            remove(employeeRole);
        }
        reset().setList(true);
    }
    
    public void delete(EmployeeRole employeeRole) {
        employeeRoleService.deleteById(employeeRole.getId());
        remove(employeeRole);
        addInformationMessage("User Role was successfully deleted");
        reset().setList(true);
    }
    
    /*
    public Integer getPageNumber() {
        return slices.getNumber() + 1;
    }
    
    public boolean isNextPage() {
        return slices.hasNext();
    }
    
    public boolean isPreviousPage() {
        return slices.hasPrevious();
    }
    
    public void firstPageDocuments() {
        slices = employeeRoleService.findAll(slices.previousOrFirstPageable());
        addCollections(slices.toList());
    }
    
    public void lastPageDocuments() {
        slices = employeeRoleService.findAll(slices.nextOrLastPageable());
        addCollections(slices.toList());
    }
    
    public void nextDocuments() {
        if (slices.hasNext()) {
            slices = employeeRoleService.findAll(slices.nextPageable());
            addCollections(slices.toList());
        }
    }
    
    public void previousDocuments() {
        if (slices.hasPrevious()) {
            slices = employeeRoleService.findAll(slices.previousPageable());
            addCollections(slices.toList());
        }
    }
    */
    public List<EmployeeRole> getEmployeeRoles() {
        return this.getCollections();
    }
    
}
