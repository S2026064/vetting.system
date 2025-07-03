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
@Table(name = "complete_vetting_sett")
@Getter
@Setter
public class CompleteVettingSetting extends BaseEntity {
    @Column(name = "complete_vetting")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean completeVetting;

    public CompleteVettingSetting() {
        this.completeVetting = Boolean.FALSE;
    }
}
