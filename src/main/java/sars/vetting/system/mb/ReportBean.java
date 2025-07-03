/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.DateUtil;
import sars.vetting.system.common.ReportType;
import sars.vetting.system.domain.Employee;
import sars.vetting.system.domain.PublicOfficer;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.mb.util.MonotoryRiskHelper;
import sars.vetting.system.mb.util.RecordCheckHelper;
import sars.vetting.system.mb.util.VettingHelper;
import sars.vetting.system.service.EmployeeServiceLocal;
import sars.vetting.system.service.NoteServiceLocal;
import sars.vetting.system.service.VettingServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class ReportBean extends BaseBean {
    
    @Autowired
    private VettingServiceLocal vettingService;
    @Autowired
    private EmployeeServiceLocal employeeService;
    @Autowired
    private NoteServiceLocal noteService;
    
    private List<ReportType> reportTypes = new ArrayList<>();
    private List<VettingHelper> vettings = new ArrayList<>();
    private List<MonotoryRiskHelper> monotoryRiskHelpers = new ArrayList<>();
    private List<RecordCheckHelper> recordCheckHelpers = new ArrayList<>();
    
    private ReportType reportType;
    
    private boolean viewMonitoringRiskReport;
    private boolean viewVettingRegisterReport;
    private boolean viewRecordChecksReport;
    
    private Date startDate;
    private Date endDate;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

//    private static final String[] columns = {"Date (Risk identified Date)", "Full Names", "ID Number", "SID Number", "Position", "Business Unit", "Region", "Office", "Clearance Required", "Clearance Issued", "Years Recommended", "Risks Identified", "Risk Category", "Date For Next Monitoring", "Comments/Feedback/Mitigation", "Monitoring", "Person responsible for monitoring risk", "Comments from SSA"};
    private static final String[] columns = {"Date (Risk identified Date)", "Full Names", "ID Number", "SID Number", "Position", "Business Unit", "Region", "Office", "Clearance Issued", "Years Recommended", "Date For Next Monitoring", "Comments/Feedback/Mitigation", "Comments from SSA"};

//    private static final String[] columnsVetting = {"SID Number", "Full Names", "ID Number", "Position", "Org Unit Name", "Grade", "Project", "Priority Project(tick if priority)", "Region", "Office", "Date Z-204 Requested to completed", "Date Z-204 Submitted", "Responsible vetting official", "Date Allocated To Vetting Official", "Date sent to QA", "Sars Recommendations", "Date Transferred To SSA", "Clearance Requested", "Clearance Issued", "Date Received From SSA", "Clearance Number Issued", "Clearance Issue Date", "Clearance Expiry Date", "Comments From SSA", "Comments From Sars", "Risks Identified"};
    private static final String[] columnsVetting = {"SID Number", "Full Names", "ID Number", "Position", "Org Unit Name", "Grade", "Project", "Priority Project(tick if priority)", "Region", "Office", "Date Z-204 Requested to completed", "Date Z-204 Submitted", "Responsible vetting official", "Date Allocated To Vetting Official", "Date sent to QA", "Sars Recommendations", "Date Transferred To SSA", "Clearance Requested", "Clearance Issued", "Clearance Number Issued", "Clearance Issue Date", "Clearance Expiry Date", "Comments From SSA", "Comments From Sars", "Risks Identified"};
//    private static final String[] columnsRecord = {"Full Names", "SID Number", "ID Number", "Citizenship", "Position", "Business Unit", "Region", "Office", "CIPRO Directors and IT Information", "Enterprise Links", "Enterprise Previous Links", "Activity Report", "Outstanding Returns", "Outstanding Debt", "Current Outstanding debt", "Summary of Previous Properties", "Candidate (New/Existing)", "Qualifications", "Credit", "Criminal", "Internal Investigation", "Vetting Status", "Income Tax", "Last Assessment", "Returns Outstanding", "Money Owed to SARS"};
    private static final String[] columnsRecord = {"Full Names", "SID Number", "ID Number", "Citizenship", "Position", "Business Unit", "Region", "Office", "CIPRO Directors and IT Information", "Enterprise Links", "Outstanding Returns", "Outstanding Debt", "Qualifications", "Credit", "Criminal", "Internal Investigation", "Vetting Status", "Income Tax", "Last Assessment", "Returns Outstanding", "Money Owed to SARS"};
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        reportTypes = Arrays.asList(ReportType.values());
    }
    
    public void viewReport() {
        
        if (this.reportType.toString().isEmpty()) {
            addWarningMessage("Please select the report type");
            return;
        }
        
        if (this.startDate == null) {
            addWarningMessage("Please select the report start date range");
            return;
        }
        
        if (this.endDate == null) {
            addWarningMessage("Please select the report end date range");
            return;
        }
        
        if (this.endDate.before(this.startDate)) {
            addWarningMessage("The end date cannot be before the starting date please correct the date range selection");
            return;
        }

        //update the time to midnight for reporting
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        endDate = calendar.getTime();
        
        switch (reportType) {
            case MONITORING_RISK_REGISTER_REPORT:
                setPanelTitleName(ReportType.MONITORING_RISK_REGISTER_REPORT.toString());
                for (Vetting vetting : vettingService.findCapturedVettings(startDate, endDate)) {
                    MonotoryRiskHelper monotoryRiskHelper = new MonotoryRiskHelper();
                    monotoryRiskHelper.setEmployeeSid(vetting.getEmployee().getSid());
                    monotoryRiskHelper.setEmployeeFullNames(vetting.getEmployee().getFullNames());
                    monotoryRiskHelper.setEmployeePositionTitle(vetting.getEmployee().getPositionTitle());
                    monotoryRiskHelper.setEmployeeIdNumber(vetting.getEmployee().getIdentityNumber());
                    monotoryRiskHelper.setBusinessUnit(vetting.getEmployee().getOrgName());
                    monotoryRiskHelper.setRiskIdentifiedDate(vetting.getCreatedDate());
                    monotoryRiskHelper.setRegion(vetting.getEmployee().getRegionName());
                    if (vetting.getClearanceLevel() != null) {
                        monotoryRiskHelper.setClearanceIssued(vetting.getClearanceLevel().toString());
                    }
                    monotoryRiskHelper.setYearsRecommendation(DateUtil.getBefore(vetting.getCreatedDate(), 1642));
                    monotoryRiskHelper.setDateNextMonitoring(DateUtil.getBefore(vetting.getCreatedDate(), 1642));
                    if (!vetting.getNotes().isEmpty() && vetting.getNotes().iterator().next().getRoleDescription().equalsIgnoreCase("Officer")) {
                        monotoryRiskHelper.setCommentSSA(noteService.findByRoleDescription("Officer").getDescription());
                    }
                    monotoryRiskHelper.setComment(vetting.getComments().size());
                    monotoryRiskHelpers.add(monotoryRiskHelper);
                }
                this.resetViews().setViewMonitoringRiskReport(true);
                break;
            case VETTING_REGISTER_REPORT:
                setPanelTitleName(ReportType.VETTING_REGISTER_REPORT.toString());
                for (Vetting vetting : vettingService.findCapturedVettings(startDate, endDate)) {
                    VettingHelper vettingHelper = new VettingHelper();
                    vettingHelper.setEmployeeSid(vetting.getEmployee().getSid());
                    vettingHelper.setEmployeeFullNames(vetting.getEmployee().getFullNames());
                    vettingHelper.setEmployeeGrade(vetting.getEmployee().getGrade());
                    vettingHelper.setEmployeePositionTitle(vetting.getEmployee().getPositionTitle());
                    vettingHelper.setEmployeeIdNumber(vetting.getEmployee().getIdentityNumber());
                    vettingHelper.setBusinessUnit(vetting.getEmployee().getOrgName());
                    vettingHelper.setRegion(vetting.getEmployee().getRegionName());
                    vettingHelper.setZ204Request(vetting.getCreatedDate());
                    vettingHelper.setZ204RequestSubmited(vetting.getZ204SubmittedDate());
                    vettingHelper.setAllocatedQA(vetting.getDateSentQa());
                    vettingHelper.setDateAllocatedOfficial(vetting.getDateSentOfficer());
                    vettingHelper.setRiskIdentified(vetting.getCreatedDate());
                    vettingHelper.setDateToSSA(vetting.getDateSentOfficer());
                    vettingHelper.setClearenceIssueDate(vetting.getGradeClearance().getCreatedDate());
                    vettingHelper.setProject(vetting.getProjectSummary());
                    vettingHelper.setPriorityProject(vetting.getProjectPrioritySummary());
                    if (!vetting.getNotes().isEmpty() && vetting.getNotes().iterator().next().getRoleDescription().equalsIgnoreCase("Analyst")) {
                        vettingHelper.setCommentFromSars(noteService.findByRoleDescription("Analyst").getDescription());
                    }
                    if (!vetting.getNotes().isEmpty() && vetting.getNotes().iterator().next().getRoleDescription().equalsIgnoreCase("Officer")) {
                        vettingHelper.setCommentFromSSA(noteService.findByRoleDescription("Officer").getDescription());
                    }
                    if (vetting.getClearanceLevel() != null) {
                        vettingHelper.setClearenceIssue(vetting.getClearanceLevel().toString());
                    }
                    if (vetting.getOfficerSid() != null) {
                        Employee employee = employeeService.findBySid(vetting.getOfficerSid());
                        vettingHelper.setResponsibleOfficer(employee.getFullNames());
                    }
                    vettingHelper.setClearenceExpiryDate(DateUtil.getBefore(vetting.getCreatedDate(), 1642));
                    vettings.add(vettingHelper);
                }
                this.resetViews().setViewVettingRegisterReport(true);
                break;
            
            case RECORD_CHECKS_REPORT:
                setPanelTitleName(ReportType.RECORD_CHECKS_REPORT.toString());
                for (Vetting vetting : vettingService.findCapturedVettings(startDate, endDate)) {
                    RecordCheckHelper checkHelper = new RecordCheckHelper();
                    checkHelper.setEmployeeSid(vetting.getEmployee().getSid());
                    checkHelper.setEmployeeFullNames(vetting.getEmployee().getFullNames());
                    checkHelper.setEmployeePositionTitle(vetting.getEmployee().getPositionTitle());
                    checkHelper.setEmployeeIdNumber(vetting.getEmployee().getIdentityNumber());
                    checkHelper.setPersonnelArea(vetting.getEmployee().getDevisionName());
                    checkHelper.setRegion(vetting.getEmployee().getRegionName());
                    checkHelper.setBusinessUnit(vetting.getEmployee().getOrgName());
                    checkHelper.setVettingStatus(vetting.getVettingStatus());
                    for (PublicOfficer officer : vetting.getEmployee().getPublicOfficerDetails()) {
                        checkHelper.setDirector(officer.getPublicOfficerAt());
                        checkHelper.setOutStandingReturn(officer.getLinkedTotOutstReturns());
                        checkHelper.setOutstandingDebt(officer.getLinkedTotOutstDebt());
                        checkHelper.setEnterpriseLink(officer.getLinkedRefNBR());
                        checkHelper.setEnterpriseLinkPrevious(officer.getLinkedItTurnover());
                    }
                    if (vetting.getScreening() != null) {
                        checkHelper.setQualification(vetting.getEmployee().getQualifications().size());
                        checkHelper.setCreditRecord(vetting.getScreening().getCreditRecords().size());
                        checkHelper.setCriminalRecord(vetting.getScreening().getCriminalRecords().size());
                        checkHelper.setInternalInvestigationRecord(vetting.getScreening().getInternalInvestigationRecords().size());
                        
                        if (vetting.getScreening().getTaxDetails() != null) {
                            checkHelper.setTaxRefNumber(vetting.getScreening().getTaxDetails().getIncomeTaxNumber());
                            checkHelper.setLastAssesment(vetting.getScreening().getTaxDetails().getLastAssesmentDate());
                            checkHelper.setReturnOustanding(vetting.getScreening().getTaxDetails().getOutstandingReturn());
                            checkHelper.setMoneyOwedSars(vetting.getScreening().getTaxDetails().getMoneyOwNedToSars());
                        }
                    }
                    recordCheckHelpers.add(checkHelper);
                }
                this.resetViews().setViewRecordChecksReport(true);
                break;
        }
        reset().setList(false);
    }
    
    public void cancel() {
        switch (reportType) {
            case RECORD_CHECKS_REPORT:
                recordCheckHelpers.clear();
                setViewRecordChecksReport(false);
                break;
            case VETTING_REGISTER_REPORT:
                vettings.clear();
                setViewVettingRegisterReport(false);
                break;
            case MONITORING_RISK_REGISTER_REPORT:
                monotoryRiskHelpers.clear();
                setViewMonitoringRiskReport(false);
                break;
        }
        reset().setList(true);
    }
    
    public ReportBean resetViews() {
        setViewMonitoringRiskReport(false);
        setViewVettingRegisterReport(false);
        setViewRecordChecksReport(false);
        return this;
    }
    
    public void generateMonitoryReport() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("MonitoryReport");
        sheet.setDefaultColumnStyle(0, workbook.createCellStyle());
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setBold(true);
        headerFont.setItalic(false);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.THICK);
        headerCellStyle.setBorderLeft(BorderStyle.THICK);
        headerCellStyle.setBorderRight(BorderStyle.THICK);
        headerCellStyle.setBorderTop(BorderStyle.THICK);
        Row headerRow = sheet.createRow(0);
        int counter = 0;
        for (String column : columns) {
            Cell cell = headerRow.createCell(counter);
            cell.setCellValue(column);
            cell.setCellStyle(headerCellStyle);
            counter++;
        }
        int rownum = 1;
        for (MonotoryRiskHelper monotoryRisk : monotoryRiskHelpers) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(dateFormat.format(monotoryRisk.getRiskIdentifiedDate()));
            row.createCell(1).setCellValue(monotoryRisk.getEmployeeFullNames());
            row.createCell(2).setCellValue(monotoryRisk.getEmployeeIdNumber());
            row.createCell(3).setCellValue(monotoryRisk.getEmployeeSid());
            row.createCell(4).setCellValue(monotoryRisk.getEmployeePositionTitle());
            row.createCell(5).setCellValue(monotoryRisk.getBusinessUnit());
            row.createCell(6).setCellValue(monotoryRisk.getRegion());
            row.createCell(7).setCellValue(monotoryRisk.getOffice());
            row.createCell(8).setCellValue(monotoryRisk.getClearanceIssued());
            row.createCell(9).setCellValue(monotoryRisk.getYearsRecommendation());
            row.createCell(10).setCellValue(monotoryRisk.getDateNextMonitoring());
            row.createCell(11).setCellValue(monotoryRisk.getComment());
            row.createCell(12).setCellValue(monotoryRisk.getCommentSSA());
        }
        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] outArray = outputStream.toByteArray();
            response.reset();
            response.setContentType("application/ms-excel");
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=Monitory Report.xls");
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
            fc.responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Monitory Report has been downloaded successfully");
        
    }
    
    public void generateVettingRegisterReport() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("VettingRegisterReport");
        sheet.setDefaultColumnStyle(0, workbook.createCellStyle());
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setBold(true);
        headerFont.setItalic(false);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.THICK);
        headerCellStyle.setBorderLeft(BorderStyle.THICK);
        headerCellStyle.setBorderRight(BorderStyle.THICK);
        headerCellStyle.setBorderTop(BorderStyle.THICK);
        Row headerRow = sheet.createRow(0);
        int counter = 0;
        for (String column : columnsVetting) {
            Cell cell = headerRow.createCell(counter);
            cell.setCellValue(column);
            cell.setCellStyle(headerCellStyle);
            counter++;
        }
        int rownum = 1;
        for (VettingHelper vetting : vettings) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(vetting.getEmployeeSid());
            row.createCell(1).setCellValue(vetting.getEmployeeFullNames());
            row.createCell(2).setCellValue(vetting.getEmployeeIdNumber());
            row.createCell(3).setCellValue(vetting.getEmployeePositionTitle());
            row.createCell(4).setCellValue(vetting.getBusinessUnit());
            row.createCell(5).setCellValue(vetting.getEmployeeGrade());
            row.createCell(6).setCellValue(vetting.getProject());
            row.createCell(7).setCellValue(vetting.getPriorityProject());
            row.createCell(8).setCellValue(vetting.getRegion());
            row.createCell(9).setCellValue(vetting.getOffice());
            if (vetting.getZ204Request() != null) {
                row.createCell(10).setCellValue(dateFormat.format(vetting.getZ204Request()));
            }
            if (vetting.getZ204RequestSubmited() != null) {
                row.createCell(11).setCellValue(dateFormat.format(vetting.getZ204RequestSubmited()));
            }
            row.createCell(12).setCellValue(vetting.getResponsibleOfficer());
            if (vetting.getDateAllocatedOfficial() != null) {
                row.createCell(13).setCellValue(dateFormat.format(vetting.getDateAllocatedOfficial()));
            }
            if (vetting.getAllocatedQA() != null) {
                row.createCell(14).setCellValue(dateFormat.format(vetting.getAllocatedQA()));
            }
            row.createCell(15).setCellValue(vetting.getRecommendation());
            if (vetting.getDateToSSA() != null) {
                row.createCell(16).setCellValue(dateFormat.format(vetting.getDateToSSA()));
            }
            row.createCell(17).setCellValue(vetting.getClearenceRequest());
            row.createCell(18).setCellValue(vetting.getClearenceIssue());
            row.createCell(19).setCellValue(vetting.getClearenceNumberIssue());
            if (vetting.getClearenceIssueDate() != null) {
                row.createCell(20).setCellValue(dateFormat.format(vetting.getClearenceIssueDate()));
            }
            row.createCell(21).setCellValue(vetting.getClearenceExpiryDate());
            row.createCell(22).setCellValue(vetting.getCommentFromSSA());
            row.createCell(23).setCellValue(vetting.getCommentFromSars());
            if (vetting.getRiskIdentified() != null) {
                row.createCell(24).setCellValue(dateFormat.format(vetting.getRiskIdentified()));
            }
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columnsVetting.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] outArray = outputStream.toByteArray();
            response.reset();
            response.setContentType("application/ms-excel");
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=Vetting Register Report.xls");
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
            fc.responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Vetting Register Report has been downloaded successfully");
        
    }
    
    public void generateRecordChecksReport() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("RecordChecksReport");
        sheet.setDefaultColumnStyle(0, workbook.createCellStyle());
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setBold(true);
        headerFont.setItalic(false);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.THICK);
        headerCellStyle.setBorderLeft(BorderStyle.THICK);
        headerCellStyle.setBorderRight(BorderStyle.THICK);
        headerCellStyle.setBorderTop(BorderStyle.THICK);
        Row headerRow = sheet.createRow(0);
        int counter = 0;
        for (String column : columnsRecord) {
            Cell cell = headerRow.createCell(counter);
            cell.setCellValue(column);
            cell.setCellStyle(headerCellStyle);
            counter++;
        }
        int rownum = 1;
        for (RecordCheckHelper checkHelper : recordCheckHelpers) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(checkHelper.getEmployeeFullNames());
            row.createCell(1).setCellValue(checkHelper.getEmployeeSid());
            row.createCell(2).setCellValue(checkHelper.getEmployeeIdNumber());
            row.createCell(3).setCellValue(checkHelper.getCitizenship());
            row.createCell(4).setCellValue(checkHelper.getEmployeePositionTitle());
            row.createCell(5).setCellValue(checkHelper.getBusinessUnit());
            row.createCell(6).setCellValue(checkHelper.getRegion());
            row.createCell(7).setCellValue(checkHelper.getOffice());
            row.createCell(8).setCellValue(checkHelper.getDirector());
            row.createCell(9).setCellValue(checkHelper.getEnterpriseLink());
