/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.EmployeeRole;

/**
 *
 * @author S2028398
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findBySid(String sid);

    Employee findByIdentityNumber(String identityNumber);
    
    Employee findByPassportNumber(String passportNumber);

    @Query("SELECT e FROM Employee e WHERE e.sid LIKE %:sid% OR e.fullNames LIKE %:fullnames%")
    List<Employee> findBySidOrFirstNameOrLastName(@Param("sid") String sid, @Param("fullnames") String fullnames);

    @Query("SELECT e FROM Employee e WHERE e.sid=:searchParameter OR e.employeeNumber=:searchParameter")
    Employee findBySidOrEmployeeNumber(@Param("searchParameter") String searchParameter);
    
    @Query("SELECT e FROM Employee e WHERE e.identityNumber=:searchParameter OR e.passportNumber=:searchParameter")
    Employee findByIdentityNumberOrPassportNumber(@Param("searchParameter") String searchParameter);

    @Query("SELECT e FROM Employee e WHERE e.employeeRole.description =:descript")
    List<Employee> findEmployeesByEmployeeRole(@Param("descript") String description);
    
    @Query("SELECT e FROM Employee e WHERE e.employeeRole.description =:descript")
    Employee findByEmployeeRole(@Param("descript") String description);
    
    List<Employee> findEmployeeByEmployeeRoleNotIn(List<EmployeeRole> employeeRole);
}
