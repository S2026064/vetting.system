/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.persistence.EmployeeRoleRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class EmployeeRoleService implements EmployeeRoleServiceLocal {

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Override
    public EmployeeRole save(EmployeeRole employeeRole) {
        return employeeRoleRepository.save(employeeRole);
    }

    @Override
    public EmployeeRole findById(Long id) {
        return employeeRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public EmployeeRole update(EmployeeRole employeeRole) {
        return employeeRoleRepository.save(employeeRole);
    }

    @Override
    public EmployeeRole deleteById(Long id) {
        EmployeeRole employeeRole = findById(id);

        if (employeeRole != null) {
            employeeRoleRepository.delete(employeeRole);
        }
        return employeeRole;
    }

    @Override
    public List<EmployeeRole> listAll() {
        return employeeRoleRepository.findAll();
    }

    @Override
    public boolean isExist(EmployeeRole employeeRole) {
        return employeeRoleRepository.findById(employeeRole.getId()) != null;
    }

    @Override
    public EmployeeRole findByDescription(String description) {
        return employeeRoleRepository.findByDescription(description);
    }

    @Override
    public Page<EmployeeRole> findAll(Pageable pageable) {
        return employeeRoleRepository.findAll(pageable);
    }

}
