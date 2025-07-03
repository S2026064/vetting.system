/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sars.vetting.system.common.ConnectionType;
import sars.vetting.system.common.DatasourceFactory;
import sars.vetting.system.common.DatasourceService;
import sars.vetting.system.common.YesNo;
import sars.vetting.system.domain.CreditRecord;
import sars.vetting.system.domain.CriminalRecord;
import sars.vetting.system.domain.DisciplinaryRecord;
import sars.vetting.system.domain.InternalInvestigationRecord;
import sars.vetting.system.domain.Qualification;
import sars.vetting.system.domain.Screening;
import sars.vetting.system.domain.TaxDetails;

/**
 *
 * @author S2028398
 */
@Service
public class DOPIService implements DOPIServiceLocal {

    @Autowired
    private EmployeeServiceLocal employeeService;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    @Override
    public Screening getScreeningByIdNumber(String idNumber, String userSid) {
        Screening screening = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.DOPI);
            connection = datasourceService.getDatasourceConnection();
//            stmt = connection.prepareStatement("SELECT IDNumber,LastAssessment,ReturnsOutstanding,MoneyOwnedToSars,TaxStatus,ITSNumber,Status,DateModified FROM [DOPI].[dbo].[Screening] WHERE IDNumber =(?)");
            stmt = connection.prepareStatement("SELECT TOP (1) IDNumber,LastAssessment,ReturnsOutstanding,MoneyOwnedToSars,TaxStatus,ITSNumber,Status,DateModified FROM [DOPI].[dbo].[Screening] WHERE IDNumber =(?) ORDER BY [DateModified] DESC");
//            stmt = connection.prepareStatement("SELECT IDNumber,LastAssessment,ReturnsOutstanding,MoneyOwnedToSars,TaxStatus,ITSNumber,Status,DateModified FROM [DOPI].[dbo].[Screening] WHERE IDNumber= (?) AND YEAR(DateModified) = (SELECT MAX(YEAR(DateModified)) FROM [DOPI].[dbo].[Screening] WHERE IDNumber= (?))");
            stmt.setString(1, idNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("IDNumber") != null) {
                    screening = new Screening();
                    screening.setCreatedBy(userSid);
                    screening.setCreatedDate(new Date());
                    DateFormat format = new SimpleDateFormat("YYYY-M-DD");
                    Date date = (Date) format.parse(rs.getString("DateModified"));
                    screening.setLatestUpdateDate(date);

                    screening.setScreenStatus(rs.getString("Status"));

                    TaxDetails taxDetail = new TaxDetails();
                    taxDetail.setLastAssesmentDate(rs.getString("LastAssessment"));
                    taxDetail.setOutstandingReturn(rs.getString("ReturnsOutstanding"));
                    taxDetail.setMoneyOwNedToSars(rs.getString("MoneyOwnedToSars"));
                    taxDetail.setIncomeTaxNumber(rs.getString("ITSNumber"));
                    taxDetail.setStatus(rs.getString("TaxStatus"));
                    if (taxDetail.getIncomeTaxNumber() != null) {
                        taxDetail.setTaxRegistered(YesNo.YES);
                    } else {
                        taxDetail.setTaxRegistered(YesNo.NO);
                    }
                    screening.setTaxDetails(taxDetail);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
        } catch (ParseException ex) {
            Logger.getLogger(DOPIService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
            }
        }
        return screening;
    }

    @Override
    public List<Qualification> getQualificationByEmployeeIdnumber(String idNumber, String userSid) {
        List<Qualification> qualifications = new ArrayList<>();
        Qualification qualification = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.DOPI);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("SELECT IDNumber,QName,QInstitution,QYear,QVerified FROM [DOPI].[dbo].[ScreeningQualifications] WHERE IDNumber= (?) AND ModifiedDate IS NOT NULL");
