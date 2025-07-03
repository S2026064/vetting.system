/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.BoardMembershipType;

/**
 *
 * @author S2026080
 */
@Entity
@Table(name = "board_membership")
@Getter
@Setter
public class BoardMembership extends BaseEntity {

    @Column(name = "board_membership_type")
    @Enumerated(EnumType.STRING)
    private BoardMembershipType boardMembershipType;
    
    @Column(name = "corporation_role")
    private String corporationRole;

    @Column(name = "name_corporation")
    private String nameOfCorporation;

}
