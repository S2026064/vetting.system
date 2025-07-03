package sars.vetting.system.mb;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.vetting.system.domain.DocumentumUrl;
import sars.vetting.system.service.DocumentumUrlServiceLocal;

/**
 *
 * @author s2028398
 */
@ManagedBean
@ViewScoped
public class DocumentumUrlBean extends BaseBean<DocumentumUrl> {

    @Autowired
    private DocumentumUrlServiceLocal documentumUrlService;
    private boolean moreThanOneDocumentumUrl;

    @PostConstruct
    public void init() {
        reset().setAdd(true);
        addCollections(documentumUrlService.listAll());
        if (getCollections().isEmpty()) {
            DocumentumUrl documentumUrl = new DocumentumUrl();
            documentumUrl.setCreatedBy(getActiveUser().getSid());
            documentumUrl.setCreatedDate(new Date());
            addCollection(documentumUrl);
            addEntity(documentumUrl);
        } else {
            if (getCollections().size() > 1) {
                addWarningMessage("The can not be more than one SLA Configuration");
            } else {
                addEntity(getCollections().get(0));
            }
        }

    }

    public void save(DocumentumUrl documentumUrl) {
        if (documentumUrl.getId() != null) {
            documentumUrlService.update(documentumUrl);
            addInformationMessage("SLA Configuration was successfully updated");
        } else {
            documentumUrlService.save(documentumUrl);
            addInformationMessage("SLA Configuration was successfully saved");
        }
        reset().setAdd(true);
    }

    public void delete(DocumentumUrl documentumUrl) {
        documentumUrlService.deleteById(documentumUrl.getId());
        remove(documentumUrl);
        addInformationMessage("SLA Configuration was successfully deleted");

        reset().setAdd(true);
    }

    public void cancel(DocumentumUrl documentumUrl) {
        if (documentumUrl.getId() == null && getDocumentumUrls().contains(documentumUrl)) {
            remove(documentumUrl);
        }
        reset().setAdd(true);
    }

    public List<DocumentumUrl> getDocumentumUrls() {
        return this.getCollections();
    }

    public boolean isMoreThanOneDocumentumUrl() {
        return moreThanOneDocumentumUrl;
    }

    public void setMoreThanOneDocumentumUrl(boolean moreThanOneDocumentumUrl) {
        this.moreThanOneDocumentumUrl = moreThanOneDocumentumUrl;
    }

}