//            stmt = connection.prepareStatement("SELECT IDNumber,QName,QInstitution,QYear,QVerified FROM [DOPI].[dbo].[ScreeningQualifications] WHERE IDNumber= (?) AND YEAR(ModifiedDate) = (SELECT MAX(YEAR(ModifiedDate)) FROM [DOPI].[dbo].[ScreeningQualifications] WHERE IDNumber= (?))");

            stmt.setString(1, idNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("IDNumber") != null) {
                    qualification = new Qualification();
                    qualification.setCreatedBy(userSid);
                    qualification.setCreatedDate(new Date());
                    qualification.setQualificationName(rs.getString("QName"));
                    qualification.setInstitution(rs.getString("QInstitution"));
                    qualification.setDateObtained(rs.getString("QYear"));
                    qualification.setVerification(rs.getString("QVerified"));
                    qualification.setEmployee(employeeService.findByIdentityNumber(idNumber));
                    qualifications.add(qualification);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
            }
        }
        return qualifications;
    }

    @Override
    public List<CreditRecord> getCreditRecordByEmployeeIdnumber(String idNumber, String userSid) {
        List<CreditRecord> creditRecords = new ArrayList<>();
        CreditRecord creditRecord = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.DOPI);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("SELECT IDNumber,Category,Creditor,Date,Amount,PaymentArrangements,Comment FROM [DOPI].[dbo].[ScreeningCreditRecord] WHERE IDNumber =(?)");
            stmt.setString(1, idNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("IDNumber") != null) {
                    creditRecord = new CreditRecord();
                    creditRecord.setCreatedBy(userSid);
                    creditRecord.setCreatedDate(new Date());
                    creditRecord.setCategory(rs.getString("Category"));
                    creditRecord.setCreditor(rs.getString("Creditor"));
                    creditRecord.setCreditDate(rs.getString("Date"));
                    creditRecord.setAmount(rs.getString("Amount"));
                    creditRecord.setArrangement(rs.getString("PaymentArrangements"));
                    creditRecord.setComment(rs.getString("Comment"));
                    creditRecords.add(creditRecord);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
            }
        }
        return creditRecords;
    }

    @Override
    public List<CriminalRecord> getCriminalRecordByEmployeeIdnumber(String idNumber, String userSid) {
        List<CriminalRecord> criminalRecords = new ArrayList<>();
        CriminalRecord criminalRecord = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.DOPI);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("SELECT IDNumber,Offence,CaseNo,Date,Place,Results,Comment FROM [DOPI].[dbo].[ScreeningCriminalRecord] WHERE IDNumber =(?)");
            stmt.setString(1, idNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("IDNumber") != null) {
                    criminalRecord = new CriminalRecord();
                    criminalRecord.setCreatedBy(userSid);
                    criminalRecord.setCreatedDate(new Date());
                    criminalRecord.setOffence(rs.getString("Offence"));
                    criminalRecord.setCaseNo(rs.getString("CaseNo"));
                    criminalRecord.setOffenceDate(rs.getString("Date"));
                    criminalRecord.setPlace(rs.getString("Place"));
                    criminalRecord.setResult(rs.getString("Results"));
                    criminalRecord.setComment(rs.getString("Comment"));
                    criminalRecords.add(criminalRecord);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
            }
        }
        return criminalRecords;
    }

    @Override
    public List<InternalInvestigationRecord> getInternaInvestigationByEmployeeIdnumber(String idNumber, String userSid) {
        List<InternalInvestigationRecord> internalInvestigationRecords = new ArrayList<>();
        InternalInvestigationRecord internalInvestigationRecord = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.DOPI);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("SELECT IDNumber,CaseNo,Summary,Status,Date,Comment FROM [DOPI].[dbo].[ScreeningInternaInvestigation] WHERE IDNumber =@IDNumber");
            stmt.setString(1, idNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("IDNumber") != null) {
                    internalInvestigationRecord = new InternalInvestigationRecord();
                    internalInvestigationRecord.setCreatedBy(userSid);
                    internalInvestigationRecord.setCreatedDate(new Date());
                    internalInvestigationRecord.setCaseNumber(rs.getString("CaseNo"));
                    internalInvestigationRecord.setSummary(rs.getString("Summary"));
                    internalInvestigationRecord.setInvestigationDate(rs.getString("Date"));
                    internalInvestigationRecord.setComment(rs.getString("Comment"));
                    internalInvestigationRecords.add(internalInvestigationRecord);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
            }
        }
        return internalInvestigationRecords;
    }

    @Override
    public List<DisciplinaryRecord> getDisciplinaryByEmployeeIdnumber(String idNumber, String userSid) {
        List<DisciplinaryRecord> disciplinaryRecords = new ArrayList<>();
        DisciplinaryRecord disciplinaryRecord = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.DOPI);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("SELECT IDNumber,DOO,Summary,Status,Date,Comment FROM [DOPI].[dbo].[ScreeningDisciplinary] WHERE IDNumber =(?)");
            stmt.setString(1, idNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("IDNumber") != null) {
                    disciplinaryRecord = new DisciplinaryRecord();
                    disciplinaryRecord.setCreatedBy(userSid);
                    disciplinaryRecord.setCreatedDate(new Date());
                    disciplinaryRecord.setOffenceDate(rs.getString("DOO"));
                    disciplinaryRecord.setSummary(rs.getString("Summary"));
                    disciplinaryRecord.setStatus(rs.getString("Status"));
                    disciplinaryRecord.setOutcomeDate(rs.getString("Date"));
                    disciplinaryRecord.setComment(rs.getString("Comment"));
                    disciplinaryRecords.add(disciplinaryRecord);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DOPIService.class.getName() + ":" + idNumber).log(Level.SEVERE, null, e);
            }
        }
        return disciplinaryRecords;
    }
}
