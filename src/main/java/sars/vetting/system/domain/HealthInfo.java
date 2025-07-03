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
@Table(name = "health_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthInfo extends BaseEntity {

    @Column(name = "institution")
    private String institution;

    @Column(name = "counsellor")
    private String counsellor;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "health_problem")
    private String healthProblem;

}
