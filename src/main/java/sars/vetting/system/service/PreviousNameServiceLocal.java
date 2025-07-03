/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.PreviousName;

/**
 *
 * @author S2026064
 */
public interface PreviousNameServiceLocal {
    
     PreviousName save(PreviousName previousName);

    PreviousName findById(Long id);

    PreviousName update(PreviousName previousName);

    PreviousName deleteById(Long id);

    List<PreviousName> listAll();
    
}
