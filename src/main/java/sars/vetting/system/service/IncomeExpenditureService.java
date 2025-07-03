/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.IncomeExpenditure;
import sars.vetting.system.persistence.IncomeExpenditureRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class IncomeExpenditureService implements IncomeExpenditureServiceLocal{
    
    @Autowired
    private IncomeExpenditureRepository incomeExpenditureRepository;

    @Override
    public IncomeExpenditure save(IncomeExpenditure incomeExpenditure) {
        return incomeExpenditureRepository.save(incomeExpenditure);
    }

    @Override
    public IncomeExpenditure findById(Long id) {
        return incomeExpenditureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public IncomeExpenditure update(IncomeExpenditure incomeExpenditure) {
        return incomeExpenditureRepository.save(incomeExpenditure);
    }

    @Override
    public IncomeExpenditure deleteById(Long id) {
        IncomeExpenditure incomeExpenditure = findById(id);
        if(incomeExpenditure != null){
            incomeExpenditureRepository.delete(incomeExpenditure);
        }
        return incomeExpenditure;
    }

    @Override
    public List<IncomeExpenditure> listAll() {
        return incomeExpenditureRepository.findAll();
    }

    @Override
    public boolean isExist(IncomeExpenditure incomeExpenditure) {
        return incomeExpenditureRepository.findById(incomeExpenditure.getId()) != null;
    }

   
}
