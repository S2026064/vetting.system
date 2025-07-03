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
import sars.vetting.system.common.Relationship;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "emp_relative")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRelative extends Person {

    @Column(name = "relationship")
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

}
