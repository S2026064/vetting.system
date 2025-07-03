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
@Table(name = "family_board_membership")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyBoardMembership extends BaseEntity {

    @Column(name = "corporation_role")
    private String corporationRole;

    @Column(name = "corporation_name")
    private String corporationName;
}
