/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.PreviousName;
import sars.vetting.system.persistence.PreviousNameRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class PreviousNameService implements PreviousNameServiceLocal{
        
    @Autowired
    private PreviousNameRepository previousNameRepository;

    @Override
    public PreviousName save(PreviousName previousName) {
        return previousNameRepository.save(previousName);
    }

    @Override
    public PreviousName findById(Long id) {
        return previousNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public PreviousName update(PreviousName previousName) {
        return previousNameRepository.save(previousName);
    }

    @Override
    public PreviousName deleteById(Long id) {
        PreviousName previousName = findById(id);
        if(previousName != null){
            previousNameRepository.delete(previousName);
        }
        return  previousName;
    }

    @Override
    public List<PreviousName> listAll() {
        return previousNameRepository.findAll();
    }

}
