/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sars.vetting.system.domain.CriminalCharge;

/**
 *
 * @author S2026987
 */
@Repository
public interface CriminalChargeRepository extends JpaRepository<CriminalCharge, Long> {

}
