/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.persistence;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.vetting.system.common.ClearanceLevel;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.Vetting;

/**
 *
 * @author S2026064
 */
@Repository
public interface VettingRepository extends JpaRepository<Vetting, Long> {

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus =:status")
    Slice<Vetting> findVettingRecordsByStatus(@Param("status") VettingStatus vettingStatus, Pageable pageable);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus IN (:vettingStatus)")
    List<Vetting> findVettingRecordsByStatus(@Param("vettingStatus") List<VettingStatus> vettingStatuses);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus=:vettingStatus and (e.vettingProcessingOfficer.sid=:sid)")
    List<Vetting> findVettingRecordsBySidAndStatus(@Param("vettingStatus") VettingStatus vettingStatus, @Param("sid") String sid);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus IN (:vettingStatus) and e.employee.sid=:sid")
    List<Vetting> findVettingRecordsByEmployeeSidAndStatus(@Param("vettingStatus") List<VettingStatus> vettingStatuses, @Param("sid") String sid);

    @Query("SELECT e FROM Vetting e WHERE e.employee.managerSID =:sid")
    List<Vetting> findByManagerSid(@Param("sid") String managerSid);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus=:vettingStatus and e.employee.managerSID =:sid")
    List<Vetting> findByVettingStatusAndManagerSid(@Param("vettingStatus") VettingStatus vettingStatuses, @Param("sid") String managerSid);

    @Query("SELECT e FROM Vetting e WHERE e.employee.identityNumber =:idNumber")
    Vetting findbyIdNumber(@Param("idNumber") String idNumber);

    @Query("SELECT e FROM Vetting e WHERE e.employee.sid =:sid")
    Vetting findVettingByEmployeeSid(@Param("sid") String sid);

    @Query("SELECT e FROM Vetting e WHERE (e.vettingStatus='FORMS_ISSUED' OR e.vettingStatus='FORMS_SUBMITTED' OR e.vettingStatus='SARS_PROCESSING' OR e.vettingStatus='SSA_PROCESSING' OR e.vettingStatus='SENT_TO_QA' OR e.vettingStatus='CLEARANCE_ISSUED' OR e.vettingStatus='CLEARANCE_DENIED') AND ((GETDATE()  > DATEADD(DAY,30,e.createdDate)) OR (GETDATE()  > DATEADD(DAY,30,e.updatedDate)))")
    List<Vetting> findEscalatedVettings();
//    Slice<Vetting> findEscalatedVettings(Pageable pageable);

//    @Query("SELECT e FROM Vetting e WHERE (e.vettingStatus='FORMS_SUBMITTED' OR e.vettingStatus='SARS_PROCESSING' OR e.vettingStatus='SSA_PROCESSING' OR e.vettingStatus='SENT_TO_QA' OR e.vettingStatus='CLEARANCE_ISSUED' OR e.vettingStatus='CLEARANCE_DENIED')")
//    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus IN (:vettingStatus)")@Query("SELECT e FROM Vetting e WHERE (e.vettingStatus <> 'REWORK' OR e.vettingStatus <> 'FORMS_ISSUED' OR e.vettingStatus <> 'ADDITIONAL_DOCUMENT')")
    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus IN (:vettingStatus)")
    Slice<Vetting> findByVettingStatusIn(@Param("vettingStatus") List<VettingStatus> vettingStatuses, Pageable pageable);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus='FORMS_ISSUED' AND ((GETDATE()  > DATEADD(DAY,5,e.createdDate)) OR (GETDATE()  > DATEADD(DAY, 5,e.updatedDate)))")
    Slice<Vetting> findReminderVettings(Pageable pageable);

    @Query("SELECT e FROM Vetting e WHERE ((GETDATE()  > DATEADD(DAY,1642,e.createdDate)))")
    Slice<Vetting> findExpiredVettings(Pageable pageable);

    @Query("SELECT e FROM Vetting e WHERE e.createdBy =:sid OR e.analystSid =:sid OR e.officerSid =:sid OR e.qaSid =:sid OR e.firstApproverSid =:sid OR  e.subjectSid =:sid")
    Slice<Vetting> findVettingBySid(@Param("sid") String sid, Pageable pageable);

//    @Query("SELECT e FROM Vetting e WHERE e.firstApproverSid =:sid OR e.")
//    Slice<Vetting> findVettingBySid(@Param("sid") String sid, Pageable pageable);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus IN (:vettingStatus)")
    Slice<Vetting> findVettingByStatuses(@Param("vettingStatus") List<VettingStatus> vettingStatuses, Pageable pageable);

    @Query("SELECT e FROM Vetting e WHERE e.vettingStatus=:vettingStatus AND (e.createdDate>=:startDate AND e.createdDate<=:endDate)")
    List<Vetting> findRecievedVettings(@Param("vettingStatus") VettingStatus vettingStatus, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT e FROM Vetting e WHERE e.createdDate>=:startDate AND e.createdDate<=:endDate")
    List<Vetting> findCapturedVettings(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

//     @Query("SELECT e FROM Vetting e WHERE (e.vettingStatus='APPROVED' OR e.vettingStatus='VETTING_DENIED') AND e.createdDate>=:startDate AND e.createdDate<=:endDate")
//    List<Vetting> findCapturedVettings(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    Vetting findTopByVettingStatusOrderByUpdatedDateDesc(VettingStatus vettingStatus);

    Vetting findTopByVettingStatusAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus vettingStatus, String sid);

    Vetting findTopByVettingStatusAndClearanceLevelAndSubjectSidNotOrderByUpdatedDateDesc(VettingStatus vettingStatus, ClearanceLevel clearanceLevel, String sid);

    Vetting findBySubjectSidAndVettingStatusNotIn(String subjectSid, List<VettingStatus> vettingStatuses);

//    List<Vetting> findByVettingStatusNotIn(List<VettingStatus> vettingStatuses);
    List<Vetting> findByVettingStatus(VettingStatus vettingStatus);
}
