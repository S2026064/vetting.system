/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.IncomeExpenditure;

/**
 *
 * @author S2026064
 */
public interface IncomeExpenditureServiceLocal {
    
     public IncomeExpenditure save(IncomeExpenditure incomeExpenditure);

    public IncomeExpenditure findById(Long id);

    public IncomeExpenditure update(IncomeExpenditure incomeExpenditure);

    public IncomeExpenditure deleteById(Long id);

    public List<IncomeExpenditure> listAll();

    public boolean isExist(IncomeExpenditure incomeExpenditure);
    
   
    
    
}
