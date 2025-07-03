/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.Attachment;

/**
 *
 * @author S2026987
 */
public interface AttachmentServiceLocal {

    Attachment save(Attachment documentation);

    Attachment findById(Long id);

    Attachment update(Attachment documentation);

    Attachment deleteById(Long id);

    List<Attachment> listAll();

    boolean isExist(Attachment documentation);

    void deleteAttachment(Long id);

}
