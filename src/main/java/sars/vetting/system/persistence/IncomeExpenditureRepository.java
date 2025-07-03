/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.vetting.system.domain.IncomeExpenditure;

/**
 *
 * @author S2026064
 */
@Repository
public interface IncomeExpenditureRepository extends JpaRepository<IncomeExpenditure, Long>{
    
}
