/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.HealthInfo;

/**
 *
 * @author S2026987
 */
public interface HealthInfoServiceLocal {
    
    HealthInfo save(HealthInfo healthInfo);

    HealthInfo findById(Long id);

    HealthInfo update(HealthInfo healthInfo);

    HealthInfo deleteById(Long id);

    List<HealthInfo> listAll();

    boolean isExist(HealthInfo healthInfo);
    
  
   
    
}
