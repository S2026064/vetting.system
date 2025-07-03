package sars.vetting.system.mb;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sars.vetting.system.domain.AdjustmentCategory;
import sars.vetting.system.domain.AdjustmentResponsibility;
import sars.vetting.system.service.AdjustmentResponsibilityServiceLocal;
import sars.vetting.system.service.AdjustmentCategoryServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class AdjustementCategoryBean extends BaseBean<AdjustmentCategory> {

    @Autowired
    private AdjustmentCategoryServiceLocal adjustmentCategoryService;
    @Autowired
    private AdjustmentResponsibilityServiceLocal adjustmentResponsibilityService;

    private Slice slices;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
        Pageable pageable = PageRequest.of(0, 10);
        slices = adjustmentCategoryService.findAll(pageable);
        addCollections(slices.toList());

    }

    public void addOrUpdate(AdjustmentCategory adjustmentCategory) {
        reset().setAdd(true);

        if (adjustmentCategory != null) {
            adjustmentCategory.setUpdatedBy(getActiveUser().getSid());
            adjustmentCategory.setUpdatedDate(new Date());
        } else {
            adjustmentCategory = new AdjustmentCategory();
            adjustmentCategory.setCreatedBy(getActiveUser().getSid());
            adjustmentCategory.setCreatedDate(new Date());

            addCollection(adjustmentCategory);
        }
        addEntity(adjustmentCategory);

    }

    public void addOrUpdateResponsibility() {
        AdjustmentResponsibility responsibility = new AdjustmentResponsibility();
        if (responsibility.getId() == null) {
            responsibility.setCreatedBy(getActiveUser().getSid());
            responsibility.setCreatedDate(new Date());
            getEntity().addResponsibilities(responsibility);
        } else {
            responsibility.setUpdatedBy(getActiveUser().getSid());
            responsibility.setUpdatedDate(new Date());
        }
    }

    public void removeResponsibility(AdjustmentResponsibility responsibility) {
        if (responsibility.getId() != null) {
            getEntity().removeResponsibilities(responsibility);
            addEntity(adjustmentCategoryService.update(getEntity()));
            adjustmentResponsibilityService.deleteById(responsibility.getId());
        } else {
            getEntity().removeResponsibilities(responsibility);
        }
        addInformationMessage("Responsibility successfully removed");
    }

    public void save(AdjustmentCategory adjustmentCategory) {
        if (adjustmentCategory.getId() != null) {
            adjustmentCategoryService.update(adjustmentCategory);
            addInformationMessage("Adjustment Category was successfully updated.");
        } else {
            adjustmentCategoryService.save(adjustmentCategory);
            addInformationMessage("Adjustment Category was successfully created.");
        }
        reset().setList(true);
    }

    public void cancel(AdjustmentCategory adjustmentCategory) {
        if (adjustmentCategory.getId() == null && getAdjustmentCategories().contains(adjustmentCategory)) {
            remove(adjustmentCategory);
        }
        reset().setList(true);
    }

    public void delete(AdjustmentCategory adjustmentCategory) {
        adjustmentCategoryService.deleteById(adjustmentCategory.getId());
        remove(adjustmentCategory);
        addInformationMessage("Adjustment Category was successfully deleted");
        reset().setList(true);
    }

    public Integer getPageNumber() {
        return slices.getNumber() + 1;
    }

    public boolean isNextPage() {
        return slices.hasNext();
    }

    public boolean isPreviousPage() {
        return slices.hasPrevious();
    }

    public void firstPageDocuments() {
        slices = adjustmentCategoryService.findAll(slices.previousOrFirstPageable());
        addCollections(slices.toList());
    }

    public void lastPageDocuments() {
        slices = adjustmentCategoryService.findAll(slices.nextOrLastPageable());
        addCollections(slices.toList());
    }

    public void nextDocuments() {
        if (slices.hasNext()) {
            slices = adjustmentCategoryService.findAll(slices.nextPageable());
            addCollections(slices.toList());
        }
    }

    public void previousDocuments() {
        if (slices.hasPrevious()) {
            slices = adjustmentCategoryService.findAll(slices.previousPageable());
            addCollections(slices.toList());
        }
    }

    public List<AdjustmentCategory> getAdjustmentCategories() {
        return this.getCollections();
    }

    public Slice getSlices() {
        return slices;
    }

    public void setSlices(Slice slices) {
        this.slices = slices;
    }

}
