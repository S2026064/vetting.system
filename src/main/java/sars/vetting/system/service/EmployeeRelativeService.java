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
import sars.vetting.system.domain.EmployeeRelative;
import sars.vetting.system.persistence.AttachmentRepository;
import sars.vetting.system.persistence.EmployeeRelativeRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class EmployeeRelativeService implements EmployeeRelativeServiceLocal{
    
    @Autowired
    private EmployeeRelativeRepository employeeRelativeRepository;

    @Override
    public EmployeeRelative save(EmployeeRelative employeeRelative) {
        return employeeRelativeRepository.save(employeeRelative);
    }

    @Override
    public EmployeeRelative findById(Long id) {
        return employeeRelativeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public EmployeeRelative update(EmployeeRelative employeeRelative) {
        return employeeRelativeRepository.save(employeeRelative);
    }

    @Override
    public EmployeeRelative deleteById(Long id) {
        EmployeeRelative employeeRelative = findById(id);

        if (employeeRelative != null) {
            employeeRelativeRepository.delete(employeeRelative);
        }
        return employeeRelative;
    }

    @Override
    public List<EmployeeRelative> listAll() {
        return employeeRelativeRepository.findAll();
    }

    @Override
    public boolean isExist(EmployeeRelative employeeRelative) {
        return employeeRelativeRepository.findById(employeeRelative.getId()) != null;
    }

  

  
    
}
