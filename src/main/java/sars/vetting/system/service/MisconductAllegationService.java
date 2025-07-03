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
import sars.vetting.system.domain.MisconductAllegation;
import sars.vetting.system.domain.Reference;
import sars.vetting.system.persistence.CitizenShipRepository;
import sars.vetting.system.persistence.MisconductAllegationRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class MisconductAllegationService implements MisconductAllegationServiceLocal {

    @Autowired
    private MisconductAllegationRepository misconductAllegationRepository;

    @Override
    public MisconductAllegation save(MisconductAllegation misconductAllegation) {
        return misconductAllegationRepository.save(misconductAllegation);
    }

    @Override
    public MisconductAllegation findById(Long id) {
        return misconductAllegationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public MisconductAllegation update(MisconductAllegation misconductAllegation) {
        return misconductAllegationRepository.save(misconductAllegation);
    }

    @Override
    public MisconductAllegation deleteById(Long id) {
        MisconductAllegation misconductAllegation = findById(id);

        if (misconductAllegation != null) {
            misconductAllegationRepository.delete(misconductAllegation);
        }
        return misconductAllegation;
    }

    @Override
    public List<MisconductAllegation> listAll() {
        return misconductAllegationRepository.findAll();
    }

    @Override
    public boolean isExist(MisconductAllegation misconductAllegation) {
        return misconductAllegationRepository.findById(misconductAllegation.getId()) != null;
    }

}
