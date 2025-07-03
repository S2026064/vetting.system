/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Qualification;

/**
 *
 * @author S2026987
 */
public interface QualificationServiceLocal {

    Qualification save(Qualification qualification);

    Qualification findById(Long id);

    Qualification update(Qualification qualification);

    Qualification deleteById(Long id);

    List<Qualification> listAll();

    boolean isExist(Qualification qualification);

    List<Qualification> deleteAllByEmployeeId(Long empId);
    
//    void deleteByEmployeeId(List<Long> employeeIds);
      void deleteByEmployeeId(Long empId);
      
      void deleteQualification(Long id);

}
