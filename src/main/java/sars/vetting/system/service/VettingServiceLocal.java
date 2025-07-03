/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2026064
 */
public interface VettingServiceLocal {

    public Vetting save(Vetting vetting);

    public Vetting findById(Long id);

    public Vetting update(Vetting vetting);

    public Vetting deleteById(Long id);

    public List<Vetting> listAll();

    public boolean isExist(Vetting vetting);

    public Vetting findVettingByEmployeeSid(String sid);

    public Vetting findByIdNumber(String idNumber);

    List<Vetting> findVettingRecordsBySidAndStatus(VettingStatus vettingStatus, String sid);

    Slice<Vetting> findVettingRecordsByStatus(VettingStatus vettingStatus, Pageable pageable);

    Page<Vetting> findAll(Pageable pageable);

    List<Vetting> findByManagerSid(String managerSid);

//    Slice<Vetting> findEscalatedVettings(Pageable pageable);
    Slice<Vetting> findByVettingStatusIn(List<VettingStatus> vettingStatuses, Pageable pageable);

    Slice<Vetting> findReminderVettings(Pageable pageable);

    Slice<Vetting> findExpiredVettings(Pageable pageable);

    Slice<Vetting> findVettingBySid(String sid, Pageable pageable);

    Slice<Vetting> findVettingByStatuses(List<VettingStatus> vettingStatuses, Pageable pageable);

    Vetting findTopByVettingStatusOrderByUpdatedDateDesc(VettingStatus vettingStatus);

    boolean isVettingRecordExist(String subjectSid, List<VettingStatus> vettingStatuses);

    List<Vetting> findVettingRecordsByEmployeeSidAndStatus(List<VettingStatus> vettingStatuses, String sid);

    List<Vetting> findVettingRecordsByStatus(List<VettingStatus> vettingStatuses);

//    List<Vetting> findByVettingStatusNotIn(List<VettingStatus> vettingStatuses);
    List<Vetting> findByVettingStatus(VettingStatus vettingStatus);

    //for report perpose
    List<Vetting> findRecievedVettings(VettingStatus vettingStatus, Date startDate, Date endDate);

    List<Vetting> findCapturedVettings(Date startDate, Date endDate);

    Vetting findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus vettingStatus, String sid);

    Vetting findTopByVettingStatusAndClearanceLevelAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus vettingStatus, ClearanceLevel clearanceLevel, String sid);

    List<Vetting> findEscalatedVettings();

    List<Vetting> findByVettingStatusAndManagerSid(VettingStatus vettingStatuses, String managerSid);

}
