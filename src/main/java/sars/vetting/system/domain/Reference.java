package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "reference")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reference extends Person {
    @Column(name = "years_known")
    private int yearsKnown; 
    
    @Column(name = "occupation")
    private String occupation; 
}
