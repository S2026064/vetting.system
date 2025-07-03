/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Question;
import sars.vetting.system.domain.ScreeningDeclaration;
import sars.vetting.system.persistence.QuestionRepository;
import sars.vetting.system.persistence.ScreeningDeclarationRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class ScreeningDeclarationService implements ScreeningDeclarationServiceLocal {

    @Autowired
    private ScreeningDeclarationRepository screeningDeclarationRepository;

    @Override
    public ScreeningDeclaration save(ScreeningDeclaration screeningDeclaration) {
        return screeningDeclarationRepository.save(screeningDeclaration);
    }

    @Override
    public ScreeningDeclaration findById(Long id) {
        return screeningDeclarationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public ScreeningDeclaration update(ScreeningDeclaration screeningDeclaration) {
        return screeningDeclarationRepository.save(screeningDeclaration);
    }

    @Override
    public ScreeningDeclaration deleteById(Long id) {
        ScreeningDeclaration screeningDeclaration = findById(id);
        if (screeningDeclaration != null) {
            screeningDeclarationRepository.delete(screeningDeclaration);
        }
        return screeningDeclaration;
    }

    @Override
    public List<ScreeningDeclaration> listAll() {
        return screeningDeclarationRepository.findAll();

    }

    @Override
    public boolean isExist(ScreeningDeclaration screeningDeclaration) {
        return screeningDeclarationRepository.findById(screeningDeclaration.getId()) != null;

    }

   
}
