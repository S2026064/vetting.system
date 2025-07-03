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
import sars.vetting.system.domain.Certificate;
import sars.vetting.system.persistence.CertificateRepository;

/**
 *
 * @author S2028398
 */
@Service
@Transactional
public class CertificateService implements CertificateServiceLocal {

    @Autowired
    private CertificateRepository certificateRepository;

    @Override
    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate findById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Certificate update(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate deleteById(Long id) {
        Certificate certificate = findById(id);
        if (certificate != null) {
            certificateRepository.delete(certificate);
        }
        return certificate;
    }

    @Override
    public List<Certificate> listAll() {
        return certificateRepository.findAll();
    }

    @Override
    public boolean isExist(Certificate certificate) {
        return certificateRepository.findById(certificate.getId()) != null;
    }

    @Override
    public void deleteAttachment(Long id) {
        certificateRepository.deleteCertificate(id);
    }
}
