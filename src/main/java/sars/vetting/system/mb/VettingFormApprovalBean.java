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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.Note;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.CommentServiceLocal;
import sars.vetting.system.service.EmailNotificationSenderServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingFormApprovalBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingFormApprovalBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private CommentServiceLocal commentService;

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

    private List<Comment> comments = new ArrayList<>();
    private List<Note> notesAdded = new ArrayList<>();
    private final List<VettingStatus> completedVettingStatuses = new ArrayList<>();
    private final List<VettingStatus> activeVettingFileStatus = new ArrayList<>();
    private List<Vetting> completedVetting;
    private List<Vetting> activeVetting;

    private Slice vettingSlice;
    private Comment comment;
    private Note note;
    private int activeTab = 0;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        vettingSlice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.CLEARANCE_ISSUED, getActiveUser().getSid(), pageable);
//        addCollections(vettingSlice.toList());
        addCollections(vettingService.findVettingRecordsBySidAndStatus(VettingStatus.CLEARANCE_ISSUED, getActiveUser().getSid()));
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

    public void loadApprovalVettingForm(Vetting vettingRecord) {
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
        reset().setApprove(true);
    }

    public void vettingClaim() {
        if (getCollections().size() < 10) {
            Vetting allocatedVetting = vettingService.findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus.APPROVER_POOL, getActiveUser().getSid());
            if (allocatedVetting != null) {
                allocatedVetting.setUpdatedBy(getActiveUser().getSid());
                allocatedVetting.setUpdatedDate(new Date());
                allocatedVetting.setVettingStatus(VettingStatus.CLEARANCE_ISSUED);
                allocatedVetting.setVettingProcessingOfficer(getActiveUser().getEmployee());
                allocatedVetting.setFirstApproverSid(getActiveUser().getSid());
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

    public void nextVettingRecords() {
        if (vettingSlice.hasNext()) {
            vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, vettingSlice.nextPageable());
            addCollections(vettingSlice.toList());
        }
    }

    public void denied(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.CLEARANCE_DENIED);
        vetting.setFirstApproverSid(getActiveUser().getSid());
        Employee employee = employeeService.findByEmployeeRole(EmployeeRoleType.SECOND_LEVEL_APPROVER.toString());
        vetting.setVettingProcessingOfficer(employee);
        vettingService.update(vetting);
        synchronize(vetting);
        try {
            emailNotificationSenderService.sendNotificationApproval(NotificationType.DENIED_VETTING, vetting.getCreatedDate(), employee, getActiveUser().getEmployee(), vetting);
//            emailNotificationSenderService.sendNotification(NotificationType.DENIED_VETTING, vetting.getCreatedDate(), employee, getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Vetting successfully Declined");
        reset().setList(true);
    }

    public void approveVetting(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.APPROVED);
        vettingService.update(vetting);
        synchronize(vetting);
        Employee employee = employeeService.findBySid(vetting.getSubjectSid());
        try {
            emailNotificationSenderService.sendNotificationApproval(NotificationType.VETTING_CONCLUDED, vetting.getCreatedDate(), employee, getActiveUser().getEmployee(), vetting);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Vetting successfully Approved");
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

        vetting.setVettingStatus(VettingStatus.SENT_TO_QA);
        vetting.setVettingProcessingOfficer(employeeService.findBySid(vetting.getQaSid()));
        vettingService.update(vetting);
        addInformationMessage("Vetting have been sendback for rework  successfully");
        synchronize(vetting);
        try {
            emailNotificationSenderService.sendNotification(NotificationType.REWORK, vetting.getCreatedDate(), vetting.getVettingProcessingOfficer(), getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        reset().setList(true);
    }

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
     return vettingSlice.hasNext();
     }

     public boolean isPreviousPage() {
     return vettingSlice.hasPrevious();
     }

     public void previousVettingRecords() {
     if (vettingSlice.hasPrevious()) {
     vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, vettingSlice.previousPageable());
     addCollections(vettingSlice.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, vettingSlice.nextOrLastPageable());
     addCollections(vettingSlice.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_ISSUED, vettingSlice.previousOrFirstPageable());
     addCollections(vettingSlice.toList());
     }

     public Integer getPageNumber() {
     return vettingSlice.getNumber();
     }
     */
    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public Slice getSlice() {
        return vettingSlice;
    }

    public void setSlice(Slice vettingSlice) {
        this.vettingSlice = vettingSlice;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Vetting> getCompletedVetting() {
        return completedVetting;
    }

    public void setCompletedVetting(List<Vetting> completedVetting) {
        this.completedVetting = completedVetting;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
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
        if (activeTab < 3) {
            activeTab++;
        }
    }

    public void prev() {
        if (activeTab > 0) {
            activeTab--;
        }
    }

}
