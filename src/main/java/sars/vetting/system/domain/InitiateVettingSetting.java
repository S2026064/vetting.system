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
@Table(name = "initiate_vetting_sett")
@Getter
@Setter
public class InitiateVettingSetting extends BaseEntity {
    @Column(name = "vettingInitiation")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean vettingInitiation;

    public InitiateVettingSetting() {
        this.vettingInitiation = Boolean.FALSE;
    }
}
