package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.DopiStatus;
import sars.vetting.system.common.EmployeeStatus;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee extends Person {

    @Column(name = "emp_sid")
    private String sid;

    @Column(name = "emp_number")
    private String employeeNumber;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "position_title")
    private String positionTitle;

    @Column(name = "grade")
    private String grade;

    @Column(name = "gender")
    private String gender;

    @Column(name = "manager_sid")
    private String managerSID;

    @Column(name = "manager_fullname")
    private String managerFullName;

    @Column(name = "manager_position")
    private String managerPosition;

    @Column(name = "manager_contact_number")
    private String managerContactNumber;

    @Column(name = "manager_email")
    private String managerEmailAddress;

    @Column(name = "tax_ref_number")
    private String taxReferenceNumber;

    @Column(name = "devision_name")
    private String devisionName;

    @Column(name = "devision_code")
    private String devisionCode;

    @Column(name = "race")
    private String race;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "region_name")
    private String regionName;
    
//    @Column(name = "gender_description")
//    private String genderDescription;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee_id")
    @OneToMany(cascade = CascadeType.ALL, targetEntity = PublicOfficer.class, mappedBy = "employee",orphanRemoval = true)
    private List<PublicOfficer> publicOfficerDetails = new ArrayList();

    @Column(name = "dopi_satus")
    @Enumerated(EnumType.STRING)
    private DopiStatus dopiStatus;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee_id")
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Qualification.class, mappedBy = "employee",orphanRemoval = true)
    private List<Qualification> qualifications = new ArrayList();

    @Column(name = "emp_status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "emp_role")
    private EmployeeRole employeeRole;

    public void addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
    }

    public void removeQualification(Qualification qualification) {
        this.qualifications.remove(qualification);
    }

    public void addPublicOfficer(PublicOfficer publicOfficer) {
        this.publicOfficerDetails.add(publicOfficer);
    }

    public void removePublicOfficer(PublicOfficer publicOfficer) {
        this.publicOfficerDetails.remove(publicOfficer);
    }

    public void addPublicOfficerDetails(List<PublicOfficer> publicOfficerDetails) {
        this.publicOfficerDetails.addAll(publicOfficerDetails);
    }

    public void addQualifications(List<Qualification> qualifications) {
        this.qualifications.addAll(qualifications);
    }
}
