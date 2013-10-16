package martins.developer.world.jsf.component.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 6869530953927351269L;
	private static final Logger logger = LoggerFactory.getLogger(ChartPhaseListener.class);

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		Map<String, String> requestParameter = facesContext.getExternalContext().getRequestParameterMap();
		if (logger.isDebugEnabled()) {
			for (String parameter : requestParameter.keySet()) {
				logger.debug(parameter + ":" + requestParameter.get(parameter));
			}
		}
		String mdwChartComponent = requestParameter.get(ChartComponent.MDW_CHART_COMPONENT);
		if (mdwChartComponent != null) {
			//renderResponse(facesContext);
		}
	}

	private void renderResponse(FacesContext facesContext) {
		try {
			InputStream resource = ChartPhaseListener.class.getClassLoader().getResourceAsStream("jsf-logo.jpg");
			if (resource != null) {
				HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
				ServletOutputStream outputStream = response.getOutputStream();
				IOUtils.copy(resource, outputStream);
				outputStream.flush();
				outputStream.close();
				resource.close();
			} else {
				logger.warn("Could not load logo file from classpath.");
			}
		} catch (IOException e) {
			logger.error("Rendering response failed: " + e.getMessage());
		}
		facesContext.responseComplete();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		logger.debug("beforePhase()");
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
