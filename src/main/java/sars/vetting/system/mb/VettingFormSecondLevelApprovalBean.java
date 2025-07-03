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
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.Note;
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
public class VettingFormSecondLevelApprovalBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingFormSecondLevelApprovalBean.class.getName());
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;

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
    private final List<VettingStatus> completedVettingStatuses = new ArrayList<>();
    private final List<VettingStatus> activeVettingFileStatus = new ArrayList<>();
    private List<Note> notesAdded = new ArrayList<>();
    private List<Vetting> completedVetting;
    private List<Vetting> activeVetting;

    private Comment comment;
    private boolean isRejectDenied;
    private Slice slice;
    private int activeTab = 0;

    private Note note;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findVettingRecordsBySidAndStatus(VettingStatus.CLEARANCE_DENIED, getActiveUser().getSid(), pageable);
//        addCollections(slice.toList());

        addCollections(vettingService.findVettingRecordsBySidAndStatus(VettingStatus.CLEARANCE_DENIED, getActiveUser().getSid()));

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

    public void loadSecondLevelApprovalVettingForm(Vetting vettingRecord) {
        screeningDeclarationBean.init(vettingRecord);
        screeningBean.init(vettingRecord);
        incomeExpenditureBean.init(vettingRecord);
        if (vettingRecord.getClearanceLevel() != null) {
            gradeClearanceBean.init(vettingRecord);
        }
        attachmentBean.init(vettingRecord);
        comments = vettingRecord.getComments();
        notesAdded = vettingRecord.getNotes();
        addEntity(vettingRecord);
        reset().setSecondLevelApproval(true);
    }

    public void vettingClaim() {
        if (getCollections().size() < 10) {
            Vetting allocatedVetting = vettingService.findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus.CLEARANCE_DENIED, getActiveUser().getSid());
            if (allocatedVetting != null) {
                allocatedVetting.setUpdatedBy(getActiveUser().getSid());
                allocatedVetting.setUpdatedDate(new Date());
                allocatedVetting.setVettingProcessingOfficer(getActiveUser().getEmployee());
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

    public void approveVetting(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.VETTING_DENIED);
        vettingService.update(vetting);
        synchronize(vetting);
        Employee employee = employeeService.findBySid(vetting.getSubjectSid());
        try {
            emailNotificationSenderService.sendNotificationApproval(NotificationType.DENIED_VETTING, vetting.getCreatedDate(), employee, getActiveUser().getEmployee(), vetting);
//            emailNotificationSenderService.sendNotificationApproval(NotificationType.VETTING_CONCLUDED, vetting.getCreatedDate(), employee, getActiveUser().getEmployee(), vetting);
//            emailNotificationSenderService.sendNotification(NotificationType.VETTING_CONCLUDED, vetting.getCreatedDate(), employee, getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingFormSecondLevelApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingFormSecondLevelApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Vetting successfully Approved");
        reset().setList(true);
    }

    public void rejectDenied(Vetting vetting) {
        if (note.getDescription().isEmpty()) {
            addWarningMessage("Please add comment.");
            return;
        }
        vetting.setVettingStatus(VettingStatus.CLEARANCE_ISSUED);
        vetting.setVettingProcessingOfficer(employeeService.findBySid(vetting.getFirstApproverSid()));
        vettingService.update(vetting);
        synchronize(vetting);
        addInformationMessage("The vetting rejected Sucessfully");
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
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_DENIED, slice.nextPageable());
     addCollections(slice.toList());
     }
     }

     public void previousVettingRecords() {
     if (slice.hasPrevious()) {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_DENIED, slice.previousPageable());
     addCollections(slice.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_DENIED, slice.nextOrLastPageable());
     addCollections(slice.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     slice = vettingService.findVettingRecordsByStatus(VettingStatus.CLEARANCE_DENIED, slice.previousOrFirstPageable());
     addCollections(slice.toList());
     }

     public Integer getPageNumber() {
     return slice.getNumber() + 1;
     }
     */

    public void cancel(Vetting vetting) {
        if (vetting.getId() == null && getCollections().contains(vetting)) {
            remove(vetting);
        }
        reset().setList(true);

    }

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public boolean isIsRejectDenied() {
        return isRejectDenied;
    }

    public void setIsRejectDenied(boolean isRejectDenied) {
        this.isRejectDenied = isRejectDenied;
    }

    public GradeClearanceBean getGradeClearanceBean() {
        return gradeClearanceBean;
    }

    public void setGradeClearanceBean(GradeClearanceBean gradeClearanceBean) {
        this.gradeClearanceBean = gradeClearanceBean;
    }

    public List<Vetting> getCompletedVetting() {
        return completedVetting;
    }

    public void setCompletedVetting(List<Vetting> completedVetting) {
        this.completedVetting = completedVetting;
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
