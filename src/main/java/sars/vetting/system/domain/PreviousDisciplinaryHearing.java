package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "prev_disciplinary_hearing")
@Getter
@Setter
public class PreviousDisciplinaryHearing extends BaseEntity {

    @Column(name = "charge")
    private String charge;

    @Column(name = "outcome")
    private String outcome;
}
