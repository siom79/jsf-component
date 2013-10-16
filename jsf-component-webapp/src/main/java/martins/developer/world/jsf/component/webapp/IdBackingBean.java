package martins.developer.world.jsf.component.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@SessionScoped
@Named
public class IdBackingBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(IdBackingBean.class);
    private String id = "";
    private String internalId = "";

    @PostConstruct
    public void postConstruct() {
        internalId = generateId();
    }

    private String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public void onSubmit() {
        logger.info(String.format("onSubmit(): id=%s, hidden-id=%s", getId(), FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("idForm:id")));
        if(!id.equals(internalId)) {
            FacesMessage message = new FacesMessage("IDs do not match!");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("idForm", message);
        }
        internalId = generateId();
    }

    public String getId() {
        return internalId;
    }

    public void setId(String id) {
        this.id = id;
    }
}
