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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sars.vetting.system.common.ScreeningResponseOption;

@Entity
@Table(name = "screening_declaration_checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningDeclarationResponse extends BaseEntity {
    
    @Column(name = "current_sars_employee", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption currentSarsEmployee;
    
    @Column(name = "former_sars_employee", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption formerSarsEmployee;
    
     @Column(name = "registered_taxpayer", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption registeredTaxpayer;
    
    @Column(name = "rsa_citizen", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption rsaCitizen;
    
    @Column(name = "dual_citizenship", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption dualCitizenship;
    
    @Column(name = "blacklisted", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption blacklisted;
    
    @Column(name = "valid_qualification", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption validQualification;
    
    @Column(name = "illegal_drug", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption illegalDrug;
    
    @Column(name = "criminal_charges", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption criminalCharges;
    
    @Column(name = "criminal_conviction", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption criminalConviction;
    
    @Column(name = "disciplanary_hearing_current", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption disciplinaryHearingAtCurrent;
    
    @Column(name = "disciplanary_hearing_former", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption disciplinaryHearingAtFormer;
    
    @Column(name = "investigated_for_misconduct", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption investigatedForMisconduct;
    
    @Column(name = "tax_practitioner", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption taxPractitioner;
    
    @Column(name = "clearing_agent", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption clearingAgent;
    
    @Column(name = "business_with_state_organ", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption businnessWithStateOrgan;
    
    @Column(name = "board_membership", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption boardMembership;
    
    @Column(name = "family_members_board_membership", nullable = true)
    @Enumerated(EnumType.STRING)
    private ScreeningResponseOption familyMembersBoardMembership;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<Citizenship> citizenships = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<CriminalCharge> laidCriminalCharges = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<ConvictedCriminalCharge> convictedCriminalCharges = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<CurrentEmpDisciplinaryHearing> currentEmpDisciplinaryHearings = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<PreviousDisciplinaryHearing> formerEmpDisciplinaryHearings = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<MisconductAllegation> misconductAllegations = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<StateOrganBusiness> stateOrganBusinesses = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<BoardMembership> boardMemberships = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<FamilyBoardMembership> familyBoardMemberships = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_id")
    private List<IllegalDrug> illegalDrugs = new ArrayList();

    @Column(name = "tax_pract_number")
    private String taxPractitionerNo;
    
      @Column(name = "tax_number")
    private String taxNo;

    public void addCitizenship(Citizenship citizenship) {
        this.citizenships.add(citizenship);
    }

    public void removeCitizenship(Citizenship citizenship) {
        this.citizenships.remove(citizenship);
    }

    public void addLaidCriminalCharge(CriminalCharge laidCriminalCharge) {
        this.laidCriminalCharges.add(laidCriminalCharge);
    }

    public void removeLaidCriminalCharge(CriminalCharge laidCriminalCharge) {
        this.laidCriminalCharges.remove(laidCriminalCharge);
    }

    public void addConvictedCriminalCharge(ConvictedCriminalCharge convictedCriminalCharge) {
        this.convictedCriminalCharges.add(convictedCriminalCharge);
    }

    public void removeConvictedCriminalCharge(ConvictedCriminalCharge convictedCriminalCharge) {
        this.convictedCriminalCharges.remove(convictedCriminalCharge);
    }

    public void addCurrentEmpDisciplinaryHearing(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing) {
        this.currentEmpDisciplinaryHearings.add(currentEmpDisciplinaryHearing);
    }

    public void removeCurrentEmpDisciplinaryHearing(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing) {
        this.currentEmpDisciplinaryHearings.remove(currentEmpDisciplinaryHearing);
    }

    public void addFormerEmpDisciplinaryHearing(PreviousDisciplinaryHearing formerEmpDisciplinaryHearing) {
        this.formerEmpDisciplinaryHearings.add(formerEmpDisciplinaryHearing);
    }

    public void removeFormerEmpDisciplinaryHearing(PreviousDisciplinaryHearing formerEmpDisciplinaryHearing) {
        this.formerEmpDisciplinaryHearings.remove(formerEmpDisciplinaryHearing);
    }

    public void addMisconductAllegation(MisconductAllegation misconductAllegation) {
        this.misconductAllegations.add(misconductAllegation);
    }

    public void removeMisconductAllegation(MisconductAllegation misconductAllegation) {
        this.misconductAllegations.remove(misconductAllegation);
    }

    public void addStateOrganBusiness(StateOrganBusiness stateOrganBusiness) {
        this.stateOrganBusinesses.add(stateOrganBusiness);
    }

    public void removeStateOrganBusiness(StateOrganBusiness stateOrganBusiness) {
        this.stateOrganBusinesses.remove(stateOrganBusiness);
    }

    public void addBoardMembership(BoardMembership boardMembership) {
        this.boardMemberships.add(boardMembership);
    }

    public void removeBoardMembership(BoardMembership boardMembership) {
        this.boardMemberships.remove(boardMembership);
    }

    public void addFamilyBoardMembership(FamilyBoardMembership familyBoardMembership) {
        this.familyBoardMemberships.add(familyBoardMembership);
    }

    public void removeFamilyBoardMembership(FamilyBoardMembership familyBoardMembership) {
        this.familyBoardMemberships.remove(familyBoardMembership);
    }

    public void addIllegalDrug(IllegalDrug illegalDrug) {
        this.illegalDrugs.add(illegalDrug);
    }

    public void removeIllegalDrug(IllegalDrug illegalDrug) {
        this.illegalDrugs.remove(illegalDrug);
    }

}
