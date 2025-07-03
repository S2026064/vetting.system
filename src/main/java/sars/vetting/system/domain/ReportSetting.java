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
@Table(name = "report_sett")
@Getter
@Setter
public class ReportSetting extends BaseEntity {
    @Column(name = "report")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean report;

    public ReportSetting() {
        this.report = Boolean.FALSE;
    }
}
