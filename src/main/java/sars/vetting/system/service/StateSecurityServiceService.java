/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.StateSecurityService;
import sars.vetting.system.persistence.AttachmentRepository;
import sars.vetting.system.persistence.StateSecurityServiceRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class StateSecurityServiceService implements StateSecurityServiceServiceLocal{
    
    @Autowired
    private StateSecurityServiceRepository securityServiceRepository;

    @Override
    public StateSecurityService save(StateSecurityService securityService) {
        return securityServiceRepository.save(securityService);
    }

    @Override
    public StateSecurityService findById(Long id) {
        return securityServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public StateSecurityService update(StateSecurityService securityService) {
        return securityServiceRepository.save(securityService);
    }

    @Override
    public StateSecurityService deleteById(Long id) {
        StateSecurityService securityService = findById(id);

        if (securityService != null) {
            securityServiceRepository.delete(securityService);
        }
        return securityService;
    }

    @Override
    public List<StateSecurityService> listAll() {
        return securityServiceRepository.findAll();
    }

    @Override
    public boolean isExist(StateSecurityService securityService) {
        return securityServiceRepository.findById(securityService.getId()) != null;
    }

  

  
    
}
