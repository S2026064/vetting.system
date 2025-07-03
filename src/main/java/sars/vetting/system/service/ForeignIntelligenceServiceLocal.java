/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.ForeignIntelligence;

/**
 *
 * @author S2026987
 */
public interface ForeignIntelligenceServiceLocal {
    
    ForeignIntelligence save(ForeignIntelligence foreignIntelligence);

    ForeignIntelligence findById(Long id);

    ForeignIntelligence update(ForeignIntelligence foreignIntelligence);

    ForeignIntelligence deleteById(Long id);

    List<ForeignIntelligence> listAll();

    boolean isExist(ForeignIntelligence foreignIntelligence);
    
  
   
    
}
