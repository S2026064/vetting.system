/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.DocumentumUrl;

/**
 *
 * @author S2028873
 */
public interface DocumentumUrlServiceLocal {

    DocumentumUrl save(DocumentumUrl documentumUrl);

    DocumentumUrl findById(Long id);

    DocumentumUrl findFirstByOrderByCreatedDateDesc();

    DocumentumUrl update(DocumentumUrl documentumUrl);

    DocumentumUrl deleteById(Long id);

    List<DocumentumUrl> listAll();

    boolean isExist(DocumentumUrl documentumUrl);

}
