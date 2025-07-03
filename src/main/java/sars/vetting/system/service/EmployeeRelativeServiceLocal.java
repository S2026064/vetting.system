/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.EmployeeRelative;

/**
 *
 * @author S2026987
 */
public interface EmployeeRelativeServiceLocal {
    
    EmployeeRelative save(EmployeeRelative employeeRelative);

    EmployeeRelative findById(Long id);

    EmployeeRelative update(EmployeeRelative employeeRelative);

    EmployeeRelative deleteById(Long id);

    List<EmployeeRelative> listAll();

    boolean isExist(EmployeeRelative employeeRelative);
    
  
   
    
}
