package sars.vetting.system.mb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.EmployeeRoleType;
import sars.vetting.system.common.EmployeeStatus;
import sars.vetting.system.common.NotificationType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.common.VettingType;
import sars.vetting.system.domain.Comment;
import sars.vetting.system.domain.CreditRecord;
import sars.vetting.system.domain.CriminalRecord;
import sars.vetting.system.domain.DisciplinaryRecord;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.Expenditure;
import sars.vetting.system.domain.GradeClearance;
import sars.vetting.system.domain.Income;
import sars.vetting.system.domain.IncomeExpenditure;
import sars.vetting.system.domain.InternalInvestigationRecord;
import sars.vetting.system.domain.Note;
import sars.vetting.system.domain.Screening;
import sars.vetting.system.domain.ScreeningDeclaration;
import sars.vetting.system.domain.ScreeningDeclarationResponse;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.CommentServiceLocal;
import sars.vetting.system.service.DOPIServiceLocal;
import sars.vetting.system.service.EmailNotificationSenderServiceLocal;
import sars.vetting.system.service.EmployeeInformationServiceLocal;
import sars.vetting.system.service.EmployeeRoleServiceLocal;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class VettingInitiationBean extends BaseBean<Vetting> {

    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmailNotificationSenderServiceLocal emailNotificationSenderService;
    @Autowired
    private CommentServiceLocal commentService;
    @Autowired
    private EmployeeServiceLocal userService;
    @Autowired
    private EmployeeInformationServiceLocal employeeInformationService;
    @Autowired
    private DOPIServiceLocal dOPIService;
    @Autowired
    private EmployeeRoleServiceLocal employeeRoleService;

    private static final Logger LOG = Logger.getLogger(VettingInitiationBean.class.getName());

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

    private List<Employee> analysts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Note> notesAdded = new ArrayList<>();
    private List<VettingType> vettingTypes = new ArrayList<>();
    private final List<VettingStatus> completedVettingStatuses = new ArrayList<>();
    private final List<VettingStatus> activeVettingFileStatus = new ArrayList<>();
    private List<Vetting> completedVetting;
    private List<Vetting> activeVetting;

    private Slice vettingSlice;
    private Comment comment;
    private String searchParam;
    private Employee persistentEmployee;
    private Note note;
    private int activeTab = 0;
    private boolean useDropdown = false;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        vettingTypes.addAll(Arrays.asList(VettingType.values()));
//        Pageable pageable = PageRequest.of(0, 10);
//        vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.FORMS_SUBMITTED, pageable);
//        addCollections(vettingSlice.toList());
        addCollections(vettingService.findByVettingStatus(VettingStatus.FORMS_SUBMITTED));

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

    public void searchEmployee() {
        if (getEntity().getVettingType().equals(VettingType.EMPLOYEE)) {
            Employee persistentUserDb = employeeService.findBySid(searchParam);
            if (persistentUserDb != null) {
                if (searchParam.trim().equalsIgnoreCase(getActiveUser().getEmployee().getIdentityNumber())) {
                    addWarningMessage("Please note that you cannot initiate vetting for yourself");
                    return;
                }
                List<VettingStatus> commpleteStatuses = new ArrayList<>();
                commpleteStatuses.add(VettingStatus.VETTING_DENIED);
                commpleteStatuses.add(VettingStatus.APPROVED);
                if (vettingService.isVettingRecordExist(persistentUserDb.getSid(), commpleteStatuses)) {
                    addWarningMessage("This employee has an active vetting file");
                    return;
                } else {
                    setPersistentEmployee(persistentUserDb);
                }
            } else if (persistentUserDb == null) {
                if (searchParam.trim().equalsIgnoreCase(getActiveUser().getEmployee().getIdentityNumber())) {
                    addWarningMessage("Please note that you cannot initiate vetting for yourself");
                    return;
                }

                Optional<Employee> sapOptional = Optional.ofNullable(employeeInformationService.getEmployeeUserBySid(searchParam, getActiveUser().getSid()));
                if (sapOptional.isPresent()) {
                    Employee sapEmployee = sapOptional.get();
                    if (sapEmployee != null) {
                        sapEmployee.setEmployeeRole(employeeRoleService.findByDescription(EmployeeRoleType.SUBJECT.toString()));
                        sapEmployee.setEmployeeStatus(EmployeeStatus.ACTIVE);
                        if (StringUtils.isNotEmpty(sapEmployee.getIdentityNumber())) {
                            //pull information from DOPI and Public officer from IBR data using id number
                            sapEmployee.addPublicOfficerDetails(employeeInformationService.getPublicOfficer(sapEmployee.getIdentityNumber(), getActiveUser().getSid()));
                            sapEmployee.addQualifications(dOPIService.getQualificationByEmployeeIdnumber(sapEmployee.getIdentityNumber(), getActiveUser().getSid()));
                        }
                    } else {
                        addErrorMessage("System user with SID number", searchParam, "does not exist");
                        return;
                    }
                    //Search manager on vettingDB
                    if (StringUtils.isNotEmpty(sapEmployee.getManagerSID())) {
                        Employee manager = userService.findBySid(sapEmployee.getManagerSID());
                        if (manager == null) {
                            //If not found, Search Manager on SapDB
                            manager = employeeInformationService.getEmployeeUserBySid(sapEmployee.getManagerSID(), sapEmployee.getSid());
                            manager.setEmployeeRole(employeeRoleService.findByDescription(EmployeeRoleType.MANAGER.toString()));
                            manager.setEmployeeStatus(EmployeeStatus.ACTIVE);
                            if (StringUtils.isNotEmpty(sapEmployee.getIdentityNumber())) {
                                //pull information from DOPI and Public officer from IBR data using id number
                                manager.addPublicOfficerDetails(employeeInformationService.getPublicOfficer(manager.getIdentityNumber(), getActiveUser().getSid()));
                                manager.addQualifications(dOPIService.getQualificationByEmployeeIdnumber(manager.getIdentityNumber(), getActiveUser().getSid()));
                            }
                            userService.save(manager);
                        }
                    }
                    Employee persistentEmployeeSAP = userService.save(sapEmployee);
                    setPersistentEmployee(persistentEmployeeSAP);
                } else {
                    addWarningMessage("The Employee with the S-ID Number", searchParam, "does not exist");
                }
            }
        } else {
            Employee persistentUser = employeeService.findByIdentityNumberOrPassportNumber(searchParam);
            if (searchParam.trim().equalsIgnoreCase(getActiveUser().getEmployee().getIdentityNumber())) {
                addWarningMessage("Please note that you cannot initiate vetting for yourself");
                return;
            }
            if (persistentUser != null) {
                List<VettingStatus> commpleteStatuses = new ArrayList<>();
                commpleteStatuses.add(VettingStatus.VETTING_DENIED);
                commpleteStatuses.add(VettingStatus.APPROVED);
                if (vettingService.isVettingRecordExist(persistentUser.getSid(), commpleteStatuses)) {
                    addWarningMessage("This employee has an active vetting file");
                    return;
                } else {
                    setPersistentEmployee(persistentUser);
                }
            } else {
                addWarningMessage("The Employee with the ID Number", searchParam, "does not exist");
            }
        }
        reset().setInitiate(true);
    }

    public void viewVettingForm(Vetting vettingRecord) {
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
        reset().setReview(true);
    }

    //view vetting details
    public void loadInitiateVettingForm() {
        reset().setFirstPanel(true);
        Vetting vetting = new Vetting();
        vetting.setCreatedBy(getActiveUser().getSid());
        vetting.setCreatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.FORMS_ISSUED);

        IncomeExpenditure incomeExpenditure = new IncomeExpenditure();
        incomeExpenditure.setCreatedBy(getActiveUser().getSid());
        incomeExpenditure.setCreatedDate(new Date());

        Expenditure expenditure = new Expenditure();
        expenditure.setCreatedBy(getActiveUser().getSid());
        expenditure.setCreatedDate(new Date());
        incomeExpenditure.addExpenditure(expenditure);

        Income income = new Income();
        income.setCreatedBy(getActiveUser().getSid());
        income.setCreatedDate(new Date());
        incomeExpenditure.setIncome(income);
        vetting.setIncomeExpenditure(incomeExpenditure);

        ScreeningDeclaration screeningDeclaration = new ScreeningDeclaration();
        screeningDeclaration.setCreatedBy(getActiveUser().getSid());
        screeningDeclaration.setCreatedDate(new Date());
        screeningDeclaration.setScreeningDate(new Date());

        ScreeningDeclarationResponse screeningDeclarationResponse = new ScreeningDeclarationResponse();
        screeningDeclarationResponse.setCreatedBy(getActiveUser().getSid());
        screeningDeclarationResponse.setCreatedDate(new Date());

        screeningDeclaration.setScreeningDeclarationResponse(screeningDeclarationResponse);
        vetting.setScreeningDeclaration(screeningDeclaration);

        Screening screening = new Screening();
        screening.setCreatedBy(getActiveUser().getSid());
        screening.setCreatedDate(new Date());
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setCreatedBy(getActiveUser().getSid());
        creditRecord.setCreatedDate(new Date());
        screening.addCreditRecord(creditRecord);

        CriminalRecord criminalRecord = new CriminalRecord();
        criminalRecord.setCreatedBy(getActiveUser().getSid());
        criminalRecord.setCreatedDate(new Date());
        screening.addCriminalRecord(criminalRecord);

        DisciplinaryRecord disciplinaryRecord = new DisciplinaryRecord();
        disciplinaryRecord.setCreatedBy(getActiveUser().getSid());
        disciplinaryRecord.setCreatedDate(new Date());
        screening.addDisciplinaryRecord(disciplinaryRecord);
        InternalInvestigationRecord internalInvestigationRecord = new InternalInvestigationRecord();
        internalInvestigationRecord.setCreatedBy(getActiveUser().getSid());
        internalInvestigationRecord.setCreatedDate(new Date());
        screening.addInternalInvestigationRecord(internalInvestigationRecord);
        vetting.setScreening(screening);

        GradeClearance gradeClearance = new GradeClearance();
        gradeClearance.setCreatedBy(getActiveUser().getSid());
        gradeClearance.setCreatedDate(new Date());
        vetting.setGradeClearance(gradeClearance);

        addEntity(vetting);
        addCollection(vetting);
    }

    public void vettingTypeListner() {

        if (getEntity().getVettingType() == null) {
            addWarningMessage("Please select the vetting type first");
            return;
        }

        if (getEntity().getVettingType().equals(VettingType.PROVIDER)) {
            reset().setInitiate(true);
        } else {
            reset().setInitiate(true);
        }
    }

    public void allocateVetting(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        if (note.getDescription().isEmpty()) {
            getEntity().removeNote(note);
        }
        vetting.setVettingStatus(VettingStatus.ANALYST_POOL);
        vettingService.update(vetting);
        addInformationMessage("Vetting successfully allocated to analyst pool");
        synchronize(vetting);
        reset().setList(true);
    }

    public void save(Vetting vetting) {
        vetting.setUpdatedBy(getActiveUser().getSid());
        vetting.setUpdatedDate(new Date());
        vetting.setVettingStatus(VettingStatus.FORMS_SUBMITTED);
        vettingService.update(vetting);
        addInformationMessage("Vetting successfully saved");
        synchronize(vetting);
        reset().setList(true);
    }

    public void initiateVettingProcess(Employee employee) {
        getEntity().setEmployee(employee);
        getEntity().setSubjectSid(employee.getSid());
        getEntity().setTat(0);
        vettingService.save(getEntity());
        addInformationMessage("Vetting initiated successfuly");

        try {
            emailNotificationSenderService.sendNotification(NotificationType.EMAIL_TO_SUBJECT, getEntity().getCreatedDate(), employee, getActiveUser().getEmployee());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VettingInitiationBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingInitiationBean.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            Logger.getLogger(VettingInitiationBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VettingInitiationBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean isNextPage() {
        return vettingSlice.hasNext();
    }

    public boolean isPreviousPage() {
        return vettingSlice.hasPrevious();
    }

    public void nextVettingRecords() {
        if (vettingSlice.hasNext()) {
            vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.FORMS_SUBMITTED, vettingSlice.nextPageable());
            addCollections(vettingSlice.toList());
        }
    }

    public void previousVettingRecords() {
        if (vettingSlice.hasPrevious()) {
            vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.FORMS_SUBMITTED, vettingSlice.previousPageable());
            addCollections(vettingSlice.toList());
        }
    }

    public void getNextOrLastVettingRecord() {
        vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.FORMS_SUBMITTED, vettingSlice.nextOrLastPageable());
        addCollections(vettingSlice.toList());
    }

    public void getPreviousOrFirstVettingRecord() {
        vettingSlice = vettingService.findVettingRecordsByStatus(VettingStatus.FORMS_SUBMITTED, vettingSlice.previousOrFirstPageable());
        addCollections(vettingSlice.toList());
    }

    public Integer getPageNumber() {
        return vettingSlice.getNumber() + 1;
    }

    public void cancel(Vetting vetting) {
        if (vetting.getId() == null) {
            if (getCollections().contains(vetting)) {
                getCollections().remove(vetting);
            }
        }
        reset().setList(true);
    }

    public void addMessage(AjaxBehaviorEvent event) {
        UIComponent component = event.getComponent();
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            Boolean value = (Boolean) inputComponent.getValue();
            String summary = value ? "Activated" : "De-activated";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        }
    }

    public void cancel() {
        reset().setList(true);
    }

    public List<Vetting> getVettingRecords() {
        return this.getCollections();
    }

    public List<Employee> getAnalysts() {
        return analysts;
    }

    public void setAnalysts(List<Employee> analysts) {
        this.analysts = analysts;
    }

    public Employee getPersistentEmployee() {
        return persistentEmployee;
    }

    public void setPersistentEmployee(Employee persistentEmployee) {
        this.persistentEmployee = persistentEmployee;
    }

    public Slice getVettingSlice() {
        return vettingSlice;
    }

    public void setVettingSlice(Slice vettingSlice) {
        this.vettingSlice = vettingSlice;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
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

    public List<VettingType> getVettingTypes() {
        return vettingTypes;
    }

    public void setVettingTypes(List<VettingType> vettingTypes) {
        this.vettingTypes = vettingTypes;
    }

    public GradeClearanceBean getGradeClearanceBean() {
        return gradeClearanceBean;
    }

    public void setGradeClearanceBean(GradeClearanceBean gradeClearanceBean) {
        this.gradeClearanceBean = gradeClearanceBean;
    }

    public Note getNote() {
        return note;
    }

    public List<Note> getNotesAdded() {
        return notesAdded;
    }

    public void setNotesAdded(List<Note> notesAdded) {
        this.notesAdded = notesAdded;
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

    public boolean isUseDropdown() {
        return useDropdown;
    }

    public void setUseDropdown(boolean useDropdown) {
        this.useDropdown = useDropdown;
    }

}
