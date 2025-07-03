/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.SecurityClearance;
import sars.vetting.system.persistence.SecurityClearanceRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class SecurityClearanceService implements SecurityClearanceServiceLocal {

    @Autowired
    private SecurityClearanceRepository securityClearanceRepository;

    @Override
    public SecurityClearance save(SecurityClearance securityClearance) {
        return securityClearanceRepository.save(securityClearance);

    }

    @Override
    public SecurityClearance findById(Long id) {
        return securityClearanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public SecurityClearance update(SecurityClearance securityClearance) {
        return securityClearanceRepository.save(securityClearance);
    }

    @Override
    public SecurityClearance deleteById(Long id) {
        SecurityClearance clearance = findById(id);
        
        if(clearance != null){
            securityClearanceRepository.delete(clearance);
        }
        return clearance;
    }

    @Override
    public List<SecurityClearance> listAll() {
        return securityClearanceRepository.findAll();
    }

}
