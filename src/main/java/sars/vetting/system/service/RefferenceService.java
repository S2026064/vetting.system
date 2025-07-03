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
import sars.vetting.system.domain.Reference;
import sars.vetting.system.persistence.ReferenceRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class RefferenceService implements ReferenceServiceLocal{
    
    @Autowired
    private ReferenceRepository referenceRepository;

    @Override
    public Reference save(Reference reference) {
        return referenceRepository.save(reference);
    }

    @Override
    public Reference findById(Long id) {
        return referenceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Reference update(Reference reference) {
        return referenceRepository.save(reference);
    }

    @Override
    public Reference deleteById(Long id) {
        Reference reference = findById(id);

        if (reference != null) {
            referenceRepository.delete(reference);
        }
        return reference;
    }

    @Override
    public List<Reference> listAll() {
        return referenceRepository.findAll();
    }

    @Override
    public boolean isExist(Reference reference) {
        return referenceRepository.findById(reference.getId()) != null;
    }

  

  
    
}
