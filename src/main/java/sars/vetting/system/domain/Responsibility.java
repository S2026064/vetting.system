package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.DamageLevel;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "responsibility")
@Getter
@Setter
public class Responsibility extends BaseEntity {

    @Lob
    @Column(name = "description")
    private String description;
    
    @Column(name = "respo_value")
    private Integer responsibilityValue;
    
    @Column(name = "damage_level")
    @Enumerated(EnumType.STRING)
    private DamageLevel damageLevel;
    

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "job_role_id")
    private JobRole jobRole;

}
