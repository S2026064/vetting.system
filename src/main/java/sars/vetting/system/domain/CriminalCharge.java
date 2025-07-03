/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@Table(name = "criminal_charge")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriminalCharge extends BaseEntity {

    @Column(name = "nature")
    private String nature;

    @Column(name = "outcome")
    private String outcome;
}
