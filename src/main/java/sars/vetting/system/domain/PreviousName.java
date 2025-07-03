/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author S2026064
 */
@Entity
@Table(name = "previos_name")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreviousName extends BaseEntity {

    @Column(name = "previous_lastname")
    private String previousLastName;

    @Column(name = "previous_fullname")
    private String previousFullName;

    @Column(name = "date_of_change")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfChange;

}
