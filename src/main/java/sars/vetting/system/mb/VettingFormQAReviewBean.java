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
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Note;
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
public class VettingFormQAReviewBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingFormQAReviewBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private DocumentAttachmentServiceLocal attachmentService;

    @ManagedProperty(value = "#{screeningBean}")
    private ScreeningBean screeningBean;
    @ManagedProperty(value = "#{incomeExpenditureBean}")
    private IncomeExpenditureBean incomeExpenditureBean;
    @ManagedProperty(value = "#{attachmentBean}")
    private AttachmentBean attachmentBean;
    @ManagedProperty(value = "#{screeningDeclarationBean}")
    private ScreeningDeclarationBean screeningDeclarationBean;
    @ManagedProperty(value = "#{gradeClearanceBean}")
    private GradeClearanceBean gradeClearanceBean;

    private List<Employee> approvers = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Note> notesAdded = new ArrayList<>();
    private final List<VettingStatus> completedVettingStatuses = new ArrayList<>();
    private final List<VettingStatus> activeVettingFileStatus = new ArrayList<>();
    private List<Vetting> completedVetting;
    private List<Vetting> activeVetting;
    List<ClearanceLevel> pools = new ArrayList<>();
    private ClearanceLevel selectedStatus;

    private Slice slice;
    private Note note;
    private int activeTab = 0;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        pools.add(ClearanceLevel.TOP_SECRETE);
        pools.add(ClearanceLevel.SECRET);
        pools.add(ClearanceLevel.CONFIDENTIAL);
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SENT_TO_QA, getActiveUser().getSid(), pageable);
//        addCollections(slice.toList());
        addCollections(vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SENT_TO_QA, getActiveUser().getSid()));
        

        //all completed vetting
        completedVettingStatuses.add(VettingStatus.VETTING_DENIED);
        completedVettingStatuses.add(VettingStatus.APPROVED);
        completedVetting = vettingService.findVettingRecordsByStatus(completedVettingStatuses);

        //all active vetting in pool
        activeVettingFileStatus.add(VettingStatus.ANALYST_POOL);
        activeVettingFileStatus.add(VettingStatus.APPROVER_POOL);
        activeVettingFileStatus.add(VettingStatus.OFFICER_POOL);
        activeVettingFileStatus.add(VettingStatus.QA_POOL);
        activeVettingFileStatus.add(VettingStatus.APPROVER_POOL);
        activeVettingFileStatus.add(VettingStatus.FINAL_APPROVER_POOL);
        activeVetting = vettingService.findVettingRecordsByStatus(activeVettingFileStatus);
    }

    public void loadQAReviewVettingForm(Vetting vettingRecord) {
        screeningDeclarationBean.init(vettingRecord);
        screeningBean.init(vettingRecord);
        incomeExpenditureBean.init(vettingRecord);
        attachmentBean.init(vettingRecord);
        if (vettingRecord.getClearanceLevel() != null) {
            gradeClearanceBean.init(vettingRecord);
        }
        comments = vettingRecord.getComments();
        notesAdded = vettingRecord.getNotes();
        addEntity(vettingRecord);
        reset().setQaReview(true);
    }

    public void vettingClaim() {
        if (getCollections().size() < 10) {
//            Vetting allocatedVetting = vettingService.findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus.QA_POOL, getActiveUser().getSid());
            Vetting allocatedVetting = vettingService.findTopByVettingStatusAndClearanceLevelAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus.QA_POOL, selectedStatus, getActiveUser().getSid());
            if (allocatedVetting != null) {
                allocatedVetting.setUpdatedBy(getActiveUser().getSid());
                allocatedVetting.setUpdatedDate(new Date());
                allocatedVetting.setVettingStatus(VettingStatus.SENT_TO_QA);
                allocatedVetting.setVettingProcessingOfficer(getActiveUser().getEmployee());
                allocatedVetting.setQaSid(getActiveUser().getSid());
                allocatedVetting.setDateSentQa(new Date());
                vettingService.update(allocatedVetting);
                addCollection(allocatedVetting);
                try {
                    emailNotificationSenderService.sendNotification(NotificationType.ALLOCATED_OFFICER, allocatedVetting.getCreatedDate(), allocatedVetting.getVettingProcessingOfficer(), getActiveUser().getEmployee());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VettingFormReviewBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VettingFormReviewBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                addWarningMessage("There is no vetting in a pool");
            }
        } else {
            addWarningMessage("You need to complete review of the vetting claimed before claiming new vetting");
        }
        reset().setList(true);
    }

    public void save(Vetting vettingRecord) {
        vettingRecord.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vettingRecord.setScreening(screeningBean.getScreening());
        vettingRecord.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vettingRecord.addAttachments(attachmentBean.getAttachments());
        for (Attachment supportingDocument : vettingRecord.getAttachments()) {
            if (supportingDocument.getId() == null) {
                JsonDocumentDto jsonDocumentDto = attachmentBean.uploadDocumentsToServer(supportingDocument);
                JSONObject myResponse;
                myResponse = new JSONObject(attachmentService.uploadDocument(jsonDocumentDto));
                String objectId = myResponse.getString("objectId");
                supportingDocument.setCode(objectId);
            }
        }
        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        addEntity(vettingService.update(vettingRecord));
        addInformationMessage("Vetting was successfully updated.");
        this.redirect("qaReview");
        reset().setList(true);
    }

    public void allocateVetting(Vetting vettingRecord) {
        vettingRecord.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vettingRecord.setScreening(screeningBean.getScreening());
        vettingRecord.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vettingRecord.addAttachments(attachmentBean.getAttachments());
        for (Attachment supportingDocument : vettingRecord.getAttachments()) {
            if (supportingDocument.getId() == null) {
                JsonDocumentDto jsonDocumentDto = attachmentBean.uploadDocumentsToServer(supportingDocument);
                JSONObject myResponse;
                myResponse = new JSONObject(attachmentService.uploadDocument(jsonDocumentDto));
                String objectId = myResponse.getString("objectId");
                supportingDocument.setCode(objectId);
            }
        }

        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        vettingRecord.setVettingStatus(VettingStatus.CLEARANCE_ISSUED);
        Employee employee = employeeService.findByEmployeeRole(EmployeeRoleType.APPROVER.toString());
        vettingRecord.setVettingProcessingOfficer(employee);
        vettingRecord.setFirstApproverSid(employee.getSid());
        if (vettingRecord.getFirstApproverSid() != null) {
            vettingRecord.setVettingProcessingOfficer(employeeService.findBySid(vettingRecord.getFirstApproverSid()));
            vettingRecord.setVettingStatus(VettingStatus.CLEARANCE_ISSUED);
        } else {
            vettingRecord.setVettingStatus(VettingStatus.APPROVER_POOL);
        }
        if (note.getDescription().isEmpty()) {
            getEntity().removeNote(note);
        }
        vettingService.update(vettingRecord);
        addInformationMessage("Vetting successfully allocated to Approver pool");
        synchronize(vettingRecord);
        reset().setList(true);
        try {
            emailNotificationSenderService.sendNotification(NotificationType.SEND_TO_APPROVER, vettingRecord.getCreatedDate(), employee, getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormQAReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormQAReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rework(Vetting vetting) {
        if (vetting == null) {
            addWarningMessage("Vetting you have selected does not exists.");
            return;
        }

        if (note.getDescription().isEmpty()) {
            addWarningMessage("Please add note.");
            return;
        }

        vetting.setVettingStatus(VettingStatus.SSA_PROCESSING);
        vetting.setVettingProcessingOfficer(employeeService.findBySid(vetting.getOfficerSid()));
        vettingService.update(vetting);
        addInformationMessage("Vetting have been sended to rework  successfully");
        synchronize(vetting);
        try {
            emailNotificationSenderService.sendNotification(NotificationType.REWORK, vetting.getCreatedDate(), vetting.getVettingProcessingOfficer(), getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormQAReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormQAReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        reset().setList(true);
    }

    //note 
    public void addNote(String clickedButton) {
        reset().setNotes(true);
        note = new Note();
        note.setCreatedBy(getActiveUser().getSid());
        note.setCreatedDate(new Date());
        note.setLoggedOnUserFullName(getActiveUser().getFullName());
        note.setRoleDescription(getActiveUser().getEmployeeRole().getDescription());
        note.setClickedButton(clickedButton);
        getEntity().addNote(note);
    }

    public void updateComment(Note persistentNote) {
        reset().setNotes(true);
        persistentNote.setUpdatedBy(getActiveUser().getSid());
        persistentNote.setUpdatedDate(new Date());
        note = persistentNote;
    }

    public void deleteComment(Note note) {
        synchronise(getEntity(), note);
        if (note.getId() != null) {
            addEntity(vettingService.update(getEntity()));
        }
        addCollection(getEntity());
        addInformationMessage("Note successfully deleted");
    }

    private void synchronise(Vetting vetting, Note note) {
        vetting.removeNote(note);
        getCollections().remove(vetting);
    }

    public void cancelNote(Note note) {
        if (note.getId() == null) {
            if (getEntity().getNotes().contains(note)) {
                getEntity().removeNote(note);
            }
        }
        reset().setList(true);
    }

    /*
     public boolean isNextPage() {
     return slice.hasNext();
     }

     public boolean isPreviousPage() {
     return slice.hasPrevious();
     }

     public void nextVettingRecords() {
     if (slice.hasNext()) {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.SENT_TO_QA, slice.nextPageable());
     addCollections(slice.toList());
     }
     }

     public void previousVettingRecords() {
     if (slice.hasPrevious()) {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.SENT_TO_QA, slice.previousPageable());
     addCollections(slice.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.SENT_TO_QA, slice.nextOrLastPageable());
     addCollections(slice.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.SENT_TO_QA, slice.previousOrFirstPageable());
     addCollections(slice.toList());
     }

     public Integer getPageNumber() {
     return slice.getNumber();
     }

     public void save(Employee employee) {

     }
     */
    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public List<Employee> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<Employee> approvers) {
        this.approvers = approvers;
    }

    public Slice getSlice() {
        return slice;
    }

    public void setSlice(Slice slice) {
        this.slice = slice;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public GradeClearanceBean getGradeClearanceBean() {
        return gradeClearanceBean;
    }

    public void setGradeClearanceBean(GradeClearanceBean gradeClearanceBean) {
        this.gradeClearanceBean = gradeClearanceBean;
    }

    public List<Note> getNotesAdded() {
        return notesAdded;
    }

    public void setNotesAdded(List<Note> notesAdded) {
        this.notesAdded = notesAdded;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public List<Vetting> getCompletedVetting() {
        return completedVetting;
    }

    public void setCompletedVetting(List<Vetting> completedVetting) {
        this.completedVetting = completedVetting;
    }

    public List<Vetting> getActiveVetting() {
        return activeVetting;
    }

    public void setActiveVetting(List<Vetting> activeVetting) {
        this.activeVetting = activeVetting;
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    public List<ClearanceLevel> getPools() {
        return pools;
    }

    public void setPools(List<ClearanceLevel> pools) {
        this.pools = pools;
    }

    public ClearanceLevel getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(ClearanceLevel selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public void next() {
        if (activeTab < 7) {
            activeTab++;
        }
    }

    public void prev() {
        if (activeTab > 0) {
            activeTab--;
        }
    }
}
