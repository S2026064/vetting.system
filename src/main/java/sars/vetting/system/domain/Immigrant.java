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
import sars.vetting.system.common.YesNo;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "immigrant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Immigrant extends BaseEntity {

    @Column(name = "arrival_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDate;

    @Column(name = "issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date permitIssueDate;

    @Column(name = "naturalized_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date naturalizedDate;

    @Column(name = "emmigration_country_name")
    private String emmigrationCountryName;

    @Column(name = "issued_country")
    private String issuedCountry;

    @Column(name = "entry_port")
    private String entryPort;

    @Column(name = "certificate_number")
    private String certificateNumber;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "immigration_permit_number")
    private String immigrationPermitNumber;

    @Column(name = "permanent_residance_permit")
    @Enumerated(EnumType.STRING)
    private YesNo permanentResidencePermit;

    @Column(name = "immigrant_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date immigratedDate;

}
