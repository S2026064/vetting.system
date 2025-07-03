/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.common.ExpenditureDescription;
import sars.vetting.system.common.ExpenditureType;
import sars.vetting.system.common.IncomeType;
import sars.vetting.system.common.VettingStatus;
import sars.vetting.system.domain.AdditionalIncome;
import sars.vetting.system.domain.Expenditure;
import sars.vetting.system.domain.IncomeExpenditure;
import sars.vetting.system.domain.Vetting;
import sars.vetting.system.service.AdditionalIncomeServiceLocal;
import sars.vetting.system.service.ExpenditureServiceLocal;
import sars.vetting.system.service.IncomeExpenditureServiceLocal;

/**
 *
 * @author S2028873
 */
@ManagedBean
@ViewScoped
public class IncomeExpenditureBean extends BaseBean<IncomeExpenditure> {

    @Autowired
    private ExpenditureServiceLocal expenditureService;
    @Autowired
    private IncomeExpenditureServiceLocal incomeExpenditureService;
    @Autowired
    private AdditionalIncomeServiceLocal additionalIncomeService;

    private List<ExpenditureType> expenditureTypes;
    private List<ExpenditureDescription> expenditureDescriptions;

    private double spouseTotalIncome;
    private double employeeTotalIncome;
    private VettingStatus vettingStatus;

    public void init(Vetting vettingRecord) {
        expenditureTypes = new ArrayList<>();
        expenditureDescriptions = new ArrayList<>();
        expenditureDescriptions.addAll(Arrays.asList(ExpenditureDescription.values()));
        expenditureTypes.addAll(Arrays.asList(ExpenditureType.values()));
        setVettingStatus(vettingRecord.getVettingStatus());
        addEntity(vettingRecord.getIncomeExpenditure());
    }

    public void calculate() {
//        if (getEntity().getAdditionalIncomes().isEmpty() && getEntity().getExpenditures().isEmpty() && getEntity().getIncome().getEmployeeIncome() == null) {
//            addInformationMessage("Provide Income and Expenditures");
//            return;
//        } else {
            double additionalSummation = 0;
            double employeeSummation = 0;
            double additional = 0;
            for (Expenditure exp : getEntity().getExpenditures()) {
                if (exp.getExpenditureType().equals(ExpenditureType.OTHER_EXPENDITURE)) {
                    additionalSummation += exp.getAmount();
                } else {
                    employeeSummation += exp.getAmount();
                }
            }

            if (getEntity().getIncome().getEmployeeIncome() == 0) {
                getEntity().setEmployeeTotalIncome(0.00);
            } else {
                getEntity().setEmployeeTotalIncome(getEntity().getIncome().getEmployeeIncome() - employeeSummation);
            }
            //additional income
//        if (!getEntity().getAdditionalIncomes().isEmpty()) {
            for (AdditionalIncome expend : getEntity().getAdditionalIncomes()) {
                additional += expend.getAmount();
            }
            if (additional == 0) {
                getEntity().setSpouseTotalIncome(0.00);
            } else {
                getEntity().setSpouseTotalIncome(additional - additionalSummation);
            }
//        }
//        }
    }

    public void addExpenditureItem() {
        Expenditure localExpenditure = new Expenditure();
        if (localExpenditure.getId() == null) {
            localExpenditure = new Expenditure();
            localExpenditure.setCreatedBy(getActiveUser().getSid());
            localExpenditure.setCreatedDate(new Date());
        } else {
            localExpenditure.setUpdatedBy(getActiveUser().getSid());
            localExpenditure.setUpdatedDate(new Date());
            localExpenditure.setIncomeType(IncomeType.EMPLOYEE);
        }
        getEntity().addExpenditure(localExpenditure);
    }

    public void removeExpenditure(Expenditure expenditure) {
        if (expenditure.getId() != null) {
            getEntity().getExpenditures().remove(expenditure);
            addEntity(incomeExpenditureService.update(getEntity()));
            expenditureService.deleteById(expenditure.getId());
        } else {
            if (getEntity().getExpenditures().contains(expenditure)) {
                getEntity().removeExpenditure(expenditure);
            }
        }
        addInformationMessage("Expenditure has been successfully removed ");
    }

    public void addAdditionalIncome() {
        AdditionalIncome additinalIncome = new AdditionalIncome();
        if (additinalIncome.getId() == null) {
            additinalIncome = new AdditionalIncome();
            additinalIncome.setCreatedBy(getActiveUser().getSid());
            additinalIncome.setCreatedDate(new Date());
        } else {
            additinalIncome.setUpdatedBy(getActiveUser().getSid());
            additinalIncome.setUpdatedDate(new Date());
        }
        getEntity().addAdditinalIncome(additinalIncome);
    }

    public void removeAdditionalIncome(AdditionalIncome additionalIncome) {
        if (additionalIncome.getId() != null) {
            getEntity().getAdditionalIncomes().remove(additionalIncome);
            addEntity(incomeExpenditureService.update(getEntity()));
            additionalIncomeService.deleteById(additionalIncome.getId());
        } else {
            if (getEntity().getAdditionalIncomes().contains(additionalIncome)) {
                getEntity().removeAdditionalIncome(additionalIncome);
            }
        }
        addInformationMessage("Expenditure has been successfully removed ");
    }

    public IncomeExpenditure getIncomeExpenditure() {
        return getEntity();
    }

    public List<ExpenditureType> getExpenditureTypes() {
        return expenditureTypes;
    }

    public void setExpenditureTypes(List<ExpenditureType> expenditureTypes) {
        this.expenditureTypes = expenditureTypes;
    }

    public List<ExpenditureDescription> getExpenditureDescriptions() {
        return expenditureDescriptions;
    }

    public void setExpenditureDescriptions(List<ExpenditureDescription> expenditureDescriptions) {
        this.expenditureDescriptions = expenditureDescriptions;
    }

    public double getSpouseTotalIncome() {
        return spouseTotalIncome;
    }

    public void setSpouseTotalIncome(double spouseTotalIncome) {
        this.spouseTotalIncome = spouseTotalIncome;
    }

    public double getEmployeeTotalIncome() {
        return employeeTotalIncome;
    }

    public void setEmployeeTotalIncome(double employeeTotalIncome) {
        this.employeeTotalIncome = employeeTotalIncome;
    }

    public boolean isDisabled() {
        return !(vettingStatus.equals(VettingStatus.FORMS_ISSUED) || vettingStatus.equals(VettingStatus.REWORK));
    }

    public VettingStatus getVettingStatus() {
        return vettingStatus;
    }

    public void setVettingStatus(VettingStatus vettingStatus) {
        this.vettingStatus = vettingStatus;
    }

}
