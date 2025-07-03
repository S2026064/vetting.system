package sars.vetting.system.domain;

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
@Table(name = "visited_country")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitedCountry extends BaseEntity {

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "purpose")
    private String purpose;

}
