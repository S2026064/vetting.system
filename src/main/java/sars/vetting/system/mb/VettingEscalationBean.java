package sars.vetting.system.mb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.EmailNotificationSenderServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingEscalationBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingEscalationBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<Employee> vettingOfficers = new ArrayList<>();
    private List<VettingStatus> vettingStatuses = new ArrayList<>();

    private Slice slice;
    private VettingStatus selctedVettingStatus;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        vettingStatuses.add(VettingStatus.FORMS_SUBMITTED);
        vettingStatuses.add(VettingStatus.SARS_PROCESSING);
        vettingStatuses.add(VettingStatus.SENT_TO_QA);
        vettingStatuses.add(VettingStatus.CLEARANCE_ISSUED);
        vettingStatuses.add(VettingStatus.CLEARANCE_DENIED);
        vettingStatuses.add(VettingStatus.FIND_ALL);
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findEscalatedVettings(pageable);
//        addCollections(slice.toList());
        addCollections(vettingService.findEscalatedVettings());
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
            default:
                break;
        }
    }

    public void allocateVetting(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vettingService.update(vetting);
        synchronize(vetting);
        try {
            emailNotificationSenderService.sendNotification(NotificationType.ALLOCATE_VETTING, vetting.getCreatedDate(), vetting.getVettingProcessingOfficer(), getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingEscalationBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingEscalationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Vetting allocated successfully");
        reset().setList(true);
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
            addCollections(vettingService.findVettingRecordsByStatus(vettingStatuses));
        }
        if (getCollections().isEmpty()) {
            addInformationMessage("No vetting records of that status");
        }
    }
    /*
     public boolean isNextPage() {
     return slice.hasNext();
     }

     public boolean isPreviousPage() {
     return slice.hasPrevious();
     }

     public void nextDocuments() {
     if (slice.hasNext()) {
     slice = vettingService.findEscalatedVettings(slice.nextPageable());
     addCollections(slice.toList());
     }
     }

     public void previousDocuments() {
     if (slice.hasPrevious()) {
     slice = vettingService.findEscalatedVettings(slice.previousPageable());
     addCollections(slice.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     slice = vettingService.findEscalatedVettings(slice.nextOrLastPageable());
     addCollections(slice.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     slice = vettingService.findEscalatedVettings(slice.previousOrFirstPageable());
     addCollections(slice.toList());
     }

     public Integer getPageNumber() {
     return slice.getNumber() + 1;
     }
     */

    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public Slice getSlice() {
        return slice;
    }

    public void setSlice(Slice slice) {
        this.slice = slice;
    }

    public List<Employee> getVettingOfficers() {
        return vettingOfficers;
    }

    public void setVettingOfficers(List<Employee> vettingOfficers) {
        this.vettingOfficers = vettingOfficers;
    }

    public List<VettingStatus> getVettingStatuses() {
        return vettingStatuses;
    }

    public void setVettingStatuses(List<VettingStatus> vettingStatuses) {
        this.vettingStatuses = vettingStatuses;
    }

    public VettingStatus getSelctedVettingStatus() {
        return selctedVettingStatus;
    }

    public void setSelctedVettingStatus(VettingStatus selctedVettingStatus) {
        this.selctedVettingStatus = selctedVettingStatus;
    }

}
