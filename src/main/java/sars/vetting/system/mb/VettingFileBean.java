package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.EmployeeRoleServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingFileBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingFileBean.class.getName());
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;
    
      @Autowired
    private EmployeeServiceLocal employeeService;

    private Slice<Vetting> slices;
    private List<VettingStatus> vettingStatuses = new ArrayList<>();
    private VettingStatus selctedVettingStatus;
    private String idNumber;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        Pageable pageable = PageRequest.of(0, 10);
        vettingStatuses.addAll(Arrays.asList(VettingStatus.values()));
        if (getActiveUser().getEmployee().getEmployeeRole().equals(employeeRoleService.findByDescription("Super Administrator")) || getActiveUser().getEmployee().getEmployeeRole().equals(employeeRoleService.findByDescription("Administrator"))) {
            addCollections(vettingService.listAll());
        } else if (getActiveUser().getEmployee().getEmployeeRole().equals(employeeRoleService.findByDescription("Manager"))) {
              addCollections(vettingService.findByManagerSid(getActiveUser().getEmployee().getSid()));
        } else {
            slices = vettingService.findVettingBySid(getActiveUser().getEmployee().getSid(), pageable);
            addCollections(slices.toList());
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
            addCollections(vettingService.listAll());
        }
        if (getCollections().isEmpty()) {
            addInformationMessage("No vetting records of that status");
        }
    }

    public void filterByIdNumber() {
        if (StringUtils.isEmpty(idNumber)) {
            addWarningMessage("Please enter id number");
            return;
        }
        getCollections().clear();
        if (StringUtils.isNotEmpty(idNumber)) {
            addCollection(vettingService.findByIdNumber(idNumber));
        } else {
            addCollections(vettingService.listAll());
        }
        if (getCollections().isEmpty()) {
            addInformationMessage("No vetting records of that status");
        }
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
     slices = vettingService.findAll(slices.nextPageable());
     addCollections(slices.toList());
     }
     }
    
     public void previousDocuments() {
     if (slices.hasPrevious()) {
     slices = vettingService.findAll(slices.previousPageable());
     addCollections(slices.toList());
     }
     }
    
     public void getNextOrLastVettingRecord() {
     slices = vettingService.findAll(slices.nextOrLastPageable());
     addCollections(slices.toList());
     }
    
     public void getPreviousOrFirstVettingRecord() {
     slices = vettingService.findAll(slices.previousOrFirstPageable());
     addCollections(slices.toList());
     }
    
     public Integer getPageNumber() {
     return slices.getNumber() + 1;
     }
     */
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public Slice getSlices() {
        return slices;
    }

    public void setSlices(Slice slices) {
        this.slices = slices;
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

    public VettingServiceLocal getVettingService() {
        return vettingService;
    }

    public void setVettingService(VettingServiceLocal vettingService) {
        this.vettingService = vettingService;
    }
}
