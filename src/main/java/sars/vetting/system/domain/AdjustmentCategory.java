package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "adjustmet_category")
@Getter
@Setter
public class AdjustmentCategory extends BaseEntity {

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AdjustmentResponsibility.class, mappedBy = "adjustmentCategory")
    private List<AdjustmentResponsibility> adjustmenetResponsibilities = new ArrayList();

    public void addResponsibilities(AdjustmentResponsibility adjustmenetResponsibility) {
        adjustmenetResponsibility.setAdjustmentCategory(this);
        this.adjustmenetResponsibilities.add(adjustmenetResponsibility);
    }

    public void removeResponsibilities(AdjustmentResponsibility adjustmenetResponsibility) {
        this.adjustmenetResponsibilities.remove(adjustmenetResponsibility);
    }

}
