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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class VettingAllocationBean extends BaseBean<Vetting> {
    
    private static final Logger LOG = Logger.getLogger(VettingAllocationBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    
    private List<Employee> vettingOfficers = new ArrayList<>();
    
    private Vetting selectedVettingRecord;
    private Slice slice;
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
        //slice = vettingService.findVettingRecordsByStatus(VettingStatus.PENDING_ALLOCATION, pageable);
        addCollections(slice.toList());
    }
    
    public void loadAllocateVettingForm(Vetting vettingRecord) {
        vettingOfficers.clear();
        reset().setAllocate(true);
        addEntity(vettingRecord);
        //if (vettingRecord.getVettingStatus().equals(VettingStatus.PENDING_ALLOCATION)) {
            vettingOfficers.addAll(employeeService.findEmployeesByEmployeeRole(EmployeeRoleType.INITIATOR.toString()));
        //}
    }
    
    public void allocateVetting(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setInitiatorSid(vetting.getVettingProcessingOfficer().getSid());
        vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        vettingService.update(vetting);
        synchronize(vetting);
        try {
            emailNotificationSenderService.sendNotification(NotificationType.ALLOCATE_VETTING, vetting.getCreatedDate(), vetting.getVettingProcessingOfficer(), getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingAllocationBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingAllocationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Vetting allocated successfully");
        reset().setList(true);
    }
    
    public boolean isNextPage() {
        return slice.hasNext();
    }
    
    public boolean isPreviousPage() {
        return slice.hasPrevious();
    }
    
    /*
    public void nextVettingRecords() {
        if (slice.hasNext()) {
            slice = vettingService.findEscalatedVettings(slice.nextPageable());
            addCollections(slice.toList());
        }
    }
    
    public void previousVettingRecords() {
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
    */
    public Integer getPageNumber() {
        return slice.getNumber() + 1;
    }
    
    public void cancel() {
        reset().setList(true);
    }
    
    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }
    
    public Vetting getSelectedVettingRecord() {
        return selectedVettingRecord;
    }
    
    public void setSelectedVettingRecord(Vetting selectedVettingRecord) {
        this.selectedVettingRecord = selectedVettingRecord;
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
    
}
