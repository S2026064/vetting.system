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
@Table(name = "illegal_drug")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IllegalDrug extends BaseEntity {

    @Column(name = "drug_name")
    private String drugName;

    @Column(name = "rehabilitation")
    private String rehabilitation;

}
