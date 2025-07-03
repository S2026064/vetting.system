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
import sars.vetting.system.domain.Qualification;
import sars.vetting.system.persistence.QualificationRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class QualificationService implements QualificationServiceLocal {

    @Autowired
    private QualificationRepository qualificationRepository;

    @Override
    public Qualification save(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    @Override
    public Qualification findById(Long id) {
        return qualificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Qualification update(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    @Override
    public Qualification deleteById(Long id) {
        Qualification qualification = findById(id);

        if (qualification != null) {
            qualificationRepository.delete(qualification);
        }
        return qualification;
    }

    @Override
    public List<Qualification> listAll() {
        return qualificationRepository.findAll();
    }

    @Override
    public boolean isExist(Qualification qualification) {
        return qualificationRepository.findById(qualification.getId()) != null;
    }

    @Override
    public List<Qualification> deleteAllByEmployeeId(Long empId) {
        return qualificationRepository.deleteAllByEmployeeId(empId);
    }

//    @Override
//    public void deleteByEmployeeId(List<Long> employeeIds) {
//        qualificationRepository.deleteByEmployeeId(employeeIds);
//    }
    @Override
    public void deleteByEmployeeId(Long empId) {
        qualificationRepository.deleteByEmployeeId(empId);
    }

    @Override
    public void deleteQualification(Long id) {
        qualificationRepository.deleteQualification(id);
    }
}
