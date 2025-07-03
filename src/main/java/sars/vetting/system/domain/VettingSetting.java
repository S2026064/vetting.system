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
@Table(name = "vetting_sett")
@Getter
@Setter
public class VettingSetting extends BaseEntity {

    @Column(name = "initiate_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean initiateVetting;
    @Column(name = "complete_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean completeVetting;
    @Column(name = "analyse_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean analyseVetting;
    @Column(name = "approve_vetting_reports_sop")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean approveVettingReportsSOP;
    @Column(name = "screening")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean screening;

    public VettingSetting() {
        this.initiateVetting = Boolean.FALSE;
        this.completeVetting = Boolean.FALSE;
        this.analyseVetting = Boolean.FALSE;
        this.approveVettingReportsSOP = Boolean.FALSE;
        this.screening = Boolean.FALSE;
    }

    public boolean isVetting() {
        return this.initiateVetting || this.completeVetting || this.analyseVetting || this.approveVettingReportsSOP || this.screening;
    }
}
