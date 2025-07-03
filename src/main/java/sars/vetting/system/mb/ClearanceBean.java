/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author S2026064
 */
@ManagedBean
@ViewScoped
public class ClearanceBean extends BaseBean<Object> {


    @ManagedProperty(value = "#{SubAreaBean}")
    private SubAreaBean subAreaBean;
    @ManagedProperty(value = "#{JobRoleBean}")
    private JobRoleBean jobAreaBean;
    @ManagedProperty(value = "#{AdjustmentCategoryBean}")
    private AdjustementCategoryBean adjustmentCategoryBean;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
    }

    public void loadClearanceForm() {
        subAreaBean.init();
        jobAreaBean.init();
        adjustmentCategoryBean.init();
       
    }


    public SubAreaBean getSubAreaBean() {
        return subAreaBean;
    }

    public void setSubAreaBean(SubAreaBean subAreaBean) {
        this.subAreaBean = subAreaBean;
    }

    public JobRoleBean getJobAreaBean() {
        return jobAreaBean;
    }

    public void setJobAreaBean(JobRoleBean jobAreaBean) {
        this.jobAreaBean = jobAreaBean;
    }

    public AdjustementCategoryBean getAdjustmentCategoryBean() {
        return adjustmentCategoryBean;
    }

    public void setAdjustmentCategoryBean(AdjustementCategoryBean adjustmentCategoryBean) {
        this.adjustmentCategoryBean = adjustmentCategoryBean;
    }

  

}
