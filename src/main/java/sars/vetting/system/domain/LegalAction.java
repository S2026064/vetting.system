package sars.vetting.system.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sars.vetting.system.common.LegalActionType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "legal_action")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LegalAction extends BaseEntity {

    @Column(name = "convicted_place")
    private String convictedPlace;

    @Column(name = "criminal_case_findings")
    private String caseFindings;

    @Column(name = "served_by")
    private String servedBy;

    @Column(name = "nature_of_case")
    private String natureOfCase;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "case_nature")
    private String caseNature;
    @Column(name = "insolvent_place")
    private String insolventPlace;

    @Column(name = "summons_place")
    private String summonPlace;

    @Column(name = "summons_findings")
    private String summonFindings;

    @Column(name = "case_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date caseDate;

    @Column(name = "summons_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date summonDate;

    @Column(name = "insolvent_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insolventDate;
    
    @Column(name = "legal_action_type")
    @Enumerated(EnumType.STRING)
    private LegalActionType legalActionType;

}
