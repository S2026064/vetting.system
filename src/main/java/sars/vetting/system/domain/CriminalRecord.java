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
@Table(name = "criminal_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriminalRecord extends BaseEntity {

    @Column(name = "offence")
    private String offence;

    @Column(name = "case_number")
    private String caseNo;

    @Column(name = "place")
    private String place;

    @Column(name = "comment")
    private String comment;

    @Column(name = "result")
    private String result;

    @Column(name = "offence_date")
    private String offenceDate;

}
