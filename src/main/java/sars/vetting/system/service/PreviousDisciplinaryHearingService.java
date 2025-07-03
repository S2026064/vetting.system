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
import sars.vetting.system.domain.PreviousDisciplinaryHearing;
import sars.vetting.system.persistence.PreviousDisciplinaryHearingRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class PreviousDisciplinaryHearingService implements PreviousDisciplinaryHearingServiceLocal {

    @Autowired
    private PreviousDisciplinaryHearingRepository previousDisciplinaryHearingRepository;

    @Override
    public PreviousDisciplinaryHearing save(PreviousDisciplinaryHearing previousDisciplinaryHearing) {
        return previousDisciplinaryHearingRepository.save(previousDisciplinaryHearing);
    }

    @Override
    public PreviousDisciplinaryHearing findById(Long id) {
        return previousDisciplinaryHearingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public PreviousDisciplinaryHearing update(PreviousDisciplinaryHearing previousDisciplinaryHearing) {
        return previousDisciplinaryHearingRepository.save(previousDisciplinaryHearing);
    }

    @Override
    public PreviousDisciplinaryHearing deleteById(Long id) {
        PreviousDisciplinaryHearing previousDisciplinaryHearing = findById(id);

        if (previousDisciplinaryHearing != null) {
            previousDisciplinaryHearingRepository.delete(previousDisciplinaryHearing);
        }
        return previousDisciplinaryHearing;
    }

    @Override
    public List<PreviousDisciplinaryHearing> listAll() {
        return previousDisciplinaryHearingRepository.findAll();
    }

    @Override
    public boolean isExist(PreviousDisciplinaryHearing previousDisciplinaryHearing) {
        return previousDisciplinaryHearingRepository.findById(previousDisciplinaryHearing.getId()) != null;
    }

}
