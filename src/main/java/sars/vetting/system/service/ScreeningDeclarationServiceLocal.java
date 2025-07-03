/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.ScreeningDeclaration;

/**
 *
 * @author S2026064
 */
public interface ScreeningDeclarationServiceLocal {

    ScreeningDeclaration save(ScreeningDeclaration screeningDeclaration);

    ScreeningDeclaration findById(Long id);

    ScreeningDeclaration update(ScreeningDeclaration screeningDeclaration);

    ScreeningDeclaration deleteById(Long id);

    List<ScreeningDeclaration> listAll();

    boolean isExist(ScreeningDeclaration screeningDeclaration);
    
    

}
