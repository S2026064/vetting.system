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
import sars.vetting.system.domain.VisitedCountry;
import sars.vetting.system.persistence.VisitedCountryRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class VisitedCountryService implements VisitedCountryServiceLocal{
    
    @Autowired
    private VisitedCountryRepository visitedCountryRepository;

    @Override
    public VisitedCountry save(VisitedCountry visitedCountry) {
        return visitedCountryRepository.save(visitedCountry);
    }

    @Override
    public VisitedCountry findById(Long id) {
        return visitedCountryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public VisitedCountry update(VisitedCountry visitedCountry) {
        return visitedCountryRepository.save(visitedCountry);
    }

    @Override
    public VisitedCountry deleteById(Long id) {
        VisitedCountry visitedCountry = findById(id);

        if (visitedCountry != null) {
            visitedCountryRepository.delete(visitedCountry);
        }
        return visitedCountry;
    }

    @Override
    public List<VisitedCountry> listAll() {
        return visitedCountryRepository.findAll();
    }

    @Override
    public boolean isExist(VisitedCountry visitedCountry) {
        return visitedCountryRepository.findById(visitedCountry.getId()) != null;
    }

  

  
    
}
