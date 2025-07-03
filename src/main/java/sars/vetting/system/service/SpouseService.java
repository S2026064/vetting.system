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
import sars.vetting.system.domain.Spouse;
import sars.vetting.system.persistence.AttachmentRepository;
import sars.vetting.system.persistence.SpouseRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class SpouseService implements SpouseServiceLocal{
    
    @Autowired
    private SpouseRepository spouseRepository;

    @Override
    public Spouse save(Spouse spouse) {
        return spouseRepository.save(spouse);
    }

    @Override
    public Spouse findById(Long id) {
        return spouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Spouse update(Spouse spouse) {
        return spouseRepository.save(spouse);
    }

    @Override
    public Spouse deleteById(Long id) {
        Spouse spouse = findById(id);

        if (spouse != null) {
            spouseRepository.delete(spouse);
        }
        return spouse;
    }

    @Override
    public List<Spouse> listAll() {
        return spouseRepository.findAll();
    }

    @Override
    public boolean isExist(Spouse spouse) {
        return spouseRepository.findById(spouse.getId()) != null;
    }

  

  
    
}
