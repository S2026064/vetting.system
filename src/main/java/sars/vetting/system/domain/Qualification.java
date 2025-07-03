package sars.vetting.system.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "qualification")
@Getter
@Setter
public class Qualification extends BaseEntity {

    @Column(name = "qualification_name")
    private String qualificationName;

    @Column(name = "year_issued")
    private String yearIssued;

    @Column(name = "institution")
    private String institution;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "certificate_number")
    private String certificateNumber;

    @Column(name = "exam_number")
    private String examNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "date_obtained")
    private String dateObtained;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "qual_type_id")
    private QualificationType qualificationType;

    @Column(name = "verification")
    private String verification;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
