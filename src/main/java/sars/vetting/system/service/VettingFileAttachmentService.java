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
import sars.vetting.system.domain.VettingFileAttachment;
import sars.vetting.system.persistence.VettingFileAttachmentRepository;

/**
 *
 * @author S2028398
 */
@Service
@Transactional
public class VettingFileAttachmentService implements VettingFileAttachmentServiceLocal {

    @Autowired
    private VettingFileAttachmentRepository vettingFileAttachmentRepository;

    @Override
    public VettingFileAttachment save(VettingFileAttachment attachment) {
        return vettingFileAttachmentRepository.save(attachment);
    }

    @Override
    public VettingFileAttachment findById(Long id) {
        return vettingFileAttachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public VettingFileAttachment update(VettingFileAttachment attachment) {
        return vettingFileAttachmentRepository.save(attachment);
    }

    @Override
    public VettingFileAttachment deleteById(Long id) {
        VettingFileAttachment attachment = findById(id);
        if (attachment != null) {
            vettingFileAttachmentRepository.deleteById(attachment.getId());
            vettingFileAttachmentRepository.flush();
        }
        return attachment;
    }

    @Override
    public List<VettingFileAttachment> listAll() {
        return vettingFileAttachmentRepository.findAll();
    }

    @Override
    public boolean isExist(VettingFileAttachment attachment) {
        return vettingFileAttachmentRepository.findById(attachment.getId()) != null;
    }

    @Override
    public void deleteAttachment(Long id) {
        vettingFileAttachmentRepository.deleteVettingFileAttachment(id);
    }

}
