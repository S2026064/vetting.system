package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "qualification_type")
@Getter
@Setter
public class QualificationType extends BaseEntity {

    @Column(name = "description")
    private String description;

}
