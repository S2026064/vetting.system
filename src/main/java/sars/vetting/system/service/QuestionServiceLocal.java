/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Question;

/**
 *
 * @author S2026064
 */
public interface QuestionServiceLocal {

    Question save(Question question);

    Question findById(Long id);

    Question update(Question question);

    Question deleteById(Long id);

    List<Question> listAll();

    boolean isExist(Question question);

}
