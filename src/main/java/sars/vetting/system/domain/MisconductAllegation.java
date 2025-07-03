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
@Table(name = "misconduct_allegation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MisconductAllegation extends BaseEntity {

    @Column(name = "charge")
    private String charge;

    @Column(name = "outcome")
    private String outcome;

}
