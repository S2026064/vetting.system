package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.CommentServiceLocal;
import sars.vetting.system.service.DocumentAttachmentServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingManagerBean extends BaseBean<Vetting> {

    private static final Logger LOG = Logger.getLogger(VettingManagerBean.class.getName());
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private DocumentAttachmentServiceLocal attachmentService;
    @Autowired
    private CommentServiceLocal commentService;

    private List<VettingStatus> vettingStatuses = new ArrayList<>();

    private VettingStatus selctedVettingStatus;
    private Vetting selectedVettingRecord;
    private Slice slice;
    private Comment comment;

    private List<Comment> comments = new ArrayList<>();

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

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        vettingStatuses.addAll(Arrays.asList(VettingStatus.values()));
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findByManagerSid(getActiveUser().getSid(), pageable);
//        addCollections(slice.toList());
        addCollections(vettingService.findByManagerSid(getActiveUser().getSid()));
    }

    public void loadReviewVettingForm(Vetting vettingRecord) {
        screeningDeclarationBean.init(vettingRecord);
        screeningBean.init(vettingRecord);
        incomeExpenditureBean.init(vettingRecord);
        attachmentBean.init(vettingRecord);
//        if (vettingRecord.getClearanceLevel() != null) {
//            gradeClearanceBean.init(vettingRecord);
//        }
        comments = vettingRecord.getComments();
        addEntity(vettingRecord);
        reset().setReview(true);
    }

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
        if (comment.getDescription().isEmpty()) {
            addWarningMessage("Please add comment.");
            return;
        }
        vettingRecord.setUpdatedBy(getActiveUser().getSid());
        vettingRecord.setUpdatedDate(new Date());
        addEntity(vettingService.update(vettingRecord));
        addInformationMessage("Vetting was successfully updated.");
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

    public void filterByStatus() {
        if (selctedVettingStatus == null) {
            addWarningMessage("Please select filter type");
            return;
        }
        getCollections().clear();
        if (selctedVettingStatus != null && !selctedVettingStatus.equals(VettingStatus.FIND_ALL)) {
            addCollections(vettingService.findByVettingStatusAndManagerSid(selctedVettingStatus, getActiveUser().getSid()));
        } else if (selctedVettingStatus.equals(VettingStatus.FIND_ALL)) {
            addCollections(vettingService.findByManagerSid(getActiveUser().getSid()));
        }
        if (getCollections().isEmpty()) {
            addInformationMessage("No vetting records of that status");
        }
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

    public void cancel() {
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
     slice = vettingService.findByManagerSid(getActiveUser().getSid(), slice.nextPageable());
     addCollections(slice.toList());
     }
     }
    
     public void previousVettingRecords() {
     if (slice.hasPrevious()) {
     slice = vettingService.findByManagerSid(getActiveUser().getSid(), slice.previousPageable());
     addCollections(slice.toList());
     }
     }
    
     public Integer getPageNumber() {
     return slice.getNumber() + 1;
     }
     */
    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public Vetting getSelectedVettingRecord() {
        return selectedVettingRecord;
    }

    public void setSelectedVettingRecord(Vetting selectedVettingRecord) {
        this.selectedVettingRecord = selectedVettingRecord;
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

    public GradeClearanceBean getGradeClearanceBean() {
        return gradeClearanceBean;
    }

    public void setGradeClearanceBean(GradeClearanceBean gradeClearanceBean) {
        this.gradeClearanceBean = gradeClearanceBean;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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
