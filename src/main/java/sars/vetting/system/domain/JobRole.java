package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.JobRoleType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "job_role")
@Getter
@Setter
public class JobRole extends BaseEntity {

    @Lob
    @Column(name = "description")
    private String description;
    
    @Column(name = "job_role_type")
    @Enumerated(EnumType.STRING)
    private JobRoleType jobRoleType;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Responsibility.class, mappedBy = "jobRole")
    private List<Responsibility> responsibilities = new ArrayList();


    public void addResponsibilities(Responsibility responsibility) {
        responsibility.setJobRole(this);
        this.responsibilities.add(responsibility);
    }

    public void removeResponsibilities(Responsibility responsibility) {
        this.responsibilities.remove(responsibility);
    }

}
