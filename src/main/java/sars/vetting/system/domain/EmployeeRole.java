package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "emp_role")
@Getter
@Setter
public class EmployeeRole extends BaseEntity {

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "report_sett_id")
    private ReportSetting reportSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_sett_id")
    private AdministrationSetting AdminSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "complete_vett_sett_id")
    private CompleteVettingSetting completeVettingSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "initiate_vett_sett_id")
    private InitiateVettingSetting initiateVettingSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_vett_sett_id")
    private ReviewVettingSetting reviewVettingSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "approve_vett_sett_id")
    private VettingApprovalSetting vettingApprovalSetting;

    public EmployeeRole(String description) {
        this.description = description;
    }

    public EmployeeRole() {
    }
}
