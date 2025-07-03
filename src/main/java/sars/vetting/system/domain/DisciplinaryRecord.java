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
@Table(name = "disciplinary_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaryRecord extends BaseEntity {

    @Column(name = "summary")
    private String summary;

    @Column(name = "comment")
    private String comment;

    @Column(name = "offence_date")
    private String offenceDate;

    @Column(name = "outcome_date")
    private String outcomeDate;

    @Column(name = "status")
    private String status;

}
