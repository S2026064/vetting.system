/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.AdditionalIncome;
import sars.vetting.system.persistence.AdditionalIncomeRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class AdditionalIncomeService implements AdditionalIncomeServiceLocal {

    @Autowired
    private AdditionalIncomeRepository additionalIncomeRepository;

    @Override
    public AdditionalIncome save(AdditionalIncome incomeExpenditure) {
        return additionalIncomeRepository.save(incomeExpenditure);
    }

    @Override
    public AdditionalIncome findById(Long id) {
        return additionalIncomeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public AdditionalIncome update(AdditionalIncome additionalIncome) {
        return additionalIncomeRepository.save(additionalIncome);
    }

    @Override
    public AdditionalIncome deleteById(Long id) {
        AdditionalIncome incomeExpenditure = findById(id);
        if (incomeExpenditure != null) {
            additionalIncomeRepository.delete(incomeExpenditure);
        }
        return incomeExpenditure;
    }

    @Override
    public List<AdditionalIncome> listAll() {
        return additionalIncomeRepository.findAll();
    }

    @Override
    public boolean isExist(AdditionalIncome additionalIncome) {
        return additionalIncomeRepository.findById(additionalIncome.getId()) != null;
    }

}
