package sars.vetting.system.mb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.EmailNotificationSenderServiceLocal;
import sars.vetting.system.service.EmployeeRoleServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingRerouteBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingRerouteBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;

    private List<Employee> vettingOfficers = new ArrayList<>();
    private List<VettingStatus> vettingStatus = new ArrayList<>();

    private Slice slices;
    private VettingStatus selctedVettingStatus;
    //keep previous user sid to alert email after update
    private String userSid;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        vettingStatus.add(VettingStatus.FORMS_SUBMITTED);
        vettingStatus.add(VettingStatus.SARS_PROCESSING);
        vettingStatus.add(VettingStatus.ANALYST_POOL);
        vettingStatus.add(VettingStatus.SSA_PROCESSING);
        vettingStatus.add(VettingStatus.OFFICER_POOL);
        vettingStatus.add(VettingStatus.CLEARANCE_ISSUED);
        vettingStatus.add(VettingStatus.QA_POOL);
        vettingStatus.add(VettingStatus.CLEARANCE_DENIED);
        vettingStatus.add(VettingStatus.APPROVER_POOL);
        vettingStatus.add(VettingStatus.REJECTED);
        vettingStatus.add(VettingStatus.ESCALATED);
        vettingStatus.add(VettingStatus.APPROVED);
        vettingStatus.add(VettingStatus.VETTING_DENIED);
        vettingStatus.add(VettingStatus.ANALYST_POOL);
        vettingStatus.add(VettingStatus.OFFICER_POOL);
        vettingStatus.add(VettingStatus.QA_POOL);
        vettingStatus.add(VettingStatus.APPROVER_POOL);
        vettingStatus.add(VettingStatus.FINAL_APPROVER_POOL);
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        slices = vettingService.findByVettingStatusIn(vettingStatus, pageable);
//        addCollections(slices.toList());
        addCollections(vettingService.findVettingRecordsByStatus(vettingStatus));
    }

    public void loadAllocateVettingForm(Vetting vettingRecord) {
        vettingOfficers.clear();
        reset().setAllocate(true);
        addEntity(vettingRecord);
        switch (vettingRecord.getVettingStatus()) {
            case FORMS_SUBMITTED:
                vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.INITIATOR.toString()));
                break;
            case SARS_PROCESSING:
                vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.ANALYST.toString()));
                break;
            case SSA_PROCESSING:
                vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.OFFICER.toString()));
                break;
            case SENT_TO_QA:
                vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.QUALITY_ASSURARER.toString()));
                break;
            case CLEARANCE_ISSUED:
                vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.APPROVER.toString()));
                break;
            case CLEARANCE_DENIED:
                vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.SECOND_LEVEL_APPROVER.toString()));
                break;
            case ANALYST_POOL:
            case OFFICER_POOL:
            case QA_POOL:
            case APPROVER_POOL:
            case FINAL_APPROVER_POOL:
                List<EmployeeRole> employeeRole = new ArrayList<>();
                employeeRole.add(employeeRoleService.findByDescription("Super Administrator"));
                employeeRole.add(employeeRoleService.findByDescription("Administrator"));
                employeeRole.add(employeeRoleService.findByDescription("Subject"));
                vettingOfficers.addAll(employeeService.findEmployeeByEmployeeRoleNotIn(employeeRole));
                break;
            default:
                break;
        }
    }

    public void filterByStatus() {
        if (selctedVettingStatus == null) {
            addWarningMessage("Please select filter type");
            return;
        }
        getCollections().clear();
        if (selctedVettingStatus != null && !selctedVettingStatus.equals(VettingStatus.FIND_ALL)) {
            addCollections(vettingService.findByVettingStatus(selctedVettingStatus));
        } else if (selctedVettingStatus.equals(VettingStatus.FIND_ALL)) {
            addCollections(vettingService.findVettingRecordsByStatus(vettingStatus));
        }
        if (getCollections().isEmpty()) {
            addInformationMessage("No vetting records of that status");
        }
    }

    public void allocateVetting(Vetting vetting) {
        userSid = vetting.getVettingProcessingOfficer().getSid();
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        if (vetting.getVettingStatus().equals(VettingStatus.FORMS_SUBMITTED)) {
            vetting.setInitiatorSid(vetting.getInitiatorSid());
        }
        if (vetting.getVettingStatus().equals(VettingStatus.SARS_PROCESSING)) {
            vetting.setAnalystSid(vetting.getAnalystSid());
        }
        if (vetting.getVettingStatus().equals(VettingStatus.SSA_PROCESSING)) {
            vetting.setOfficerSid(vetting.getOfficerSid());
        }
        if (vetting.getVettingStatus().equals(VettingStatus.SENT_TO_QA)) {
            vetting.setQaSid(vetting.getQaSid());
        }
        if (vetting.getVettingStatus().equals(VettingStatus.CLEARANCE_ISSUED)) {
            vetting.setFirstApproverSid(vetting.getFirstApproverSid());
        }
        vetting.setVettingOfficer(vetting.getVettingProcessingOfficer().getFullNames());
        vetting.setVettingProcessingOfficer(vetting.getVettingProcessingOfficer());
        vettingService.update(vetting);
        synchronize(vetting);
        try {
            Employee employee = employeeService.findBySid(userSid);
            emailNotificationSenderService.sendNotificationReallocate(NotificationType.ALLOCATE_VETTING, vetting.getCreatedDate(), vetting.getVettingProcessingOfficer(), getActiveUser().getEmployee(), employee);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingRerouteBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingRerouteBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        addInformationMessage("Vetting allocated successfully");
        reset().setList(true);
    }

    /*
     public boolean isNextPage() {
     return slices.hasNext();
     }

     public boolean isPreviousPage() {
     return slices.hasPrevious();
     }

     public void nextDocuments() {
     if (slices.hasNext()) {
     slices = vettingService.findByVettingStatusIn(vettingStatus, slices.nextPageable());
     addCollections(slices.toList());
     }
     }

     public void previousDocuments() {
     if (slices.hasPrevious()) {
     slices = vettingService.findByVettingStatusIn(vettingStatus, slices.previousPageable());
     addCollections(slices.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     slices = vettingService.findByVettingStatusIn(vettingStatus, slices.nextOrLastPageable());
     addCollections(slices.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     slices = vettingService.findByVettingStatusIn(vettingStatus, slices.previousOrFirstPageable());
     addCollections(slices.toList());
     }

     public Integer getPageNumber() {
     return slices.getNumber() + 1;
     }
     */
    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public List<Employee> getVettingOfficers() {
        return vettingOfficers;
    }

    public void setVettingOfficers(List<Employee> vettingOfficers) {
        this.vettingOfficers = vettingOfficers;
    }

    public Slice getSlices() {
        return slices;
    }

    public void setSlices(Slice slices) {
        this.slices = slices;
    }

    public VettingStatus getSelctedVettingStatus() {
        return selctedVettingStatus;
    }

    public void setSelctedVettingStatus(VettingStatus selctedVettingStatus) {
        this.selctedVettingStatus = selctedVettingStatus;
    }

    public List<VettingStatus> getVettingStatus() {
        return vettingStatus;
    }

    public void setVettingStatus(List<VettingStatus> vettingStatus) {
        this.vettingStatus = vettingStatus;
    }

}
