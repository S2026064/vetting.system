package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "adjust_cat_respons")
@Getter
@Setter
public class AdjustmentResponsibility extends BaseEntity {

    @Column(name = "description")
    private String description;
    
    @Column(name = "respo_value")
    private Integer responsibilityValue;    

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "adjust_cat_id")
    private AdjustmentCategory adjustmentCategory;

}
