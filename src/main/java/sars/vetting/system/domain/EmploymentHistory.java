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
@Table(name = "emp_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentHistory extends BaseEntity {

    @Column(name = "designation")
    private String designation;

    @Column(name = "serviceNo")
    private String serviceNo;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "supervisor_name")
    private String supervisorName;

    @Column(name = "supervisor_tel")
    private String supervisorTel;

    @Column(name = "physical_address")
    private String physicalAddress;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

}
