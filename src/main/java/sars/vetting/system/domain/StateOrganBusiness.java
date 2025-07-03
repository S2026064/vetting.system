package sars.vetting.system.domain;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "state_organ_business")
@Getter
@Setter
public class StateOrganBusiness extends BaseEntity {

    @Column(name = "state_organ")
    private String stateOrgan;

    @Column(name = "contract_expiring_date")
      @Temporal(TemporalType.TIMESTAMP)
    private Date contractExpiringDate;
}
