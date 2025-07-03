/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2028398
 */
@Entity
@Table(name = "note")
@Getter
@Setter
public class Note extends BaseEntity {

    @Column(name = "description", length = 8000)
    private String description;
    
    @Column(name = "role_description")
    private String roleDescription;

    @ManyToOne(optional = true,fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "vetting_id")
    private Vetting vetting;

    @Transient
    private String clickedButton;
}
