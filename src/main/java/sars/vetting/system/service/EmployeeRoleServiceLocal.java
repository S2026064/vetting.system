/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sars.vetting.system.domain.EmployeeRole;

/**
 *
 * @author S2026064
 */
public interface EmployeeRoleServiceLocal {
    
    
     public EmployeeRole save(EmployeeRole userRole);

    public EmployeeRole findById(Long id);

    public EmployeeRole update(EmployeeRole userRole);

    public EmployeeRole deleteById(Long id);

    public List<EmployeeRole> listAll();
    
    Page<EmployeeRole> findAll(Pageable pageable);

    public boolean isExist(EmployeeRole userRole);
    
    public EmployeeRole findByDescription(String description);
    
}
