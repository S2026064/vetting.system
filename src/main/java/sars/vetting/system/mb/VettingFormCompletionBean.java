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
import sars.vetting.system.common.AttachmentType;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.ScreeningResponseOption;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.common.VettingType;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.JsonDocumentDto;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.CommentServiceLocal;
import sars.vetting.system.service.DocumentAttachmentServiceLocal;
import sars.vetting.system.service.EmailNotificationSenderServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2026080
 */
@ManagedBean
@ViewScoped
public class VettingFormCompletionBean extends BaseBean<Vetting> {

    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private DocumentAttachmentServiceLocal attachmentService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private CommentServiceLocal commentService;

    private AttachmentType attachmentType;

    private final List<VettingStatus> vettingStatuses = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    @ManagedProperty(value = "#{screeningBean}")
    private ScreeningBean screeningBean;
    @ManagedProperty(value = "#{incomeExpenditureBean}")
    private IncomeExpenditureBean incomeExpenditureBean;
    @ManagedProperty(value = "#{attachmentBean}")
    private AttachmentBean attachmentBean;
    @ManagedProperty(value = "#{screeningDeclarationBean}")
    private ScreeningDeclarationBean screeningDeclarationBean;
    @ManagedProperty(value = "#{certificateBean}")
    private CertificateBean certificateBean;

