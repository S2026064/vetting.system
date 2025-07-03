package sars.vetting.system.domain;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "state_security_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateSecurityService extends BaseEntity {

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "service_number")
    private String serviceNumber;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "service_name")
    private String serviceName;

}
