/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.EmploymentHistory;

/**
 *
 * @author S2026987
 */
public interface EmploymentHistoryServiceLocal {

    EmploymentHistory save(EmploymentHistory employmentHistory);

    EmploymentHistory findById(Long id);

    EmploymentHistory update(EmploymentHistory employmentHistory);

    EmploymentHistory deleteById(Long id);

    List<EmploymentHistory> listAll();

    boolean isExist(EmploymentHistory employmentHistory);

}
