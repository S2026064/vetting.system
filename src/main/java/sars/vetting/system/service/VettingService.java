/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.persistence.VettingRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class VettingService implements VettingServiceLocal {

    @Autowired
    private VettingRepository vettingRepository;

    @Override
    public Vetting save(Vetting vetting) {
        return vettingRepository.save(vetting);
    }

    @Override
    public Vetting findById(Long id) {
        return vettingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                                "The requested id [" + id
                                + "] does not exist."));
    }

    @Override
    public Vetting update(Vetting vetting) {
        return vettingRepository.save(vetting);
    }

    @Override
    public Vetting deleteById(Long id) {
        Vetting vetting = findById(id);
        if (vetting != null) {
            vettingRepository.delete(vetting);
        }
        return vetting;
    }

    @Override
    public List<Vetting> listAll() {
        return vettingRepository.findAll();
    }

    @Override
    public boolean isExist(Vetting vetting) {
        return vettingRepository.findbyIdNumber(vetting.getEmployee().getIdentityNumber()) != null;
    }

    @Override
    public Vetting findVettingByEmployeeSid(String sid) {
        return vettingRepository.findVettingByEmployeeSid(sid);
    }

    @Override
    public List<Vetting> findVettingRecordsBySidAndStatus(VettingStatus vettingStatus, String sid) {
        return vettingRepository.findVettingRecordsBySidAndStatus(vettingStatus, sid);
    }

    @Override
    public Slice<Vetting> findVettingRecordsByStatus(VettingStatus vettingStatus, Pageable pageable) {
        return vettingRepository.findVettingRecordsByStatus(vettingStatus, pageable);
    }

    @Override
    public List<Vetting> findByManagerSid(String managerSid) {
        return vettingRepository.findByManagerSid(managerSid);
    }

    @Override
    public Slice<Vetting> findVettingBySid(String sid, Pageable pageable) {
        return vettingRepository.findVettingBySid(sid, pageable);

    }

//    @Override
//    public Slice<Vetting> findEscalatedVettings(Pageable pageable) {
//        return vettingRepository.findEscalatedVettings(pageable);
//    }
    @Override
    public Slice<Vetting> findExpiredVettings(Pageable pageable) {
        return vettingRepository.findExpiredVettings(pageable);
    }

    @Override
    public Slice<Vetting> findVettingByStatuses(List<VettingStatus> vettingStatuses, Pageable pageable) {
        return vettingRepository.findVettingByStatuses(vettingStatuses, pageable);
    }

    @Override
    public boolean isVettingRecordExist(String subjectSid, List<VettingStatus> vettingStatuses) {
        return vettingRepository.findBySubjectSidAndVettingStatusNotIn(subjectSid, vettingStatuses) != null;
    }

    @Override
    public Vetting findTopByVettingStatusOrderByUpdatedDateDesc(VettingStatus vettingStatus) {
        return vettingRepository.findTopByVettingStatusOrderByUpdatedDateDesc(vettingStatus);
    }

    @Override
    public List<Vetting> findVettingRecordsByEmployeeSidAndStatus(List<VettingStatus> vettingStatuses, String sid) {
        return vettingRepository.findVettingRecordsByEmployeeSidAndStatus(vettingStatuses, sid);
    }

    @Override
    public List<Vetting> findRecievedVettings(VettingStatus vettingStatus, Date startDate, Date endDate) {
        return vettingRepository.findRecievedVettings(vettingStatus, startDate, endDate);
    }

    @Override
    public List<Vetting> findCapturedVettings(Date startDate, Date endDate) {
        return vettingRepository.findCapturedVettings(startDate, endDate);
    }

    @Override
    public Slice<Vetting> findReminderVettings(Pageable pageable) {
        return vettingRepository.findReminderVettings(pageable);
    }

    @Override
    public Vetting findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus vettingStatus, String sid) {
        return vettingRepository.findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(vettingStatus, sid);
    }

    @Override
    public List<Vetting> findVettingRecordsByStatus(List<VettingStatus> vettingStatuses) {
        return vettingRepository.findVettingRecordsByStatus(vettingStatuses);
    }

//    @Override
//    public List<Vetting> findByVettingStatusNotIn(List<VettingStatus> vettingStatuses) {
//        return vettingRepository.findByVettingStatusNotIn(vettingStatuses);
//    }
    @Override
    public Page<Vetting> findAll(Pageable pageable) {
        return vettingRepository.findAll(pageable);
    }

    @Override
    public Slice<Vetting> findByVettingStatusIn(List<VettingStatus> vettingStatuses, Pageable pageable) {
        return vettingRepository.findByVettingStatusIn(vettingStatuses, pageable);
    }

    @Override
    public List<Vetting> findByVettingStatus(VettingStatus vettingStatus) {
        return vettingRepository.findByVettingStatus(vettingStatus);
    }

    @Override
    public List<Vetting> findEscalatedVettings() {
        return vettingRepository.findEscalatedVettings();
    }

    @Override
    public Vetting findTopByVettingStatusAndClearanceLevelAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus vettingStatus, ClearanceLevel clearanceLevel, String sid) {
        return vettingRepository.findTopByVettingStatusAndClearanceLevelAndSubjectSidNotOrderByUpdatedDateDesc(vettingStatus, clearanceLevel, sid);
    }

    @Override
    public List<Vetting> findByVettingStatusAndManagerSid(VettingStatus vettingStatus, String managerSid) {
        return vettingRepository.findByVettingStatusAndManagerSid(vettingStatus, managerSid);
    }

    @Override
    public Vetting findByIdNumber(String idNumber) {
        return vettingRepository.findbyIdNumber(idNumber);
    }

}
