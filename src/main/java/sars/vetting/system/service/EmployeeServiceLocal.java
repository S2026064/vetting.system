/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;

/**
 *
 * @author S2028398
 */
public interface EmployeeServiceLocal {

    public Employee save(Employee employee);

    public Employee findById(Long id);

    public Employee update(Employee user);

    public Employee deleteById(Long id);

    public List<Employee> listAll();

    public boolean isExist(String identityNumber);

    public Employee findBySid(String sid);

    public Employee findByIdentityNumber(String identityNumber);

    List<Employee> findBySidOrFirstNameOrLastName(String searchParam);

    public Employee findBySidOrEmployeeNumber(String searchParameter);

    List<Employee> findEmployeesByEmployeeRole(String description);

    Employee findByIdentityNumberOrPassportNumber(String searchParameter);

    Employee findByEmployeeRole(String description);

    List<Employee> findEmployeeByEmployeeRoleNotIn(List<EmployeeRole> employeeRole);
  
}
