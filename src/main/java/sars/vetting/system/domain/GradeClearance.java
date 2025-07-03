package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.AppliableSecurityLevel;
import sars.vetting.system.common.RiskLevel;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "grade_clearance")
@Getter
@Setter
public class GradeClearance extends BaseEntity {

    @Column(name = "national_job_role")
    private String nationalJobRole;

    @Column(name = "responsibility_one")
    private String responsibilityOne;

    @Column(name = "responsibility_two")
    private String responsibilityTwo;

    @Column(name = "responsibility_three")
    private String responsibilityThree;

    @Column(name = "step_one_damage_level")
    private String damageLevel;

    @Column(name = "step_one_outcome")
    private String outcome;

    @Column(name = "sustainable_job_role")
    private String sustainableJobRole;

    @Column(name = "responsibility_A")
    private String responsibilityA;

    @Column(name = "responsibility_B")
    private String responsibilityB;

    @Column(name = "responsibility_C")
    private String responsibilityC;

    @Column(name = "outcome_value")
    private Integer outcomeValue;

    @Column(name = "step_two_outcome")
    private String step2outcome;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "adjust_cat_id")
    private AdjustmentCategory adjustmentCategory;

    @Column(name = "adjustement_value")
    private Integer adjustmentValue = 0;

    @Column(name = "step_three_outcome")
    private String step3outcome;

    @Column(name = "scope_programme")
    private String scopeProgramme;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "sub_area_id")
    private SubArea subArea;

    @Column(name = "result_based")
    private String resultBased;

    @Column(name = "risk_level")
    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @Column(name = "clearence_level")
    @Enumerated(EnumType.STRING)
    private AppliableSecurityLevel appliableSecurityLevel;

    @Lob
    @Column(name = "comment")
    private String commentSummary;

}
