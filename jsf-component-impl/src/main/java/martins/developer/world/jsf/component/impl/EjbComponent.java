package martins.developer.world.jsf.component.impl;

import martins.developer.world.ejb.StatefulEjb;
import martins.developer.world.ejb.StatefulEjbLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@FacesComponent("EjbComponent")
public class EjbComponent extends UIOutput {
    private static final Logger logger = LoggerFactory.getLogger(EjbComponent.class);
    private static final String COMPONENT_FAMILY = "martins.developer.world.jsf.component.helloWorld";

    private enum PropertyKeys {
        statefulEjb
    };

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext facesContext) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        String imageSrc = createImageUrl(facesContext);
        writer.startElement("img", this);
        writer.writeAttribute("src", imageSrc, "");
        writer.endElement("img");
        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("ejbComponent", getStatefulEjb());
        logger.debug("Putting ejbComponent into session: "+getStatefulEjb());
    }

    private String createImageUrl(FacesContext context) {
        StringBuilder builder = new StringBuilder(context.getExternalContext().getRequestContextPath());
        if (builder.indexOf("?") == -1) {
            builder.append('?');
        } else {
            builder.append('&');
        }
        builder.append("ejbComponent").append("=").append("ejbComponent");
        builder.append("&uuid").append("=").append(UUID.randomUUID());
        return builder.toString();
    }

    public StatefulEjbLocal getStatefulEjb() {
        return (StatefulEjbLocal) getStateHelper().eval(PropertyKeys.statefulEjb);
    }

    public void setStatefulEjb(StatefulEjbLocal statefulEjb) {
        getStateHelper().put(PropertyKeys.statefulEjb, statefulEjb);
    }
}
