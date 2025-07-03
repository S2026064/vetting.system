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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.DocumentAttachmentServiceLocal;
import sars.vetting.system.service.EmailNotificationSenderServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingResponseBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingResponseBean.class.getName());
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private DocumentAttachmentServiceLocal attachmentService;
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private Slice slices;
    private List<VettingStatus> vettingStatuses = new ArrayList<>();
    private VettingStatus selctedVettingStatus;
    private String idNumber;

    @ManagedProperty(value = "#{certificateBean}")
    private CertificateBean certificateBean;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        vettingStatuses.addAll(Arrays.asList(VettingStatus.values()));
//        Pageable pageable = PageRequest.of(0, 10);
//        slices = vettingService.findAll(pageable);
//        addCollections(slices.toList());
        vettingStatuses.add(VettingStatus.APPROVED);
        vettingStatuses.add(VettingStatus.VETTING_DENIED);
        vettingStatuses.add(VettingStatus.FIND_ALL);
        addCollections(vettingService.findVettingRecordsByStatus(vettingStatuses));
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

    public void loadReviewVettingForm(Vetting vettingRecord) {
        certificateBean.init(vettingRecord);
        addEntity(vettingRecord);
        reset().setReview(true);
    }

    public void save(Vetting vettingRecord) {
        vettingRecord.addCerificats(certificateBean.getCertificates());
        for (Attachment supportingDocument : vettingRecord.getAttachments()) {
            if (supportingDocument.getId() == null) {
                JsonDocumentDto jsonDocumentDto = certificateBean.uploadDocumentsToServer(supportingDocument);
                JSONObject myResponse;
                myResponse = new JSONObject(attachmentService.uploadDocument(jsonDocumentDto));
                String objectId = myResponse.getString("objectId");
                supportingDocument.setCode(objectId);
            }
        }
        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        vettingRecord.setVettingStatus(VettingStatus.CERTIFICATE);
        addEntity(vettingService.update(vettingRecord));
        addInformationMessage("Vetting was successfully updated.");
        reset().setList(true);
        try {
            Employee subject = employeeService.findBySid(vettingRecord.getSubjectSid());
            emailNotificationSenderService.sendNotification(NotificationType.CERTIFICATE, getEntity().getCreatedDate(), subject, getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingInitiationBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingInitiationBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public CertificateBean getCertificateBean() {
        return certificateBean;
    }

    public void setCertificateBean(CertificateBean certificateBean) {
        this.certificateBean = certificateBean;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

}
