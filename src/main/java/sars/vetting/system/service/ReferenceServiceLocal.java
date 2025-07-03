/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Reference;

/**
 *
 * @author S2026987
 */
public interface ReferenceServiceLocal {
    
    Reference save(Reference reference);

    Reference findById(Long id);

    Reference update(Reference reference);

    Reference deleteById(Long id);

    List<Reference> listAll();

    boolean isExist(Reference reference);
    
  
   
    
}
