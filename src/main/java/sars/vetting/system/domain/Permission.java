package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "permission")
@Getter
@Setter
public class Permission extends BaseEntity {

    @Column(name = "view_record")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean read;

    @Column(name = "update_record")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean update;

    @Column(name = "delete_record")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean delete;

    @Column(name = "add_record")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean add;

    @Column(name = "write_record")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean write;

    public Permission() {
        this.read = Boolean.FALSE;
        this.update = Boolean.FALSE;
        this.delete = Boolean.FALSE;
        this.add = Boolean.FALSE;
        this.write = Boolean.FALSE;
    }

}
