package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sars.vetting.system.common.SpouseType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "spouse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Spouse extends Person {
    @Column(name = "spouse_type")
    @Enumerated(EnumType.STRING)
    private SpouseType spouseType;   
    
}
