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
import sars.vetting.system.domain.Area;
import sars.vetting.system.domain.SubArea;
import sars.vetting.system.service.AreaServiceLocal;
import sars.vetting.system.service.SubAreaServiceLocal;

/**
 *
 * @author S2028398
 */
@ManagedBean
@ViewScoped
public class SubAreaBean extends BaseBean<SubArea> {
    
    @Autowired
    private SubAreaServiceLocal subAreaService;
    @Autowired
    private AreaServiceLocal areaService;
    
    private Slice slices;
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
        reset().setList(true);
//        Pageable pageable = PageRequest.of(0, 10);
//        slices = subAreaService.findAll(pageable);
//        addCollections(slices.toList());
        addCollections(subAreaService.listAll());
    }
    
    public void addOrUpdate(SubArea SubArea) {
        reset().setAdd(true);
        if (SubArea != null) {
            SubArea.setUpdatedBy(getActiveUser().getSid());
            SubArea.setUpdatedDate(new Date());
        } else {
            SubArea = new SubArea();
            SubArea.setCreatedBy(getActiveUser().getSid());
            SubArea.setCreatedDate(new Date());
            addCollection(SubArea);
        }
        addEntity(SubArea);
    }
    
    public void addOrUpdateArea() {
        Area area = new Area();
        if (area.getId() == null) {
            area.setCreatedBy(getActiveUser().getSid());
            area.setCreatedDate(new Date());
            getEntity().addAreas(area);
        } else {
            area.setUpdatedBy(getActiveUser().getSid());
            area.setUpdatedDate(new Date());
        }
    }
    
    public void removeArea(Area area) {
        if (area.getId() != null) {
            getEntity().removeAreas(area);
            addEntity(subAreaService.update(getEntity()));
            areaService.deleteById(area.getId());
        } else {
            getEntity().removeAreas(area);
        }
        addInformationMessage("Area successfully removed");
    }
    
    public void save(SubArea subArea) {
        if (subArea.getId() != null) {
            subAreaService.update(subArea);
            addInformationMessage("Sub-Area was successfully updated.");
        } else {
            subAreaService.save(subArea);
            addInformationMessage("Sub-Area was successfully created.");
        }
        reset().setList(true);
    }
    
    public void cancel(SubArea subArea) {
        if (subArea.getId() == null && getSubAreas().contains(subArea)) {
            remove(subArea);
        }
        reset().setList(true);
    }
    
    public void delete(SubArea subArea) {
        subAreaService.deleteById(subArea.getId());
        remove(subArea);
        addInformationMessage("Sub-Area was successfully deleted");
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
        slices = subAreaService.findAll(slices.previousOrFirstPageable());
        addCollections(slices.toList());
    }
    
    public void lastPageDocuments() {
        slices = subAreaService.findAll(slices.nextOrLastPageable());
        addCollections(slices.toList());
    }
    
    public void nextDocuments() {
        if (slices.hasNext()) {
            slices = subAreaService.findAll(slices.nextPageable());
            addCollections(slices.toList());
        }
    }
    
    public void previousDocuments() {
        if (slices.hasPrevious()) {
            slices = subAreaService.findAll(slices.previousPageable());
            addCollections(slices.toList());
        }
    }
    
    public List<SubArea> getSubAreas() {
        return this.getCollections();
    }
    
}
