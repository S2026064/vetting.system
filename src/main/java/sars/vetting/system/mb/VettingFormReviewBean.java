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
import org.apache.commons.lang3.StringUtils;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.AppliableSecurityLevel;
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.StepOutcome;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Note;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.CommentServiceLocal;
import sars.vetting.system.service.DOPIServiceLocal;
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
public class VettingFormReviewBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingFormReviewBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private CommentServiceLocal commentService;
    @Autowired
    private DOPIServiceLocal dOPIService;
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

    private List<Employee> officers = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Note> notesAdded = new ArrayList<>();
    private final List<VettingStatus> completedVettingStatuses = new ArrayList<>();
    private final List<VettingStatus> activeVettingFileStatus = new ArrayList<>();
    private List<Vetting> completedVetting;
    private List<Vetting> activeVetting;

    private Slice slice;
    private Comment comment;
    private Note note;
    private int activeTab = 0;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SARS_PROCESSING, getActiveUser().getSid(), pageable);
//        addCollections(slice.toList());

        addCollections(vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SARS_PROCESSING, getActiveUser().getSid()));

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

    public void loadReviewVettingForm(Vetting vettingRecord) {
        screeningDeclarationBean.init(vettingRecord);
        screeningBean.init(vettingRecord);
        incomeExpenditureBean.init(vettingRecord);
        gradeClearanceBean.init(vettingRecord);
        attachmentBean.init(vettingRecord);
        comments = vettingRecord.getComments();
        notesAdded = vettingRecord.getNotes();
        addEntity(vettingRecord);
        reset().setReview(true);
    }

    public void vettingClaim() {
        if (getCollections().size() < 10) {
            Vetting allocatedVetting = vettingService.findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus.ANALYST_POOL, getActiveUser().getSid());
            if (allocatedVetting != null) {
                allocatedVetting.setUpdatedBy(getActiveUser().getSid());
                allocatedVetting.setUpdatedDate(new Date());
                allocatedVetting.setVettingStatus(VettingStatus.SARS_PROCESSING);
                allocatedVetting.setVettingProcessingOfficer(getActiveUser().getEmployee());
                allocatedVetting.setAnalystSid(getActiveUser().getSid());

                if (StringUtils.isNotEmpty(allocatedVetting.getEmployee().getIdentityNumber())) {
                    //pull screening record here
                    allocatedVetting.getScreening().addCriminalRecords(dOPIService.getCriminalRecordByEmployeeIdnumber(allocatedVetting.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
                    allocatedVetting.getScreening().addcreditRecords(dOPIService.getCreditRecordByEmployeeIdnumber(allocatedVetting.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
                    allocatedVetting.getScreening().addInternalInvestigationRecords(dOPIService.getInternaInvestigationByEmployeeIdnumber(allocatedVetting.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
                    allocatedVetting.getScreening().addDisciplinaryRecords(dOPIService.getDisciplinaryByEmployeeIdnumber(allocatedVetting.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
                    allocatedVetting.setScreening(dOPIService.getScreeningByIdNumber(allocatedVetting.getEmployee().getIdentityNumber(), getActiveUser().getSid()));
                }
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

//    public void allocateVetting(Vetting vetting) {
//        vetting.setUpdatedBy(getActiveUser().getSid());
//        vetting.setUpdatedDate(new Date());
//        if (vetting.getOfficerSid() != null) {
//            vetting.setVettingProcessingOfficer(employeeService.findBySid(vetting.getOfficerSid()));
//            vetting.setVettingStatus(VettingStatus.SSA_PROCESSING);
//        } else {
//            vetting.setVettingStatus(VettingStatus.OFFICER_POOL);
//        }
//        if (note.getDescription().isEmpty()) {
//            getEntity().removeNote(note);
//        }
//        vettingService.update(vetting);
//        addInformationMessage("Vetting successfully allocated to officer pool");
//        synchronize(vetting);
//        reset().setList(true);
//    }
//    public void save(Vetting vetting) {
//        vetting.setUpdatedBy(getActiveUser().getSid());
//        vetting.setUpdatedDate(new Date());
//        vetting.setVettingStatus(VettingStatus.SARS_PROCESSING);
//        vettingService.update(vetting);
//        addInformationMessage("Vetting successfully saved");
//        synchronize(vetting);
//        reset().setList(true);
//    }
    public void save(Vetting vettingRecord) {
        vettingRecord.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vettingRecord.setScreening(screeningBean.getScreening());
        vettingRecord.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vettingRecord.setGradeClearance(gradeClearanceBean.getGradeClearance());
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

        if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel() != null) {
            if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel().equals(AppliableSecurityLevel.CONFIDENTIAL)) {
                vettingRecord.setClearanceLevel(ClearanceLevel.CONFIDENTIAL);
            }

            if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel().equals(AppliableSecurityLevel.SECRET)) {
                vettingRecord.setClearanceLevel(ClearanceLevel.SECRET);
            }

            if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel().equals(AppliableSecurityLevel.TOP_SECRET)) {
                vettingRecord.setClearanceLevel(ClearanceLevel.TOP_SECRETE);
            }
        }
        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        addEntity(vettingService.update(vettingRecord));
        addInformationMessage("Vetting was successfully updated.");
        reset().setList(true);
    }

    public void allocateVetting(Vetting vettingRecord) {
        vettingRecord.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vettingRecord.setScreening(screeningBean.getScreening());
        vettingRecord.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vettingRecord.setGradeClearance(gradeClearanceBean.getGradeClearance());
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

        if (gradeClearanceBean.getEntity().getNationalJobRole() == null) {
            addWarningMessage("Please complete the grade clearance of the role");
            return;
        }

        if (gradeClearanceBean.getEntity().getOutcome().equals(StepOutcome.MEDIUM_LOW.toString())) {
            if (!gradeClearanceBean.getEntity().getSustainableJobRole().isEmpty() && (gradeClearanceBean.getEntity().getResponsibilityA() == null && gradeClearanceBean.getEntity().getResponsibilityB() == null && gradeClearanceBean.getEntity().getResponsibilityC() == null)) {
                addWarningMessage("Please select Suitability requirement job role responsibility");
                return;
            }
        }

        if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel() != null) {
            if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel().equals(AppliableSecurityLevel.CONFIDENTIAL)) {
                vettingRecord.setClearanceLevel(ClearanceLevel.CONFIDENTIAL);
            }

            if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel().equals(AppliableSecurityLevel.SECRET)) {
                vettingRecord.setClearanceLevel(ClearanceLevel.SECRET);
            }

            if (gradeClearanceBean.getGradeClearance().getAppliableSecurityLevel().equals(AppliableSecurityLevel.TOP_SECRET)) {
                vettingRecord.setClearanceLevel(ClearanceLevel.TOP_SECRETE);
            }
        }
        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        if (vettingRecord.getOfficerSid() != null) {
            vettingRecord.setVettingProcessingOfficer(employeeService.findBySid(vettingRecord.getOfficerSid()));
            vettingRecord.setVettingStatus(VettingStatus.SSA_PROCESSING);
        } else {
            vettingRecord.setVettingStatus(VettingStatus.OFFICER_POOL);
        }
        if (note.getDescription().isEmpty()) {
            getEntity().removeNote(note);
        }
        vettingService.update(vettingRecord);
        addInformationMessage("Vetting successfully allocated to officer pool");
        synchronize(vettingRecord);
        reset().setList(true);
    }

    public void saveComment(Comment comment) {
        if (comment.getId() != null) {
            commentService.update(comment);
        } else {
            getEntity().addComment(comment);
            addEntity(vettingService.update(getEntity()));
        }
        reset().setList(true);

    }

    public void addComment(String clickedButton) {
        reset().setCommentView(true);
        comment = new Comment();
        comment.setCreatedBy(getActiveUser().getSid());
        comment.setCreatedDate(new Date());
        comment.setLoggedOnUserFullName(getActiveUser().getFullName());
        comment.setClickedButton(clickedButton);
        getEntity().addComment(comment);
    }

    public void cancelComment(Comment comment) {
        if (comment.getId() == null && getEntity().getComments().contains(comment)) {
            getEntity().removeComment(comment);
        }
        reset().setList(true);
    }

    public void rework(Vetting vetting) {
        if (vetting == null) {
            addWarningMessage("Vetting you have selected does not exists.");
            return;
        }

        if (comment.getDescription().isEmpty()) {
            addWarningMessage("Please add comment.");
            return;
        }

        vetting.setVettingStatus(VettingStatus.REWORK);
        vetting.setEmployee(employeeService.findBySid(vetting.getSubjectSid()));
        vettingService.update(vetting);
        addInformationMessage("Vetting have been sended to rework  successfully");
        synchronize(vetting);
        try {
            emailNotificationSenderService.sendNotification(NotificationType.REWORK, vetting.getCreatedDate(), vetting.getEmployee(), getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        reset().setList(true);
    }

    //note to analyst
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
     slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SARS_PROCESSING, getActiveUser().getSid(), slice.nextPageable());
     addCollections(slice.toList());
     }
     }

     public void previousVettingRecords() {
     if (slice.hasPrevious()) {
     slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SARS_PROCESSING, getActiveUser().getSid(), slice.previousPageable());
     addCollections(slice.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SARS_PROCESSING, getActiveUser().getSid(), slice.nextOrLastPageable());
     addCollections(slice.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.SARS_PROCESSING, getActiveUser().getSid(), slice.previousOrFirstPageable());
     addCollections(slice.toList());
     }

     public Integer getPageNumber() {
     return slice.getNumber() + 1;
     }
     */
    public GradeClearanceBean getGradeClearanceBean() {
        return gradeClearanceBean;
    }

    public void setGradeClearanceBean(GradeClearanceBean gradeClearanceBean) {
        this.gradeClearanceBean = gradeClearanceBean;
    }

    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public List<Employee> getOfficers() {
        return officers;
    }

    public void setOfficers(List<Employee> officers) {
        this.officers = officers;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
