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
@Table(name = "question")
@Getter
@Setter
public class Question extends BaseEntity {

    @Column(name = "description")
    private String description;

}
