package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "sub_area")
@Getter
@Setter
public class SubArea extends BaseEntity {

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Area.class, mappedBy = "subArea")
    private List<Area> areas = new ArrayList();

    public void addAreas(Area area) {
        area.setSubArea(this);
        this.areas.add(area);
    }

    public void removeAreas(Area area) {
        this.areas.remove(area);
    }

}
