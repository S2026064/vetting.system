/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.DocumentumUrl;
import sars.vetting.system.persistence.DocumentumUrlRepository;

/**
 *
 * @author S2028873
 */
@Service
@Transactional
public class DocumentumUrlService implements DocumentumUrlServiceLocal {

    @Autowired
    private DocumentumUrlRepository documentumUrlRepository;   

    @Override
    public DocumentumUrl save(DocumentumUrl documentumUrl) {
        return documentumUrlRepository.save(documentumUrl);
    }

    @Override
    public DocumentumUrl findById(Long id) {
        return documentumUrlRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public DocumentumUrl update(DocumentumUrl documentumUrl) {
        return  documentumUrlRepository.save(documentumUrl);
    }

    @Override
    public DocumentumUrl deleteById(Long id) {
         DocumentumUrl documentumUrl = findById(id);

        if (documentumUrl != null) {
            documentumUrlRepository.delete(documentumUrl);
        }
        return documentumUrl;
    }

    @Override
    public List<DocumentumUrl> listAll() {
        return documentumUrlRepository.findAll();
    }

    @Override
    public boolean isExist(DocumentumUrl documentumUrl) {
        return documentumUrlRepository.findById(documentumUrl.getId()) != null;
    }

    @Override
    public DocumentumUrl findFirstByOrderByCreatedDateDesc() {
        return documentumUrlRepository.findFirstByOrderByCreatedDateDesc();
        }


}