    private Slice slice;
    private Comment comment;
    private int activeTab = 0;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        vettingStatuses.add(VettingStatus.ADDITIONAL_DOCUMENT);
        vettingStatuses.add(VettingStatus.ANALYST_POOL);
        vettingStatuses.add(VettingStatus.APPROVED);
        vettingStatuses.add(VettingStatus.APPROVER_POOL);
        vettingStatuses.add(VettingStatus.CLEARANCE_DENIED);
        vettingStatuses.add(VettingStatus.CLEARANCE_ISSUED);
        vettingStatuses.add(VettingStatus.ESCALATED);
        vettingStatuses.add(VettingStatus.FINAL_APPROVER_POOL);
        vettingStatuses.add(VettingStatus.FORMS_ISSUED);
        vettingStatuses.add(VettingStatus.FORMS_SUBMITTED);
        vettingStatuses.add(VettingStatus.OFFICER_POOL);
        vettingStatuses.add(VettingStatus.QA_POOL);
        vettingStatuses.add(VettingStatus.REJECTED);
        vettingStatuses.add(VettingStatus.REWORK);
        vettingStatuses.add(VettingStatus.SARS_PROCESSING);
        vettingStatuses.add(VettingStatus.SENT_TO_QA);
        vettingStatuses.add(VettingStatus.SSA_PROCESSING);
        vettingStatuses.add(VettingStatus.VETTING_DENIED);
        vettingStatuses.add(VettingStatus.CERTIFICATE);
//        Pageable pageable = PageRequest.of(0, 10);
//        slice = vettingService.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, getActiveUser().getSid(), pageable);
//        addCollections(slice.toList());
        addCollections(vettingService.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, getActiveUser().getSid()));
    }

    public void loadVettingForm(Vetting vettingRecord) {
        screeningDeclarationBean.init(vettingRecord);
        screeningBean.init(vettingRecord);
        incomeExpenditureBean.init(vettingRecord);
        attachmentBean.init(vettingRecord);
        certificateBean.init(vettingRecord);
        comments = vettingRecord.getComments();
        addEntity(vettingRecord);
        reset().setReview(true);
    }

    public void save(Vetting vetting) {
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

        if (vetting.getIncomeExpenditure().getExpenditures().iterator().next().getExpenditureType() != null) {
            incomeExpenditureBean.calculate();
        }
        if (StringUtils.isNotEmpty(vetting.getEmployee().getContactDetail().getCellPhoneNumber())) {
            employeeService.update(vetting.getEmployee());
        }
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        if (vetting.getId() != null && vetting.getVettingProcessingOfficer().getEmployeeRole().getDescription().equalsIgnoreCase("Quality Assurarer")) {
            vetting.setVettingStatus(VettingStatus.QA_POOL);
        } else if (vetting.getId() != null && vetting.getVettingProcessingOfficer().getEmployeeRole().getDescription().equalsIgnoreCase("Officer")) {
            vetting.setVettingStatus(VettingStatus.OFFICER_POOL);
        } else if (vetting.getId() != null && vetting.getVettingProcessingOfficer().getEmployeeRole().getDescription().equalsIgnoreCase("Analyst")) {
            vetting.setVettingStatus(VettingStatus.ANALYST_POOL);
        }
        addEntity(vettingService.save(vetting));
        addInformationMessage("Vetting was successfully saved.");
        reset().setList(true);
    }

    public void submit(Vetting vetting) {
        vetting.setScreeningDeclaration(screeningDeclarationBean.getDeclaration());
        vetting.setScreening(screeningBean.getScreening());
        vetting.setIncomeExpenditure(incomeExpenditureBean.getIncomeExpenditure());
        vetting.addAttachments(attachmentBean.getAttachments());

        if (!vetting.getScreeningDeclaration().isCandidateSignature()) {
            addWarningMessage("Please confirm giving Authority.");
            return;
        }

        if (vetting.getAttachments().isEmpty()) {
            addWarningMessage("Please attach supporting documents");
            return;
        }
        List<AttachmentType> validateMendatoryAttachments = new ArrayList<>();
        vetting.getAttachments().stream().map(supportingDocument -> {
            if (supportingDocument.getAttachmentType().equals(AttachmentType.ID)) {
                validateMendatoryAttachments.add(AttachmentType.ID);
            }

            return supportingDocument;
        }).map(supportingDocument -> {
            if (supportingDocument.getAttachmentType().equals(AttachmentType.Z204_FORM)) {
                validateMendatoryAttachments.add(AttachmentType.Z204_FORM);
            }
            return supportingDocument;
        }).map(supportingDocument -> {
            if (supportingDocument.getAttachmentType().equals(AttachmentType.RESIDANCE_STATUS)) {
                validateMendatoryAttachments.add(AttachmentType.RESIDANCE_STATUS);
            }
            return supportingDocument;
        }).map(supportingDocument -> {
            if (supportingDocument.getAttachmentType().equals(AttachmentType.REFUGEE_STATUS)) {
                validateMendatoryAttachments.add(AttachmentType.REFUGEE_STATUS);
            }
            return supportingDocument;
        }).map(supportingDocument -> {
            if (supportingDocument.getAttachmentType().equals(AttachmentType.WORK_PERMIT)) {
                validateMendatoryAttachments.add(AttachmentType.WORK_PERMIT);
            }
            return supportingDocument;

        }).filter(supportingDocument -> (supportingDocument.getAttachmentType().equals(AttachmentType.CONSENT))).forEachOrdered(_item -> {
            validateMendatoryAttachments.add(AttachmentType.CONSENT);
        });

        if (vetting.getVettingType().equals(VettingType.EMPLOYEE)) {
            if (!(validateMendatoryAttachments.contains(AttachmentType.ID) && validateMendatoryAttachments.contains(AttachmentType.Z204_FORM))) {
                addWarningMessage("ID copy and Z204 Form is required for successfull submission");
                return;
            }
            if (vetting.getScreeningDeclaration().getScreeningDeclarationResponse().getRsaCitizen().equals(ScreeningResponseOption.NO)) {
                if (!(validateMendatoryAttachments.contains(AttachmentType.REFUGEE_STATUS)
                        || validateMendatoryAttachments.contains(AttachmentType.RESIDANCE_STATUS)
                        || validateMendatoryAttachments.contains(AttachmentType.WORK_PERMIT))) {
                    addWarningMessage("Please upload atleast one of the following documents:", AttachmentType.REFUGEE_STATUS.toString(), " or ", AttachmentType.WORK_PERMIT.toString(), " or", AttachmentType.RESIDANCE_STATUS.toString());
                    return;
                }
            }

        }

        if (vetting.getVettingType().equals(VettingType.PROVIDER)) {
            if (!(validateMendatoryAttachments.contains(AttachmentType.ID)
                    && validateMendatoryAttachments.contains(AttachmentType.CONSENT) && validateMendatoryAttachments.contains(AttachmentType.Z204_FORM))) {
                addWarningMessage("ID copy,Z204 Form and Consent Form are required for successfull submission");
                return;
            }
            if (vetting.getScreeningDeclaration().getScreeningDeclarationResponse().getRsaCitizen().equals(ScreeningResponseOption.NO)) {
                if (!(validateMendatoryAttachments.contains(AttachmentType.REFUGEE_STATUS)
                        || validateMendatoryAttachments.contains(AttachmentType.RESIDANCE_STATUS)
                        || validateMendatoryAttachments.contains(AttachmentType.WORK_PERMIT))) {
                    addWarningMessage("Please upload atleast one of the following documents:", AttachmentType.REFUGEE_STATUS.toString(), " or ", AttachmentType.WORK_PERMIT.toString(), " or", AttachmentType.RESIDANCE_STATUS.toString());
                    return;
                }
            }

        }

        if (vetting.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT)) {
            if (vetting.getAttachments().isEmpty()) {
                addWarningMessage("Please attach supporting documents");
                return;
            }
//            List<AttachmentType> validateRequiredAttachments = new ArrayList<>();
            vetting.getAttachments().stream().map(supportingDocument -> {
                if (supportingDocument.getAttachmentType().equals(AttachmentType.ID)) {
                    validateMendatoryAttachments.add(AttachmentType.ID);
                }
                return supportingDocument;
            }).map(supportingDocument -> {
                if (supportingDocument.getAttachmentType().equals(AttachmentType.STATEMENT)) {
                    validateMendatoryAttachments.add(AttachmentType.STATEMENT);
                }
                return supportingDocument;

            }).map(supportingDocument -> {
                if (supportingDocument.getAttachmentType().equals(AttachmentType.Z204_FORM)) {
                    validateMendatoryAttachments.add(AttachmentType.Z204_FORM);
                }
                return supportingDocument;

            }).map(supportingDocument -> {
                if (supportingDocument.getAttachmentType().equals(AttachmentType.SALARY_ADVICE)) {
                    validateMendatoryAttachments.add(AttachmentType.SALARY_ADVICE);
                }
                return supportingDocument;
            }).filter(supportingDocument -> (supportingDocument.getAttachmentType().equals(AttachmentType.CONSENT))).forEachOrdered(_item -> {
                validateMendatoryAttachments.add(AttachmentType.CONSENT);
            });

            if (vetting.getVettingType().equals(VettingType.EMPLOYEE)) {
                if (!(validateMendatoryAttachments.contains(AttachmentType.STATEMENT)
                        && validateMendatoryAttachments.contains(AttachmentType.SALARY_ADVICE) && validateMendatoryAttachments.contains(AttachmentType.Z204_FORM))) {
                    addWarningMessage("Bank Statement and Z204 Form and Salary Advice are required for successfull submission");
                    return;
                }
            }

            if (vetting.getVettingType().equals(VettingType.PROVIDER)) {
                if (!(validateMendatoryAttachments.contains(AttachmentType.STATEMENT)
                        && validateMendatoryAttachments.contains(AttachmentType.SALARY_ADVICE)
                        && validateMendatoryAttachments.contains(AttachmentType.CONSENT) && validateMendatoryAttachments.contains(AttachmentType.Z204_FORM))) {
                    addWarningMessage("Bank Statement and Z204 Form and Salary Advice and Consent Form are required for successfull submission");
                    return;
                }
            }
        }
        for (Attachment supportingDocument : vetting.getAttachments()) {
            if (supportingDocument.getId() == null) {
                JsonDocumentDto jsonDocumentDto = attachmentBean.uploadDocumentsToServer(supportingDocument);
                JSONObject myResponse;
                myResponse = new JSONObject(attachmentService.uploadDocument(jsonDocumentDto));
                String objectId = myResponse.getString("objectId");
                supportingDocument.setCode(objectId);
            }
            // vetting.getAttachments().clear();
        }

        if (vetting.getIncomeExpenditure().getExpenditures().iterator().next().getExpenditureType() != null) {
            incomeExpenditureBean.calculate();
        }

        if (StringUtils.isNotEmpty(vetting.getEmployee().getContactDetail().getCellPhoneNumber())) {
            employeeService.update(vetting.getEmployee());
        }
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());

        if (vetting.getAnalystSid() != null && !vetting.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT)) {
            vetting.setVettingStatus(VettingStatus.SARS_PROCESSING);
        } else if (vetting.getOfficerSid() != null && vetting.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT)) {
            vetting.setVettingStatus(VettingStatus.SSA_PROCESSING);
        } else {
            vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        }

        if (vetting.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT) || vetting.getVettingStatus().equals(VettingStatus.REWORK)) {
            if (comment.getDescription().isEmpty()) {
                addWarningMessage("Please add comment.");
                return;
            }
            if (comment.getDescription().isEmpty()) {
                getEntity().removeComment(comment);
            }
        }

        vetting.setZ204SubmittedDate(new Date());
        addEntity(vettingService.update(vetting));
        synchronize(vetting);

        if (vetting.getVettingProcessingOfficer() != null && !vetting.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT)) {
            Employee employee = employeeService.findBySid(vetting.getVettingProcessingOfficer().getSid());
            try {
                emailNotificationSenderService.sendNotification(NotificationType.DOCUMENTS, vetting.getCreatedDate(), employee, getActiveUser().getEmployee());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VettingFormCompletionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VettingFormCompletionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            addInformationMessage("Vetting was successfully sent to the vetting officer.");
        } else if (vetting.getVettingProcessingOfficer() != null && vetting.getVettingStatus().equals(VettingStatus.ADDITIONAL_DOCUMENT)) {
            Employee employee = employeeService.findBySid(vetting.getVettingProcessingOfficer().getSid());
            try {
                emailNotificationSenderService.sendNotification(NotificationType.DOCUMENTS, vetting.getCreatedDate(), employee, getActiveUser().getEmployee());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VettingFormCompletionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VettingFormCompletionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            addInformationMessage("Vetting was successfully sent to the vetting officer.");
        } else {
            Employee employee = employeeService.findBySid(vetting.getCreatedBy());
            try {
                emailNotificationSenderService.sendNotification(NotificationType.SUBMITTED, vetting.getCreatedDate(), employee, getActiveUser().getEmployee());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VettingFormCompletionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VettingFormCompletionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            addInformationMessage("Vetting was successfully sent to the Initiator.");
        }

        reset().setList(true);
        
    }

    public void cancel() {
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

    /*
     public boolean isNextPage() {
     return slice.hasNext();
     }

     public boolean isPreviousPage() {
     return slice.hasPrevious();
     }

     public void nextVettingRecords() {
     if (slice.hasNext()) {
     slice = vettingService.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, getActiveUser().getSid(), slice.nextPageable());
     addCollections(slice.toList());
     }
     }

     public void previousVettingRecords() {
     if (slice.hasPrevious()) {
     slice = vettingService.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, getActiveUser().getSid(), slice.previousPageable());
     addCollections(slice.toList());
     }
     }

     public void getNextOrLastVettingRecord() {
     slice = vettingService.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, getActiveUser().getSid(), slice.nextOrLastPageable());
     addCollections(slice.toList());
     }

     public void getPreviousOrFirstVettingRecord() {
     slice = vettingService.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, getActiveUser().getSid(), slice.previousOrFirstPageable());
     addCollections(slice.toList());
     }

     public Integer getPageNumber() {
     return slice.getNumber() + 1;
     }
     */
    public List<Vetting> getVettingRecords() {
        return this.getCollections();
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

    public CertificateBean getCertificateBean() {
        return certificateBean;
    }

    public void setCertificateBean(CertificateBean certificateBean) {
        this.certificateBean = certificateBean;
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    public void saveContinue() {
        if (activeTab < 3) {
            activeTab++;
        }
        addEntity(vettingService.update(getEntity()));
        loadVettingForm(getEntity());
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
