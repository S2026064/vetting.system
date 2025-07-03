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
import sars.vetting.system.domain.ForeignIntelligence;
import sars.vetting.system.persistence.ForeignIntelligenceRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class ForeignIntelligenceService implements ForeignIntelligenceServiceLocal{
    
    @Autowired
    private ForeignIntelligenceRepository foreignIntelligenceRepository;

    @Override
    public ForeignIntelligence save(ForeignIntelligence foreignIntelligence) {
        return foreignIntelligenceRepository.save(foreignIntelligence);
    }

    @Override
    public ForeignIntelligence findById(Long id) {
        return foreignIntelligenceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public ForeignIntelligence update(ForeignIntelligence foreignIntelligence) {
        return foreignIntelligenceRepository.save(foreignIntelligence);
    }

    @Override
    public ForeignIntelligence deleteById(Long id) {
        ForeignIntelligence foreignIntelligence = findById(id);

        if (foreignIntelligence != null) {
            foreignIntelligenceRepository.delete(foreignIntelligence);
        }
        return foreignIntelligence;
    }

    @Override
    public List<ForeignIntelligence> listAll() {
        return foreignIntelligenceRepository.findAll();
    }

    @Override
    public boolean isExist(ForeignIntelligence foreignIntelligence) {
        return foreignIntelligenceRepository.findById(foreignIntelligence.getId()) != null;
    }

  

  
    
}
