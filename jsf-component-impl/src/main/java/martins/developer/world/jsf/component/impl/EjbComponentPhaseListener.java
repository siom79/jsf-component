package martins.developer.world.jsf.component.impl;

import martins.developer.world.ejb.StatefulEjb;
import martins.developer.world.ejb.StatefulEjbLocal;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class EjbComponentPhaseListener implements PhaseListener {
    private static final Logger logger = LoggerFactory.getLogger(EjbComponentPhaseListener.class);

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        logger.debug("EjbComponentPhaseListener.beforePhase()");
        FacesContext facesContext = phaseEvent.getFacesContext();
        ExternalContext externalContext = facesContext.getExternalContext();
        logger.debug(externalContext.getRequestPathInfo());
        Map<String, String> requestParameter = externalContext.getRequestParameterMap();
        if (logger.isDebugEnabled()) {
            for (String parameter : requestParameter.keySet()) {
                logger.debug(parameter + ":" + requestParameter.get(parameter));
            }
        }
        String ejbComponentParameter = requestParameter.get("ejbComponent");
        if (ejbComponentParameter != null) {
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            StatefulEjbLocal ejbComponent = (StatefulEjbLocal) sessionMap.get("ejbComponent");
            if (ejbComponent != null) {
                byte[] image = ejbComponent.getImage();
                HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
                ServletOutputStream outputStream = null;
                try {
                    outputStream = response.getOutputStream();
                    IOUtils.copy(new ByteArrayInputStream(image), outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    IOUtils.closeQuietly(outputStream);
                    facesContext.responseComplete();
                }
            } else {
                logger.debug("Could not retrieve ejbComponent from session.");
            }
        } else {
            logger.debug("Request parameter not found");
        }
    }

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        logger.debug("afterPhase()");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
