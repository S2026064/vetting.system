package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.YesNo;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "z204_form")
@Getter
@Setter
public class Z204Form extends BaseEntity {

    @Column(name = "delegated_official")
    private String delegatedOfficial;

    @Column(name = "delegated_official_telephone")
    private String delegatedOfficialTelephone;

    @Column(name = "clearence_level")
    @Enumerated(EnumType.STRING)
    private ClearanceLevel clearanceLevel;

    @Column(name = "spycological_therapy")
    @Enumerated(EnumType.STRING)
    private YesNo psycologicalTherapy;

    @Column(name = "naturalised")
    @Enumerated(EnumType.STRING)
    private YesNo naturalised;

    @Column(name = "alcohol_treatment")
    @Enumerated(EnumType.STRING)
    private YesNo alcoholTreatment;

    @Column(name = "permanet_resitance")
    @Enumerated(EnumType.STRING)
    private YesNo permanentResidence;

    @Column(name = "drug_abuse")
    @Enumerated(EnumType.STRING)
    private YesNo drugAbuse;

    @Column(name = "immigrated_to_sa")
    @Enumerated(EnumType.STRING)
    private YesNo immigratedToSA;

    @Column(name = "visit_outside_sa")
    @Enumerated(EnumType.STRING)
    private YesNo visitOutsideSA;

    @Column(name = "foreign_intel")
    @Enumerated(EnumType.STRING)
    private YesNo foreignIntelligence;

    @Column(name = "convicted_criminal")
    @Enumerated(EnumType.STRING)
    private YesNo convictedCriminal;

    @Column(name = "summons")
    @Enumerated(EnumType.STRING)
    private YesNo summon;

    @Column(name = "insolvent")
    @Enumerated(EnumType.STRING)
    private YesNo insolvent;

    @Column(name = "security_service")
    @Enumerated(EnumType.STRING)
    private YesNo securityService;

    @Column(name = "security_clearance")
    @Enumerated(EnumType.STRING)
    private YesNo securityClearance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attach_checklist_id")
    private AttachmentChecklist attachmentChecklist;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fin_docs_check_id")
    private FinancialDocsResponse financialDocsResponse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "immigrant_id")
    private Immigrant immigrant;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<Reference> references = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<Spouse> spouses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<EmployeeRelative> empRelatives = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<SecurityClearance> securityClearances = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<HealthInfo> healthInfos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<ForeignIntelligence> foreignIntelligences = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<EmploymentHistory> employmentHistorys = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<LegalAction> legalActions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<StateSecurityService> serviceInStateSecuritys = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "z204_form_id")
    private List<VisitedCountry> visitReisidenceOutOfSAs = new ArrayList<>();

    public Z204Form() {
        this.setPsycologicalTherapy(YesNo.NO);
        this.setSummon(YesNo.NO);
        this.setInsolvent(YesNo.NO);
        this.setVisitOutsideSA(YesNo.NO);
        this.setSecurityService(YesNo.NO);
        this.setPermanentResidence(YesNo.NO);
        this.setNaturalised(YesNo.NO);
        this.setDrugAbuse(YesNo.NO);
        this.setConvictedCriminal(YesNo.NO);
        this.setAlcoholTreatment(YesNo.NO);
        this.setForeignIntelligence(YesNo.NO);
        this.setImmigratedToSA(YesNo.NO);
        this.setSecurityClearance(YesNo.NO);
    }

    public void addReference(Reference reference) {
        this.references.add(reference);
    }

    public void removeReference(Reference reference) {
        this.references.remove(reference);
    }

    public void addSecurityClearance(SecurityClearance securityClearance) {
        this.securityClearances.add(securityClearance);
    }

    public void removeSecurityClearance(SecurityClearance securityClearance) {
        this.securityClearances.remove(securityClearance);
    }

    public void addEmployeeRelative(EmployeeRelative employeeRelative) {
        this.empRelatives.add(employeeRelative);
    }

    public void removeEmployeeRelative(EmployeeRelative employeeRelative) {
        this.empRelatives.remove(employeeRelative);
    }

    public void addSpouse(Spouse spouse) {
        this.spouses.add(spouse);
    }

    public void removeSpouse(Spouse spouse) {
        this.spouses.remove(spouse);
    }

    public void addEmpRelative(EmployeeRelative empRelative) {
        this.empRelatives.add(empRelative);
    }

    public void removeEmpRelative(EmployeeRelative empRelative) {
        this.empRelatives.remove(empRelative);
    }

    public void addHealthInfo(HealthInfo healthInfo) {
        this.healthInfos.add(healthInfo);
    }

    public void removeHealthInfo(HealthInfo healthInfo) {
        this.healthInfos.remove(healthInfo);
    }

    public void addForeignIntelligence(ForeignIntelligence foreignIntelligence) {
        this.foreignIntelligences.add(foreignIntelligence);
    }

    public void removeForeignIntelligence(ForeignIntelligence foreignIntelligence) {
        this.foreignIntelligences.remove(foreignIntelligence);
    }

    public void addEmploymentHistory(EmploymentHistory employmentHistory) {
        this.employmentHistorys.add(employmentHistory);
    }

    public void removeEmploymentHistory(EmploymentHistory employmentHistory) {
        this.employmentHistorys.remove(employmentHistory);
    }

    public void addLegalAction(LegalAction legalAction) {
        this.legalActions.add(legalAction);
    }

    public void removeLegalAction(LegalAction legalAction) {
        this.legalActions.remove(legalAction);
    }

    public void addServiceInStateSecurity(StateSecurityService serviceInStateSecurity) {
        this.serviceInStateSecuritys.add(serviceInStateSecurity);
    }

    public void removeServiceInStateSecurity(StateSecurityService serviceInStateSecurity) {
        this.serviceInStateSecuritys.remove(serviceInStateSecurity);
    }

    public void addVisitReisidenceOutOfSA(VisitedCountry visitReisidenceOutOfSA) {
        this.visitReisidenceOutOfSAs.add(visitReisidenceOutOfSA);
    }

    public void removeVisitReisidenceOutOfSA(VisitedCountry visitReisidenceOutOfSA) {
        this.visitReisidenceOutOfSAs.remove(visitReisidenceOutOfSA);
    }

}
