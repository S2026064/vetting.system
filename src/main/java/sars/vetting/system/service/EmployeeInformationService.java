/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import sars.vetting.system.common.Gender;
import sars.vetting.system.domain.ContactDetail;
import sars.vetting.system.domain.PublicOfficer;
import sars.vetting.system.domain.Employee;

/**
 *
 * @author S2028398
 */
@Service
public class EmployeeInformationService implements EmployeeInformationServiceLocal {

    @Autowired
    private LightweightDirectoryAccessProtocolService lightweightDirectoryAccessProtocolService;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    @Override
    public Employee getEmployeeUserBySid(String sid, String userSid) {
        Employee employee = null;
        connection = null;
        stmt = null;
        rs = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.EMPLOYEE_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.GetEmployeeBySID_Vetting(?)}");
            stmt.setString(1, sid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("costCentreNumber") != null) {
                    employee = new Employee();
                    employee.setCreatedBy(userSid);
                    employee.setCreatedDate(new Date());

                    employee.setCreatedBy(userSid);
                    employee.setCreatedDate(new Date());
                    employee.setSid(sid);
                    employee.setEmployeeNumber(rs.getString("employeeNumber"));
                    employee.setFullNames(rs.getString("fullName"));
                    employee.setOrgName(rs.getString("departmentName"));
                    employee.setPositionTitle(rs.getString("jobTitle"));
                    employee.setGrade(rs.getString("grade"));
                    employee.setRegionName(rs.getString("RegionName"));
                    employee.setRegionCode(rs.getString("Region"));
                    employee.setManagerSID(rs.getString("managerSID"));
                    employee.setManagerFullName(rs.getString("managerName"));
                    employee.setIdentityNumber(rs.getString("IdNumber"));
                    employee.setDevisionName(rs.getString("devisionName"));
                    employee.setDevisionCode(rs.getString("divisionCode"));

                    if (rs.getString("Gender").equals("1")) {
                        employee.setGender(Gender.MALE.toString());
                    } else if (rs.getString("Gender").equals("2")) {
                        employee.setGender(Gender.FEMALE.toString());
                    } else {
                        employee.setGender(Gender.OTHER.toString());
                    }

                    employee.setContactDetail(new ContactDetail());
//                    employee.getContactDetail().setEmailAddress(rs.getString("emailAddress"));
                    employee.getContactDetail().setEmailAddress(lightweightDirectoryAccessProtocolService.getUserEmailAddress(employee.getSid()));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(EmployeeInformationService.class.getName() + ":" + sid).log(Level.SEVERE, null, e);
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
                Logger.getLogger(EmployeeInformationService.class.getName() + ":" + sid).log(Level.SEVERE, null, e);
            }
        }
        return employee;
    }

    @Override
    public String getEmployeeEmailAddress(String sid) {
        connection = null;
        stmt = null;
        rs = null;

        try {
            DatasourceService service = DatasourceFactory.getDatabase(ConnectionType.EMPLOYEE_DATABASE);
            connection = service.getDatasourceConnection();
            stmt = connection.prepareStatement("SELECT mail FROM [Assets].[mysap].[empaddata] WHERE empsid = ?");
            stmt.setString(1, sid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("mail") != null) {
                    return rs.getString("mail");
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(EmployeeInformationService.class.getName() + ":" + sid).log(Level.SEVERE, null, e);
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
                Logger.getLogger(EmployeeInformationService.class.getName() + ":" + sid).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    @Override
    public List<PublicOfficer> getPublicOfficer(String IdNumber, String userSid) {
        List<PublicOfficer> publicOfficers = new ArrayList<>();
        PublicOfficer publicOfficer = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Public_Officers_IdNumber(?)}");
            stmt.setString(1, IdNumber);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("PO ID Number") != null) {
                    publicOfficer = new PublicOfficer();
                    publicOfficer.setCreatedBy(userSid);
                    publicOfficer.setCreatedDate(new Date());
                    publicOfficer.setPublicOfficerAt(rs.getString("Puplic Officer At:"));
                    publicOfficer.setLinkedRefNBR(rs.getString("Linked Ref-Nbr"));
                    publicOfficer.setLinkedAccountType("Linked Account Type");
                    publicOfficer.setLinkedItTurnover(rs.getString("Linked IT-Turnover"));
                    publicOfficer.setLinkedItAssYr(rs.getString("Linked IT Ass-Yr"));
                    publicOfficer.setLinkedTotOutstDebt(rs.getString("Linked Tot Outst. Debt"));
                    publicOfficer.setLinkedTotOutstReturns(rs.getString("Linked Tot Outst. Returns"));
                    publicOfficer.setPoName(rs.getString("PO Name"));
                    publicOfficer.setPoIdNumber(rs.getString("PO ID Number"));
                    publicOfficer.setPoTaxNbr(rs.getString("PO Tax Nbr"));
                    publicOfficer.setPoPhoneNbr(rs.getString("PO Phone Nbr"));
                    publicOfficer.setPoCellNbr(rs.getString("PO Cell Nbr"));
                    publicOfficer.setImportDate(rs.getString("ImportDate"));

                    publicOfficers.add(publicOfficer);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(EmployeeInformationService.class.getName() + ":" + IdNumber).log(Level.SEVERE, null, e);
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
                Logger.getLogger(EmployeeInformationService.class.getName() + ":" + IdNumber).log(Level.SEVERE, null, e);
            }
        }
        return publicOfficers;
    }

}