//            row.createCell(10).setCellValue(checkHelper.getEnterpriseLinkPrevious()); //recheck to retrive correct
//            row.createCell(11).setCellValue(checkHelper.getOutStandingReturn()); //activity
            row.createCell(10).setCellValue(checkHelper.getOutStandingReturn());
            row.createCell(11).setCellValue(checkHelper.getOutstandingDebt());
//            row.createCell(14).setCellValue(checkHelper.getOutstandingDebt());//current debt
//            row.createCell(15).setCellValue(checkHelper.getOutstandingDebt()); //property
//            row.createCell(16).setCellValue(checkHelper.getOutstandingDebt());//candidate
            //filds co extract correct data
            if (checkHelper.getQualification() != null) {
                row.createCell(12).setCellValue(checkHelper.getQualification());
            }
            if (checkHelper.getQualification() != null) {
                row.createCell(13).setCellValue(checkHelper.getCreditRecord());
            }
            if (checkHelper.getCriminalRecord() != null) {
                row.createCell(14).setCellValue(checkHelper.getCriminalRecord());
            }
            if (checkHelper.getInternalInvestigationRecord() != null) {
                row.createCell(15).setCellValue(checkHelper.getInternalInvestigationRecord());
            }
            /////////////////////
            row.createCell(16).setCellValue(checkHelper.getVettingStatus().toString());
            row.createCell(17).setCellValue(checkHelper.getTaxRefNumber());
            row.createCell(18).setCellValue(checkHelper.getLastAssesment());
            row.createCell(19).setCellValue(checkHelper.getReturnOustanding());
            row.createCell(20).setCellValue(checkHelper.getMoneyOwedSars());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columnsRecord.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] outArray = outputStream.toByteArray();
            response.reset();
            response.setContentType("application/ms-excel");
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=Record Checks Report.xls");
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
            fc.responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addInformationMessage("Record Checks Report has been downloaded successfully");
        
    }
    
    public List<ReportType> getReportTypes() {
        return reportTypes;
    }
    
    public void setReportTypes(List<ReportType> reportTypes) {
        this.reportTypes = reportTypes;
    }
    
    public ReportType getReportType() {
        return reportType;
    }
    
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
    
    public boolean isViewMonitoringRiskReport() {
        return viewMonitoringRiskReport;
    }
    
    public void setViewMonitoringRiskReport(boolean viewMonitoringRiskReport) {
        this.viewMonitoringRiskReport = viewMonitoringRiskReport;
    }
    
    public boolean isViewVettingRegisterReport() {
        return viewVettingRegisterReport;
    }
    
    public void setViewVettingRegisterReport(boolean viewVettingRegisterReport) {
        this.viewVettingRegisterReport = viewVettingRegisterReport;
    }
    
    public boolean isViewRecordChecksReport() {
        return viewRecordChecksReport;
    }
    
    public void setViewRecordChecksReport(boolean viewRecordChecksReport) {
        this.viewRecordChecksReport = viewRecordChecksReport;
    }
    
    public List<VettingHelper> getVettings() {
        return vettings;
    }
    
    public void setVettings(List<VettingHelper> vettings) {
        this.vettings = vettings;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public List<MonotoryRiskHelper> getMonotoryRiskHelpers() {
        return monotoryRiskHelpers;
    }
    
    public void setMonotoryRiskHelpers(List<MonotoryRiskHelper> monotoryRiskHelpers) {
        this.monotoryRiskHelpers = monotoryRiskHelpers;
    }
    
    public List<RecordCheckHelper> getRecordCheckHelpers() {
        return recordCheckHelpers;
    }
    
    public void setRecordCheckHelpers(List<RecordCheckHelper> recordCheckHelpers) {
        this.recordCheckHelpers = recordCheckHelpers;
    }
    
}
