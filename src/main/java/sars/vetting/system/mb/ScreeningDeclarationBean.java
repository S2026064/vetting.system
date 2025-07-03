package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.CountryUtil;
import sars.vetting.system.common.ScreeningResponseOption;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.BoardMembership;
import sars.vetting.system.domain.Citizenship;
import sars.vetting.system.domain.ConvictedCriminalCharge;
import sars.vetting.system.domain.CriminalCharge;
import sars.vetting.system.domain.CurrentEmpDisciplinaryHearing;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.FamilyBoardMembership;
import sars.vetting.system.domain.IllegalDrug;
import sars.vetting.system.domain.MisconductAllegation;
import sars.vetting.system.domain.PreviousDisciplinaryHearing;
import sars.vetting.system.domain.ScreeningDeclaration;
import sars.vetting.system.domain.StateOrganBusiness;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.BoardMembershipServiceLocal;
import sars.vetting.system.service.CitizenshipServiceLocal;
import sars.vetting.system.service.ConvictedCriminalChargeServiceLocal;
import sars.vetting.system.service.CriminalChargeServiceLocal;
import sars.vetting.system.service.CurrentEmpDisciplinaryHearingServiceLocal;
import sars.vetting.system.service.FamilyBoardMembershipServiceLocal;
import sars.vetting.system.service.IllegalDrugServiceLocal;
import sars.vetting.system.service.MisconductAllegationServiceLocal;
import sars.vetting.system.service.PreviousDisciplinaryHearingServiceLocal;
import sars.vetting.system.service.ScreeningDeclarationServiceLocal;
import sars.vetting.system.service.StateOrganBusinessServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class ScreeningDeclarationBean extends BaseBean<ScreeningDeclaration> {

    @Autowired
    private ScreeningDeclarationServiceLocal screeningDeclarationService;
    @Autowired
    private CitizenshipServiceLocal citizenshipService;
    @Autowired
    private IllegalDrugServiceLocal illegalDrugService;
    @Autowired
    private CriminalChargeServiceLocal criminalChargeService;
    @Autowired
    private ConvictedCriminalChargeServiceLocal convictedCriminalChargeService;
    @Autowired
    private CurrentEmpDisciplinaryHearingServiceLocal currentEmpDisciplinaryHearingService;
    @Autowired
    private PreviousDisciplinaryHearingServiceLocal previousDisciplinaryHearingService;
    @Autowired
    private MisconductAllegationServiceLocal misconductAllegationService;
    @Autowired
    private StateOrganBusinessServiceLocal stateOrganBusinessService;
    @Autowired
    private BoardMembershipServiceLocal boardMembershipService;
    @Autowired
    private FamilyBoardMembershipServiceLocal familyBoardMembershipService;

    private List<String> listCountries;
    private List<ScreeningResponseOption> screeningResponseOptions;

    private Employee employee;
    private VettingStatus vettingStatus;
    private boolean useDropdown = false;

    public void init(Vetting vettingRecord) {

        listCountries = new ArrayList<>();
        listCountries = CountryUtil.getDisplayCountryNames();
        screeningResponseOptions = new ArrayList<>();
        screeningResponseOptions.addAll(Arrays.asList(ScreeningResponseOption.values()));
        setEmployee(vettingRecord.getEmployee());
        setVettingStatus(vettingRecord.getVettingStatus());
        addEntity(vettingRecord.getScreeningDeclaration());
    }

    //Section to add/remove citizenship on the form 
    public void addCitizenship() {
        if (getEntity().getScreeningDeclarationResponse().getDualCitizenship().equals(ScreeningResponseOption.YES)) {
            Citizenship citizenship = new Citizenship();
            if (citizenship.getId() == null) {
                citizenship.setCreatedBy(getActiveUser().getSid());
                citizenship.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addCitizenship(citizenship);
            } else {
                citizenship.setUpdatedBy(getActiveUser().getSid());
                citizenship.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getCitizenships().clear();
        }
    }

    public void removeCitizenship(Citizenship citizenship) {
        if (citizenship.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeCitizenship(citizenship);
            addEntity(screeningDeclarationService.update(getEntity()));
            citizenshipService.deleteById(citizenship.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeCitizenship(citizenship);
        }
        addInformationMessage("Citizenship successfully removed");
    }

    //Section to add/remove illegal drugs on the form    
    public void addIllegalDrug() {
        if (getEntity().getScreeningDeclarationResponse().getIllegalDrug().equals(ScreeningResponseOption.YES)) {
            IllegalDrug illegalDrug = new IllegalDrug();
            if (illegalDrug.getId() == null) {
                illegalDrug.setCreatedBy(getActiveUser().getSid());
                illegalDrug.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addIllegalDrug(illegalDrug);
            } else {
                illegalDrug.setUpdatedBy(getActiveUser().getSid());
                illegalDrug.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getIllegalDrugs().clear();
        }
    }

    public void removeIllegalDrug(IllegalDrug illegalDrug) {
        if (illegalDrug.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeIllegalDrug(illegalDrug);
            addEntity(screeningDeclarationService.update(getEntity()));
            illegalDrugService.deleteById(illegalDrug.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeIllegalDrug(illegalDrug);
        }
        addInformationMessage("Illegal Drug successfully removed");
    }

    //Section to add/remove laid charge on the form
    public void addLiadCharge() {
        if (getEntity().getScreeningDeclarationResponse().getCriminalCharges().equals(ScreeningResponseOption.YES)) {
            CriminalCharge criminalCharge = new CriminalCharge();
            if (criminalCharge.getId() == null) {
                criminalCharge.setCreatedBy(getActiveUser().getSid());
                criminalCharge.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addLaidCriminalCharge(criminalCharge);
            } else {
                criminalCharge.setUpdatedBy(getActiveUser().getSid());
                criminalCharge.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getLaidCriminalCharges().clear();
        }
    }

    public void removeLiadCahrge(CriminalCharge criminalCharge) {
        if (criminalCharge.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeLaidCriminalCharge(criminalCharge);
            addEntity(screeningDeclarationService.update(getEntity()));
            criminalChargeService.deleteById(criminalCharge.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeLaidCriminalCharge(criminalCharge);
        }
        addInformationMessage("Criminal Charge successfully removed");
    }

    //Section to add/remove laid charge on the form
    public void addCriminalOffence() {
        if (getEntity().getScreeningDeclarationResponse().getCriminalConviction().equals(ScreeningResponseOption.YES)) {
            ConvictedCriminalCharge convictedCriminalCharge = new ConvictedCriminalCharge();
            if (convictedCriminalCharge.getId() == null) {
                convictedCriminalCharge.setCreatedBy(getActiveUser().getSid());
                convictedCriminalCharge.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addConvictedCriminalCharge(convictedCriminalCharge);
            } else {
                convictedCriminalCharge.setUpdatedBy(getActiveUser().getSid());
                convictedCriminalCharge.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getConvictedCriminalCharges().clear();
        }
    }

    public void removeCriminalOffence(ConvictedCriminalCharge convictedCriminalCharge) {
        if (convictedCriminalCharge.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeConvictedCriminalCharge(convictedCriminalCharge);
            addEntity(screeningDeclarationService.update(getEntity()));
            convictedCriminalChargeService.deleteById(convictedCriminalCharge.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeConvictedCriminalCharge(convictedCriminalCharge);
        }
        addInformationMessage("Convicted Criminal Offence successfully removed");
    }

    //Section to add/remove disciplinary Hearing At Current on the form
    public void addDisciplinaryHearingAtCurrent() {
        if (getEntity().getScreeningDeclarationResponse().getDisciplinaryHearingAtCurrent().equals(ScreeningResponseOption.YES)) {
            CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing = new CurrentEmpDisciplinaryHearing();
            if (currentEmpDisciplinaryHearing.getId() == null) {
                currentEmpDisciplinaryHearing.setCreatedBy(getActiveUser().getSid());
                currentEmpDisciplinaryHearing.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addCurrentEmpDisciplinaryHearing(currentEmpDisciplinaryHearing);
            } else {
                currentEmpDisciplinaryHearing.setUpdatedBy(getActiveUser().getSid());
                currentEmpDisciplinaryHearing.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getCurrentEmpDisciplinaryHearings().clear();
        }
    }

    public void removeDisciplinaryHearingAtCurrent(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing) {
        if (currentEmpDisciplinaryHearing.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeCurrentEmpDisciplinaryHearing(currentEmpDisciplinaryHearing);
            addEntity(screeningDeclarationService.update(getEntity()));
            currentEmpDisciplinaryHearingService.deleteById(currentEmpDisciplinaryHearing.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeCurrentEmpDisciplinaryHearing(currentEmpDisciplinaryHearing);
        }
        addInformationMessage("Current employment hearing successfully removed");
    }

    //Section to add/remove former disciplinary Hearing on the form
    public void addFormerDisciplinaryHearing() {
        if (getEntity().getScreeningDeclarationResponse().getDisciplinaryHearingAtFormer().equals(ScreeningResponseOption.YES)) {
            PreviousDisciplinaryHearing previousDisciplinaryHearing = new PreviousDisciplinaryHearing();
            if (previousDisciplinaryHearing.getId() == null) {
                previousDisciplinaryHearing.setCreatedBy(getActiveUser().getSid());
                previousDisciplinaryHearing.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addFormerEmpDisciplinaryHearing(previousDisciplinaryHearing);
            } else {
                previousDisciplinaryHearing.setUpdatedBy(getActiveUser().getSid());
                previousDisciplinaryHearing.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getFormerEmpDisciplinaryHearings().clear();
        }
    }

    public void removeFormerDisciplinaryHearing(PreviousDisciplinaryHearing previousDisciplinaryHearing) {
        if (previousDisciplinaryHearing.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeFormerEmpDisciplinaryHearing(previousDisciplinaryHearing);
            addEntity(screeningDeclarationService.update(getEntity()));
            previousDisciplinaryHearingService.deleteById(previousDisciplinaryHearing.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeFormerEmpDisciplinaryHearing(previousDisciplinaryHearing);
        }
        addInformationMessage("Previous employment hearing successfully removed");
    }

    //Section to add/remove misconduct on the form
    public void addMisconductAllegation() {
        if (getEntity().getScreeningDeclarationResponse().getInvestigatedForMisconduct().equals(ScreeningResponseOption.YES)) {
            MisconductAllegation misconductAllegation = new MisconductAllegation();
            if (misconductAllegation.getId() == null) {
                misconductAllegation.setCreatedBy(getActiveUser().getSid());
                misconductAllegation.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addMisconductAllegation(misconductAllegation);
            } else {
                misconductAllegation.setUpdatedBy(getActiveUser().getSid());
                misconductAllegation.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getMisconductAllegations().clear();
        }
    }

    public void removeMisconductAllegation(MisconductAllegation misconductAllegation) {
        if (misconductAllegation.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeMisconductAllegation(misconductAllegation);
            addEntity(screeningDeclarationService.update(getEntity()));
            misconductAllegationService.deleteById(misconductAllegation.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeMisconductAllegation(misconductAllegation);
        }
        addInformationMessage("Allegation of misconduct successfully removed");
    }

    //Section to add/remove business organ state on the form
    public void addStateOrganBusiness() {
        if (getEntity().getScreeningDeclarationResponse().getBusinnessWithStateOrgan().equals(ScreeningResponseOption.YES)) {
            StateOrganBusiness stateOrganBusiness = new StateOrganBusiness();
            if (stateOrganBusiness.getId() == null) {
                stateOrganBusiness.setCreatedBy(getActiveUser().getSid());
                stateOrganBusiness.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addStateOrganBusiness(stateOrganBusiness);
            } else {
                stateOrganBusiness.setUpdatedBy(getActiveUser().getSid());
                stateOrganBusiness.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getStateOrganBusinesses().clear();
        }
    }

    public void removeStateOrganBusiness(StateOrganBusiness stateOrganBusiness) {
        if (stateOrganBusiness.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeStateOrganBusiness(stateOrganBusiness);
            addEntity(screeningDeclarationService.update(getEntity()));
            stateOrganBusinessService.deleteById(stateOrganBusiness.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeStateOrganBusiness(stateOrganBusiness);
        }
        addInformationMessage("State Organ Business successfully removed");
    }

    //Section to add/remove business organ state on the form    
    public void addBoardMembership() {
        if (getEntity().getScreeningDeclarationResponse().getBoardMembership().equals(ScreeningResponseOption.YES)) {
            BoardMembership boardMembership = new BoardMembership();
            if (boardMembership.getId() == null) {
                boardMembership.setCreatedBy(getActiveUser().getSid());
                boardMembership.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addBoardMembership(boardMembership);
            } else {
                boardMembership.setUpdatedBy(getActiveUser().getSid());
                boardMembership.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getBoardMemberships().clear();
        }
    }

    public void removeBoardMembership(BoardMembership boardMembership) {
        if (boardMembership.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeBoardMembership(boardMembership);
            addEntity(screeningDeclarationService.update(getEntity()));
            boardMembershipService.deleteById(boardMembership.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeBoardMembership(boardMembership);
        }
        addInformationMessage("Board Membership successfully removed");
    }

    //Section to add/remove family Board Memberships on the form
    public void addFamilyBoardMembership() {
        if (getEntity().getScreeningDeclarationResponse().getFamilyMembersBoardMembership().equals(ScreeningResponseOption.YES)) {
            FamilyBoardMembership familyBoardMembership = new FamilyBoardMembership();
            if (familyBoardMembership.getId() == null) {
                familyBoardMembership.setCreatedBy(getActiveUser().getSid());
                familyBoardMembership.setCreatedDate(new Date());
                getEntity().getScreeningDeclarationResponse().addFamilyBoardMembership(familyBoardMembership);
            } else {
                familyBoardMembership.setUpdatedBy(getActiveUser().getSid());
                familyBoardMembership.setUpdatedDate(new Date());
            }
        } else {
            getEntity().getScreeningDeclarationResponse().getFamilyBoardMemberships().clear();
        }
    }

    public void removeFamilyBoardMembership(FamilyBoardMembership familyBoardMembership) {
        if (familyBoardMembership.getId() != null) {
            getEntity().getScreeningDeclarationResponse().removeFamilyBoardMembership(familyBoardMembership);
            addEntity(screeningDeclarationService.update(getEntity()));
            familyBoardMembershipService.deleteById(familyBoardMembership.getId());
        } else {
            getEntity().getScreeningDeclarationResponse().removeFamilyBoardMembership(familyBoardMembership);
        }
        addInformationMessage("Family Board Membership successfully removed");
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

    public ScreeningDeclaration getDeclaration() {
        return getEntity();
    }

    public List<String> getListCountries() {
        return listCountries;
    }

    public void setListCountries(List<String> listCountries) {
        this.listCountries = listCountries;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isDisabled() {
        return !(vettingStatus.equals(VettingStatus.FORMS_ISSUED) || vettingStatus.equals(VettingStatus.REWORK));
    }

    public VettingStatus getVettingStatus() {
        return vettingStatus;
    }

    public void setVettingStatus(VettingStatus vettingStatus) {
        this.vettingStatus = vettingStatus;
    }

    public List<ScreeningResponseOption> getScreeningResponseOptions() {
        return screeningResponseOptions;
    }

    public void setScreeningResponseOptions(List<ScreeningResponseOption> screeningResponseOptions) {
        this.screeningResponseOptions = screeningResponseOptions;
    }

    public boolean isUseDropdown() {
        return useDropdown;
    }

    public void setUseDropdown(boolean useDropdown) {
        this.useDropdown = useDropdown;
    }

}
