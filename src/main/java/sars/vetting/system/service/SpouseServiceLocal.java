/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Spouse;

/**
 *
 * @author S2026987
 */
public interface SpouseServiceLocal {
    
    Spouse save(Spouse spouse);

    Spouse findById(Long id);

    Spouse update(Spouse spouse);

    Spouse deleteById(Long id);

    List<Spouse> listAll();

    boolean isExist(Spouse spouse);
    
  
   
    
}
