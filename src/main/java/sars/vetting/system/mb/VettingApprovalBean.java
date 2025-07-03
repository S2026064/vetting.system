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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Comment;
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
public class VettingApprovalBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingApprovalBean.class.getName());
    @Autowired
    private DocumentAttachmentServiceLocal attachmentService;
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

    private List<Comment> comments = new ArrayList<>();

    @ManagedProperty(value = "#{screeningBean}")
    private ScreeningBean screeningBean;
    @ManagedProperty(value = "#{incomeExpenditureBean}")
    private IncomeExpenditureBean incomeExpenditureBean;
    @ManagedProperty(value = "#{attachmentBean}")
    private AttachmentBean attachmentBean;
    @ManagedProperty(value = "#{screeningDeclarationBean}")
    private ScreeningDeclarationBean screeningDeclarationBean;

    private Slice slice;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.CLEARANCE_ISSUED, getActiveUser().getSid(), pageable);
//        addCollections(slice.toList());
        addCollections(vettingService.findVettingRecordsBySidAndStatus(VettingStatus.CLEARANCE_ISSUED, getActiveUser().getSid()));
    }

    public void loadApprovalVettingForm(Vetting vettingRecord) {
        screeningDeclarationBean.init(vettingRecord);
        screeningBean.init(vettingRecord);
        incomeExpenditureBean.init(vettingRecord);
        attachmentBean.init(vettingRecord);
        comments = vettingRecord.getComments();
        addEntity(vettingRecord);
        reset().setReview(true);
    }

    public void nextVettingRecords() {
        if (slice.hasNext()) {
            slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, slice.nextPageable());
            addCollections(slice.toList());
        }
    }

    public void denied(Vetting vetting) {
        vetting.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vetting.setScreening(screeningBean.getScreening());
        vetting.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vetting.addAttachments(attachmentBean.getAttachments());
        for (Attachment supportingDocument : vetting.getAttachments()) {
            if (supportingDocument.getId() == null) {
                JsonDocumentDto jsonDocumentDto = attachmentBean.uploadDocumentsToServer(supportingDocument);
                JSONObject myResponse;
                myResponse = new JSONObject(attachmentService.uploadDocument(jsonDocumentDto));
                String objectId = myResponse.getString("objectId");
                supportingDocument.setCode(objectId);
            }
        }
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.CLEARANCE_DENIED);
        vettingService.update(vetting);
        reset().setList(true);
//        emailNotificationSenderService.sendEmailNotification(NotificationType.DENIED_VETTING, vetting.getCreatedDate(), vetting.getEmployee(), getActiveUser().getEmployee());
    }

    public void approveVetting(Vetting vetting) {
        vetting.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vetting.setScreening(screeningBean.getScreening());
        vetting.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vetting.addAttachments(attachmentBean.getAttachments());
        for (Attachment supportingDocument : vetting.getAttachments()) {
            if (supportingDocument.getId() == null) {
                JsonDocumentDto jsonDocumentDto = attachmentBean.uploadDocumentsToServer(supportingDocument);
                JSONObject myResponse;
                myResponse = new JSONObject(attachmentService.uploadDocument(jsonDocumentDto));
                String objectId = myResponse.getString("objectId");
                supportingDocument.setCode(objectId);
            }
        }
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.APPROVED);
        vettingService.update(vetting);
        reset().setList(true);
        Employee employee = employeeService.findBySid(vetting.getSubjectSid());
        try {
            emailNotificationSenderService.sendNotification(NotificationType.VETTING_CONCLUDED, vetting.getCreatedDate(), employee, getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void previousVettingRecords() {
        if (slice.hasPrevious()) {
            slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, slice.previousPageable());
            addCollections(slice.toList());
        }
    }

    public void getNextOrLastVettingRecord() {
        slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, slice.nextOrLastPageable());
        addCollections(slice.toList());
    }

    public void getPreviousOrFirstVettingRecord() {
        slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, slice.previousOrFirstPageable());
        addCollections(slice.toList());
    }

    public Integer getPageNumber() {
        return slice.getNumber();
    }

    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ScreeningBean getScreeningBean() {
        return screeningBean;
    }

    public void setScreeningBean(ScreeningBean screeningBean) {
        this.screeningBean = screeningBean;
    }

    public IncomeExpenditureBean getIncomeExpenditureBean() {
        return incomeExpenditureBean;
    }

    public void setIncomeExpenditureBean(IncomeExpenditureBean incomeExpenditureBean) {
        this.incomeExpenditureBean = incomeExpenditureBean;
    }

    public AttachmentBean getAttachmentBean() {
        return attachmentBean;
    }

    public void setAttachmentBean(AttachmentBean attachmentBean) {
        this.attachmentBean = attachmentBean;
    }

    public ScreeningDeclarationBean getScreeningDeclarationBean() {
        return screeningDeclarationBean;
    }

    public void setScreeningDeclarationBean(ScreeningDeclarationBean screeningDeclarationBean) {
        this.screeningDeclarationBean = screeningDeclarationBean;
    }

    public Slice getSlice() {
        return slice;
    }

    public void setSlice(Slice slice) {
        this.slice = slice;
    }

}
