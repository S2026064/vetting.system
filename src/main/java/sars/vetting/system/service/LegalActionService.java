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
import sars.vetting.system.domain.LegalAction;
import sars.vetting.system.persistence.AttachmentRepository;
import sars.vetting.system.persistence.LegalActionRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class LegalActionService implements LegalActionServiceLocal{
    
    @Autowired
    private LegalActionRepository legalActionRepository;

    @Override
    public LegalAction save(LegalAction legalAction) {
        return legalActionRepository.save(legalAction);
    }

    @Override
    public LegalAction findById(Long id) {
        return legalActionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public LegalAction update(LegalAction legalAction) {
        return legalActionRepository.save(legalAction);
    }

    @Override
    public LegalAction deleteById(Long id) {
        LegalAction legalAction = findById(id);

        if (legalAction != null) {
            legalActionRepository.delete(legalAction);
        }
        return legalAction;
    }

    @Override
    public List<LegalAction> listAll() {
        return legalActionRepository.findAll();
    }

   
  

  
    
}
