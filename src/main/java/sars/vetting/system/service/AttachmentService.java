/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.persistence.AttachmentRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class AttachmentService implements AttachmentServiceLocal {
    
    @Autowired
    private AttachmentRepository documentationRepository;
    
    @Override
    public Attachment save(Attachment documentation) {
        return documentationRepository.save(documentation);
    }
    
    @Override
    public Attachment findById(Long id) {
        return documentationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }
    
    @Override
    public Attachment update(Attachment documentation) {
        return documentationRepository.save(documentation);
    }
    
    @Override
    public Attachment deleteById(Long id) {
        Attachment attachment = findById(id);
        if (attachment != null) {
            documentationRepository.deleteById(attachment.getId());
            documentationRepository.flush();
        }
        return attachment;
    }
    
    @Override
    public List<Attachment> listAll() {
        return documentationRepository.findAll();
    }
    
    @Override
    public boolean isExist(Attachment documentation) {
        return documentationRepository.findById(documentation.getId()) != null;
    }
    
    @Override
    public void deleteAttachment(Long id) {
        documentationRepository.deleteAttachment(id);
    }
    
}
