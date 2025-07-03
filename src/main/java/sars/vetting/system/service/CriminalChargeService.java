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
import sars.vetting.system.domain.Citizenship;
import sars.vetting.system.domain.CriminalCharge;
import sars.vetting.system.domain.IllegalDrug;
import sars.vetting.system.domain.Reference;
import sars.vetting.system.persistence.CitizenShipRepository;
import sars.vetting.system.persistence.CriminalChargeRepository;
import sars.vetting.system.persistence.IllegalDrugRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class CriminalChargeService implements CriminalChargeServiceLocal {

    @Autowired
    private CriminalChargeRepository criminalChargeRepository;

    @Override
    public CriminalCharge save(CriminalCharge criminalCharge) {
        return criminalChargeRepository.save(criminalCharge);
    }

    @Override
    public CriminalCharge findById(Long id) {
        return criminalChargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public CriminalCharge update(CriminalCharge criminalCharge) {
        return criminalChargeRepository.save(criminalCharge);
    }

    @Override
    public CriminalCharge deleteById(Long id) {
        CriminalCharge criminalCharge = findById(id);

        if (criminalCharge != null) {
            criminalChargeRepository.delete(criminalCharge);
        }
        return criminalCharge;
    }

    @Override
    public List<CriminalCharge> listAll() {
        return criminalChargeRepository.findAll();
    }

    @Override
    public boolean isExist(CriminalCharge criminalCharge) {
        return criminalChargeRepository.findById(criminalCharge.getId()) != null;
    }

}
