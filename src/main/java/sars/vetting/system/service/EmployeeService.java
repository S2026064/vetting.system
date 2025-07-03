package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;
import sars.vetting.system.persistence.EmployeeRepository;

/**
 *
 * @author S2028398
 */
@Service
@Transactional
public class EmployeeService implements EmployeeServiceLocal {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee deleteById(Long id) {
        Employee employee = findById(id);
        if (employee != null) {
            employeeRepository.delete(employee);
        }
        return employee;
    }

    @Override
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @Override
    public boolean isExist(String identityNumber) {
//        return employeeRepository.findByIdentityNumber(identityNumber) != null;
        return employeeRepository.findByIdentityNumberOrPassportNumber(identityNumber) != null;

    }

    @Override
    public Employee findBySid(String sid) {
        return employeeRepository.findBySid(sid);
    }

    @Override
    public Employee findByIdentityNumber(String identityNumber) {
        return employeeRepository.findByIdentityNumber(identityNumber);
    }

    @Override
    public List<Employee> findBySidOrFirstNameOrLastName(String searchParam) {
        return employeeRepository.findBySidOrFirstNameOrLastName(searchParam, searchParam);
    }

    @Override
    public List<Employee> findEmployeesByEmployeeRole(String description) {
        return employeeRepository.findEmployeesByEmployeeRole(description);
    }

    @Override
    public Employee findBySidOrEmployeeNumber(String searchParameter) {
        return employeeRepository.findBySidOrEmployeeNumber(searchParameter);
    }

    @Override
    public Employee findByIdentityNumberOrPassportNumber(String searchParameter) {
        return employeeRepository.findByIdentityNumberOrPassportNumber(searchParameter);
    }

    @Override
    public Employee findByEmployeeRole(String description) {
        return employeeRepository.findByEmployeeRole(description);
    }

    @Override
    public List<Employee> findEmployeeByEmployeeRoleNotIn(List<EmployeeRole> employeeRole) {
        return employeeRepository.findEmployeeByEmployeeRoleNotIn(employeeRole);
    }

}
