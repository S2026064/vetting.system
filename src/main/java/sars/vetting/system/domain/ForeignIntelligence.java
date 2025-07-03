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
@Table(name = "foreign_intelligence")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForeignIntelligence extends BaseEntity {

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_number")
    private String serviceNo;

    @Column(name = "country_name")
    private String countryName;

     @Column(name = "contact_name")
    private String contactName;

    
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

}
