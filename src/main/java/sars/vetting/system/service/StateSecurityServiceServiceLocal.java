/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.StateSecurityService;

/**
 *
 * @author S2026987
 */
public interface StateSecurityServiceServiceLocal {
    
    StateSecurityService save(StateSecurityService securityService);

    StateSecurityService findById(Long id);

    StateSecurityService update(StateSecurityService securityService);

    StateSecurityService deleteById(Long id);

    List<StateSecurityService> listAll();

    boolean isExist(StateSecurityService securityService);
    
  
   
    
}
