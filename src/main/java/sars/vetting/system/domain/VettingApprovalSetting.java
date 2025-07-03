package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "approve_vetting_sett")
@Getter
@Setter
public class VettingApprovalSetting extends BaseEntity {

    @Column(name = "approve_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean approveVetting;
    @Column(name = "second_approve_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean approveSecondLevel;

    public VettingApprovalSetting() {
        this.approveVetting = Boolean.FALSE;
        this.approveSecondLevel = Boolean.FALSE;
    }

    public boolean isVettingApproval() {
        return this.approveVetting || this.approveSecondLevel;
    }
}
