package sars.vetting.system.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "screening_declaration")
@Getter
@Setter
public class ScreeningDeclaration extends BaseEntity {

    @Column(name = "job_title")
    private String JobTitle;

    @Column(name = "screening_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date screeningDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_declaration_response_id")
    private ScreeningDeclarationResponse screeningDeclarationResponse;

    @Column(name = "candidate_signature")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean candidateSignature;

}
