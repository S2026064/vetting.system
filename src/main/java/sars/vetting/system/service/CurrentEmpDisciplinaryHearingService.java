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
import sars.vetting.system.domain.CurrentEmpDisciplinaryHearing;
import sars.vetting.system.domain.IllegalDrug;
import sars.vetting.system.domain.Reference;
import sars.vetting.system.persistence.CitizenShipRepository;
import sars.vetting.system.persistence.CriminalChargeRepository;
import sars.vetting.system.persistence.CurrentEmpDisciplinaryHearingRepository;
import sars.vetting.system.persistence.IllegalDrugRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class CurrentEmpDisciplinaryHearingService implements CurrentEmpDisciplinaryHearingServiceLocal {

    @Autowired
    private CurrentEmpDisciplinaryHearingRepository currentEmpDisciplinaryHearingRepository;

    @Override
    public CurrentEmpDisciplinaryHearing save(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing) {
        return currentEmpDisciplinaryHearingRepository.save(currentEmpDisciplinaryHearing);
    }

    @Override
    public CurrentEmpDisciplinaryHearing findById(Long id) {
        return currentEmpDisciplinaryHearingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public CurrentEmpDisciplinaryHearing update(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing) {
        return currentEmpDisciplinaryHearingRepository.save(currentEmpDisciplinaryHearing);
    }

    @Override
    public CurrentEmpDisciplinaryHearing deleteById(Long id) {
        CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing = findById(id);

        if (currentEmpDisciplinaryHearingRepository != null) {
            currentEmpDisciplinaryHearingRepository.delete(currentEmpDisciplinaryHearing);
        }
        return currentEmpDisciplinaryHearing;
    }

    @Override
    public List<CurrentEmpDisciplinaryHearing> listAll() {
        return currentEmpDisciplinaryHearingRepository.findAll();
    }

    @Override
    public boolean isExist(CurrentEmpDisciplinaryHearing currentEmpDisciplinaryHearing) {
        return currentEmpDisciplinaryHearingRepository.findById(currentEmpDisciplinaryHearing.getId()) != null;
    }

}
