/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.AdditionalIncome;

/**
 *
 * @author S2026064
 */
public interface AdditionalIncomeServiceLocal {

    public AdditionalIncome save(AdditionalIncome additionalIncome);

    public AdditionalIncome findById(Long id);

    public AdditionalIncome update(AdditionalIncome additionalIncome);

    public AdditionalIncome deleteById(Long id);

    public List<AdditionalIncome> listAll();

    public boolean isExist(AdditionalIncome additionalIncome);

}
