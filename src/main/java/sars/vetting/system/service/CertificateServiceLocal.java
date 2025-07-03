/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Attachment;
import sars.vetting.system.domain.Certificate;

/**
 *
 * @author S2026987
 */
public interface CertificateServiceLocal {

    Certificate save(Certificate certificate);

    Certificate findById(Long id);

    Certificate update(Certificate certificate);

    Certificate deleteById(Long id);

    List<Certificate> listAll();

    boolean isExist(Certificate certificate);

    void deleteAttachment(Long id);

}
