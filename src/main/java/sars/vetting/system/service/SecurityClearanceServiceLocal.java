/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.SecurityClearance;

/**
 *
 * @author S2026064
 */
public interface SecurityClearanceServiceLocal {

    SecurityClearance save(SecurityClearance securityClearance);

    SecurityClearance findById(Long id);

    SecurityClearance update(SecurityClearance securityClearance);

    SecurityClearance deleteById(Long id);

    List<SecurityClearance> listAll();

}
