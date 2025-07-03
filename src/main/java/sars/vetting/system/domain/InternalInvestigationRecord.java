package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "internal_investigation_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternalInvestigationRecord extends BaseEntity {

    @Column(name = "summary")
    private String summary;

    @Column(name = "case_number")
    private String caseNumber;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private String status;

    @Column(name = "investigation_date")
    private String investigationDate;

}
