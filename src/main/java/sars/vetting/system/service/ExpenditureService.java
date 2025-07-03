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
import sars.vetting.system.domain.Expenditure;
import sars.vetting.system.persistence.ExpenditureRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class ExpenditureService implements ExpenditureServiceLocal{
    
    @Autowired
    private ExpenditureRepository expenditureRepository;

    @Override
    public Expenditure save(Expenditure expenditure) {
        return expenditureRepository.save(expenditure);
    }

    @Override
    public Expenditure findById(Long id) {
        return expenditureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Expenditure update(Expenditure expenditure) {
        return expenditureRepository.save(expenditure);
    }

    @Override
    public Expenditure deleteById(Long id) {
        Expenditure expenditure = findById(id);

        if (expenditure != null) {
            expenditureRepository.delete(expenditure);
        }
        return expenditure;
    }

    @Override
    public List<Expenditure> listAll() {
        return expenditureRepository.findAll();
    }

    @Override
    public boolean isExist(Expenditure expenditure) {
        return expenditureRepository.findById(expenditure.getId()) != null;
    }

  

  
    
}
