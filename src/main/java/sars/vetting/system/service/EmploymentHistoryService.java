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
import sars.vetting.system.domain.EmploymentHistory;
import sars.vetting.system.persistence.EmploymentHistoryRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class EmploymentHistoryService implements EmploymentHistoryServiceLocal{
    
    @Autowired
    private EmploymentHistoryRepository employmentHistoryRepository;

    @Override
    public EmploymentHistory save(EmploymentHistory employmentHistory) {
        return employmentHistoryRepository.save(employmentHistory);
    }

    @Override
    public EmploymentHistory findById(Long id) {
        return employmentHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public EmploymentHistory update(EmploymentHistory employmentHistory) {
        return employmentHistoryRepository.save(employmentHistory);
    }

    @Override
    public EmploymentHistory deleteById(Long id) {
        EmploymentHistory employmentHistory = findById(id);

        if (employmentHistory != null) {
            employmentHistoryRepository.delete(employmentHistory);
        }
        return employmentHistory;
    }

    @Override
    public List<EmploymentHistory> listAll() {
        return employmentHistoryRepository.findAll();
    }

    @Override
    public boolean isExist(EmploymentHistory employmentHistory) {
        return employmentHistoryRepository.findById(employmentHistory.getId()) != null;
    }

  

  
    
}
