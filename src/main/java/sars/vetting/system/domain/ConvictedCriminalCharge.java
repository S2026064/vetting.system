/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@Table(name = "convicted_criminal_charge")
@Getter
@Setter
public class ConvictedCriminalCharge extends BaseEntity {

    @Column(name = "nature_of_charge")
    private String natureOfCharge;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "verdict")
    private String verdict;

}
