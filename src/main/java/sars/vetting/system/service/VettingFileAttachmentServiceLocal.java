/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.service;

import java.util.List;
import sars.vetting.system.domain.VettingFileAttachment;

/**
 *
 * @author S2026987
 */
public interface VettingFileAttachmentServiceLocal {

    VettingFileAttachment save(VettingFileAttachment attachment);

    VettingFileAttachment findById(Long id);

    VettingFileAttachment update(VettingFileAttachment attachment);

    VettingFileAttachment deleteById(Long id);

    List<VettingFileAttachment> listAll();

    boolean isExist(VettingFileAttachment attachment);

    void deleteAttachment(Long id);

}
