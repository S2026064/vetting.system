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
 * @author S2028398
 */
@Entity
@Table(name = "citizenship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Citizenship extends BaseEntity {

    @Column(name = "description")
    public String description;
}
