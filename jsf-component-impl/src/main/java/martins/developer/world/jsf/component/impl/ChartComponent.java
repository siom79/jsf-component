package martins.developer.world.jsf.component.impl;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent("Chart")
public class ChartComponent extends UIOutput {
	public static final String MDW_CHART_COMPONENT = "MDWChartComponent";
	private static final String COMPONENT_FAMILY = "martins.developer.world.jsf.component.helloWorld";

	private enum PropertyKeys {
		type
	};

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String imageSrc = createImageUrl(context);
		writer.startElement("img", this);
		writer.writeAttribute("src", imageSrc, "");
		writer.endElement("img");
	}

	private String createImageUrl(FacesContext context) {
		StringBuilder builder = new StringBuilder(context.getExternalContext().getRequestContextPath());
		if (builder.indexOf("?") == -1) {
			builder.append('?');
		} else {
			builder.append('&');
		}
		builder.append(MDW_CHART_COMPONENT).append("=").append(getType());
		return builder.toString();
	}

	public String getType() {
		return (String) getStateHelper().eval(PropertyKeys.type, "type");
	}

	public void setType(String type) {
		getStateHelper().put(PropertyKeys.type, type);
	}
}
