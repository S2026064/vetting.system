package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.AppliableSecurityLevel;
import sars.vetting.system.common.DamageLevel;
import sars.vetting.system.common.JobRoleType;
import sars.vetting.system.common.RiskLevel;
import sars.vetting.system.common.StepOutcome;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdjustmentResponsibility;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.GradeClearance;
import sars.vetting.system.domain.JobRole;
import sars.vetting.system.domain.Responsibility;
import sars.vetting.system.domain.SubArea;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.AdjustmentCategoryServiceLocal;
import sars.vetting.system.service.AdjustmentResponsibilityServiceLocal;
import sars.vetting.system.service.JobRoleServiceLocal;
import sars.vetting.system.service.ResponsibilityServiceLocal;
import sars.vetting.system.service.SubAreaServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class GradeClearanceBean extends BaseBean<GradeClearance> {

    @Autowired
    private JobRoleServiceLocal jobRoleService;
    @Autowired
    private ResponsibilityServiceLocal responsibilityService;
    @Autowired
    private SubAreaServiceLocal subAreaService;
    @Autowired
    private AdjustmentCategoryServiceLocal adjustmentCategoryService;
    @Autowired
    private AdjustmentResponsibilityServiceLocal adjustmentResponsibilityService;

    private Employee employee;
    private VettingStatus vettingStatus;

//    for step1
    private JobRole selectedJobRoleNational;
    private Responsibility selectedresponsibilityOne;
    private Responsibility selectedresponsibilityTwo;
    private Responsibility selectedresponsibilityThree;
    private List<JobRole> nationalJobRoles;
    private List<Responsibility> nationalalResponsibilities;
    private DamageLevel nationalDamageLevel;
    private StepOutcome nationalStep;

    //   for  step2
    private JobRole selectedJobRoleSustainable;
    private Responsibility selectedresponsibilityA;
    private Responsibility selectedresponsibilityB;
    private Responsibility selectedresponsibilityC;
    private List<JobRole> sustainabilityJobRoles;
    private List<Responsibility> sustainableResponsibilities;
    private String sustainableStep;
    private Integer outcomeValue = 0;
    private Integer outcomeValueA = 0;
    private Integer outcomeValueB = 0;
    private Integer outcomeValueC = 0;

    //for step 3
    private AdjustmentCategory selectedAdjustmentCategory;
    private AdjustmentResponsibility selectedAdjustmentResponsibility;
    private List<AdjustmentCategory> adjustmentCategory;
    private List<AdjustmentResponsibility> adjustmentResponsibilities;
    private String adjustmentStep;
    private Integer adjustmentValue = 0;

    private String resultBased;
    private String officer;
    private List<SubArea> subAreas;

    private boolean stepResponsibility = Boolean.FALSE;

    public void init(Vetting vettingRecord) {
        setEmployee(vettingRecord.getEmployee());
        setVettingStatus(vettingRecord.getVettingStatus());
        setOfficer(vettingRecord.getVettingOfficer());
        subAreas = new ArrayList<>();
        subAreas.addAll(subAreaService.listAll());
        nationalJobRoles = new ArrayList<>();
        nationalJobRoles.addAll(jobRoleService.findByJobRoleType(JobRoleType.NATIONAL));
        sustainabilityJobRoles = new ArrayList<>();
        sustainabilityJobRoles.addAll(jobRoleService.findByJobRoleType(JobRoleType.SUSTAINABILITY));
        adjustmentCategory = new ArrayList<>();
        adjustmentCategory.addAll(adjustmentCategoryService.listAll());
        addEntity(vettingRecord.getGradeClearance());

        //this load incase you edit or vetting sentback for rework
        if (vettingRecord.getClearanceLevel() != null) {
            if (getEntity().getAppliableSecurityLevel() != null && !getEntity().getNationalJobRole().isEmpty()) {
                step1Listener();
            }
            if (getEntity().getAppliableSecurityLevel() != null && !getEntity().getDamageLevel().equals(DamageLevel.EXCEPTION.toString()) && !getEntity().getSustainableJobRole().isEmpty()) {
                step2Listner();
            }
            if (getEntity().getAppliableSecurityLevel() != null && getEntity().getAdjustmentCategory() != null) {
                step3Listner();
            }
        }
    }

    public void step1Listener() {
        if (!getEntity().getNationalJobRole().isEmpty()) {
            nationalalResponsibilities = new ArrayList<>();
            nationalalResponsibilities.addAll(responsibilityService.findByJobRoleDescriptionAndJobRoleType(getEntity().getNationalJobRole(), JobRoleType.NATIONAL));
        }
    }

    public void step2Listner() {
        if (!getEntity().getSustainableJobRole().isEmpty()) {
            sustainableResponsibilities = new ArrayList<>();
            sustainableResponsibilities.addAll(responsibilityService.findByJobRoleDescriptionAndJobRoleType(getEntity().getSustainableJobRole(), JobRoleType.SUSTAINABILITY));
        }
    }

    public void step3Listner() {
        if (getEntity().getAdjustmentCategory() != null) {
            reset().setUpdate(true);
            adjustmentResponsibilities = new ArrayList<>();
            adjustmentResponsibilities.addAll(adjustmentResponsibilityService.findByAdjustmentCategory(getEntity().getAdjustmentCategory()));
        }
    }

    public void stepListener() {
        //step 1        
        if (!getEntity().getNationalJobRole().isEmpty()) {
            if (getEntity().getResponsibilityOne() == null && getEntity().getResponsibilityTwo() == null && getEntity().getResponsibilityOne() == null && getEntity().getResponsibilityThree() == null) {
                getEntity().setDamageLevel(null);
                getEntity().setOutcome(StepOutcome.NOT_APPLICABLE.toString());
            } else {
                if (!getEntity().getResponsibilityOne().isEmpty()) {
                    selectedresponsibilityOne = responsibilityService.findByDescription(getEntity().getResponsibilityOne());
                    if (selectedresponsibilityOne != null && selectedresponsibilityOne.getResponsibilityValue() == 30) {
                        getEntity().setDamageLevel(DamageLevel.EXCEPTION.toString());
                        getEntity().setOutcome(StepOutcome.HIGH_RISK.toString());
                        getEntity().setAppliableSecurityLevel(AppliableSecurityLevel.TOP_SECRET);
                        getEntity().setResultBased("Results Based on Step 1");
                    } else if (selectedresponsibilityOne != null && selectedresponsibilityOne.getResponsibilityValue() == 20) {
                        getEntity().setDamageLevel(DamageLevel.SIGNIFICANT.toString());
                        getEntity().setOutcome(StepOutcome.MEDIUM_LOW.toString());
                    } else if (selectedresponsibilityOne != null && selectedresponsibilityOne.getResponsibilityValue() == 10) {
                        getEntity().setDamageLevel(DamageLevel.NO_MATERIAL.toString());
                        getEntity().setOutcome(StepOutcome.MEDIUM_LOW.toString());
                    } else if (selectedresponsibilityOne != null && selectedresponsibilityOne.getResponsibilityValue() == 0) {
                        getEntity().setDamageLevel(DamageLevel.NONE.toString());
                        getEntity().setOutcome(StepOutcome.NOT_APPLICABLE.toString());
                    }
                } else {
                    if (!getEntity().getResponsibilityTwo().isEmpty()) {
                        selectedresponsibilityTwo = responsibilityService.findByDescription(getEntity().getResponsibilityTwo());
                    }
                    if (!getEntity().getResponsibilityThree().isEmpty()) {
                        selectedresponsibilityThree = responsibilityService.findByDescription(getEntity().getResponsibilityThree());
                    }
                    if ((selectedresponsibilityTwo != null && selectedresponsibilityTwo.getResponsibilityValue() <= 30) || (selectedresponsibilityThree != null && selectedresponsibilityThree.getResponsibilityValue() <= 30)) {
                        nationalStep = StepOutcome.MEDIUM_LOW;
                    }
                }
            }

        }

    }

    public void stepListener2() {
        //step 2
        if (!getEntity().getSustainableJobRole().isEmpty()) {
            if (!getEntity().getResponsibilityA().isEmpty() || !getEntity().getResponsibilityB().isEmpty() || !getEntity().getResponsibilityC().isEmpty()) {
                selectedresponsibilityA = responsibilityService.findByDescription(getEntity().getResponsibilityA());
                if (selectedresponsibilityA != null) {
                    outcomeValueA = selectedresponsibilityA.getResponsibilityValue();
                }
                selectedresponsibilityB = responsibilityService.findByDescription(getEntity().getResponsibilityB());
                if (selectedresponsibilityB != null) {
                    outcomeValueB = selectedresponsibilityB.getResponsibilityValue();
                }
                selectedresponsibilityC = responsibilityService.findByDescription(getEntity().getResponsibilityC());
                if (selectedresponsibilityC != null) {
                    outcomeValueC = selectedresponsibilityC.getResponsibilityValue();
                }
            }
            getEntity().setOutcomeValue(outcomeValueA + outcomeValueB + outcomeValueC);
            getEntity().setStep2outcome("Please Proceed to Step 3");
        }
    }

    public void stepListener3() {
        //step 3
        if (getEntity().getAdjustmentCategory() != null) {
            selectedAdjustmentResponsibility = adjustmentResponsibilityService.findByDescription(getEntity().getScopeProgramme());
            if (selectedAdjustmentResponsibility != null) {
                getEntity().setAdjustmentValue(getEntity().getOutcomeValue() - selectedAdjustmentResponsibility.getResponsibilityValue());
                getEntity().setStep3outcome("Please Proceed to Step 4");

                //final step:step 4 Results
                if (adjustmentValue >= 50) {
                    getEntity().setAppliableSecurityLevel(AppliableSecurityLevel.TOP_SECRET);
                    getEntity().setRiskLevel(RiskLevel.HIGH);
                    getEntity().setResultBased("Results Based on Step 2 and 3");
                }

                if (adjustmentValue >= 16 && adjustmentValue <= 49) {
                    getEntity().setAppliableSecurityLevel(AppliableSecurityLevel.SECRET);
                    getEntity().setRiskLevel(RiskLevel.MODARATE);
                    getEntity().setResultBased("Results Based on Step 2 and 3");
                }

                if (adjustmentValue <= 15) {
                    getEntity().setAppliableSecurityLevel(AppliableSecurityLevel.CONFIDENTIAL);
                    getEntity().setRiskLevel(RiskLevel.LOW);
                    getEntity().setResultBased("Results Based on Step 2 and 3");
                }
            }
        }
    }

    public GradeClearance getGradeClearance() {
        return getEntity();
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isDisabled() {
        return !vettingStatus.equals(VettingStatus.SARS_PROCESSING);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public VettingStatus getVettingStatus() {
        return vettingStatus;
    }

    public void setVettingStatus(VettingStatus vettingStatus) {
        this.vettingStatus = vettingStatus;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public List<JobRole> getNationalJobRoles() {
        return nationalJobRoles;
    }

    public void setNationalJobRoles(List<JobRole> nationalJobRoles) {
        this.nationalJobRoles = nationalJobRoles;
    }

    public List<JobRole> getSustainabilityJobRoles() {
        return sustainabilityJobRoles;
    }

    public void setSustainabilityJobRoles(List<JobRole> sustainabilityJobRoles) {
        this.sustainabilityJobRoles = sustainabilityJobRoles;
    }

    public List<SubArea> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(List<SubArea> subAreas) {
        this.subAreas = subAreas;
    }

    public List<AdjustmentCategory> getAdjustmentCategory() {
        return adjustmentCategory;
    }

    public void setAdjustmentCategory(List<AdjustmentCategory> adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
    }

    public List<AdjustmentResponsibility> getAdjustmentResponsibilities() {
        return adjustmentResponsibilities;
    }

    public void setAdjustmentResponsibilities(List<AdjustmentResponsibility> adjustmentResponsibilities) {
        this.adjustmentResponsibilities = adjustmentResponsibilities;
    }

    public Responsibility getSelectedresponsibilityOne() {
        return selectedresponsibilityOne;
    }

    public void setSelectedresponsibilityOne(Responsibility selectedresponsibilityOne) {
        this.selectedresponsibilityOne = selectedresponsibilityOne;
    }

    public Responsibility getSelectedresponsibilityTwo() {
        return selectedresponsibilityTwo;
    }

    public void setSelectedresponsibilityTwo(Responsibility selectedresponsibilityTwo) {
        this.selectedresponsibilityTwo = selectedresponsibilityTwo;
    }

    public Responsibility getSelectedresponsibilityThree() {
        return selectedresponsibilityThree;
    }

    public void setSelectedresponsibilityThree(Responsibility selectedresponsibilityThree) {
        this.selectedresponsibilityThree = selectedresponsibilityThree;
    }

    public JobRole getSelectedJobRoleNational() {
        return selectedJobRoleNational;
    }

    public void setSelectedJobRoleNational(JobRole selectedJobRoleNational) {
        this.selectedJobRoleNational = selectedJobRoleNational;
    }

    public JobRole getSelectedJobRoleSustainable() {
        return selectedJobRoleSustainable;
    }

    public void setSelectedJobRoleSustainable(JobRole selectedJobRoleSustainable) {
        this.selectedJobRoleSustainable = selectedJobRoleSustainable;
    }

    public DamageLevel getNationalDamageLevel() {
        return nationalDamageLevel;
    }

    public void setNationalDamageLevel(DamageLevel nationalDamageLevel) {
        this.nationalDamageLevel = nationalDamageLevel;
    }

    public StepOutcome getNationalStep() {
        return nationalStep;
    }

    public void setNationalStep(StepOutcome nationalStep) {
        this.nationalStep = nationalStep;
    }

    public Responsibility getSelectedresponsibilityA() {
        return selectedresponsibilityA;
    }

    public void setSelectedresponsibilityA(Responsibility selectedresponsibilityA) {
        this.selectedresponsibilityA = selectedresponsibilityA;
    }

    public Responsibility getSelectedresponsibilityB() {
        return selectedresponsibilityB;
    }

    public void setSelectedresponsibilityB(Responsibility selectedresponsibilityB) {
        this.selectedresponsibilityB = selectedresponsibilityB;
    }

    public Responsibility getSelectedresponsibilityC() {
        return selectedresponsibilityC;
    }

    public void setSelectedresponsibilityC(Responsibility selectedresponsibilityC) {
        this.selectedresponsibilityC = selectedresponsibilityC;
    }

    public String getSustainableStep() {
        return sustainableStep;
    }

    public void setSustainableStep(String sustainableStep) {
        this.sustainableStep = sustainableStep;
    }

    public AdjustmentCategory getSelectedAdjustmentCategory() {
        return selectedAdjustmentCategory;
    }

    public void setSelectedAdjustmentCategory(AdjustmentCategory selectedAdjustmentCategory) {
        this.selectedAdjustmentCategory = selectedAdjustmentCategory;
    }

    public AdjustmentResponsibility getSelectedAdjustmentResponsibility() {
        return selectedAdjustmentResponsibility;
    }

    public void setSelectedAdjustmentResponsibility(AdjustmentResponsibility selectedAdjustmentResponsibility) {
        this.selectedAdjustmentResponsibility = selectedAdjustmentResponsibility;
    }

    public String getAdjustmentStep() {
        return adjustmentStep;
    }

    public void setAdjustmentStep(String adjustmentStep) {
        this.adjustmentStep = adjustmentStep;
    }

    public Integer getAdjustmentValue() {
        return adjustmentValue;
    }

    public void setAdjustmentValue(Integer adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
    }

    public Integer getOutcomeValue() {
        return outcomeValue;
    }

    public void setOutcomeValue(Integer outcomeValue) {
        this.outcomeValue = outcomeValue;
    }

    public String getResultBased() {
        return resultBased;
    }

    public void setResultBased(String resultBased) {
        this.resultBased = resultBased;
    }

    public List<Responsibility> getNationalalResponsibilities() {
        return nationalalResponsibilities;
    }

    public void setNationalalResponsibilities(List<Responsibility> nationalalResponsibilities) {
        this.nationalalResponsibilities = nationalalResponsibilities;
    }

    public List<Responsibility> getSustainableResponsibilities() {
        return sustainableResponsibilities;
    }

    public void setSustainableResponsibilities(List<Responsibility> sustainableResponsibilities) {
        this.sustainableResponsibilities = sustainableResponsibilities;
    }

    public boolean isStepResponsibility() {
        return stepResponsibility;
    }

    public void setStepResponsibility(boolean stepResponsibility) {
        this.stepResponsibility = stepResponsibility;
    }

}
