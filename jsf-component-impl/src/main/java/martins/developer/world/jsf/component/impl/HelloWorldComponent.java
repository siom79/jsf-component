package martins.developer.world.jsf.component.impl;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent("HelloWorld")
public class HelloWorldComponent extends UIOutput {
	private static final String COMPONENT_FAMILY = "martins.developer.world.jsf.component.helloWorld";

	private enum PropertyKeys {
		firstName, lastName
	};

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("span", this);
		writer.writeText(String.format("Hello %s %s!", getFirstName(), getLastName()), "");
		writer.endElement("span");
	}

	public String getFirstName() {
		return (String) getStateHelper().eval(PropertyKeys.firstName, "???firstName???");
	}

	public void setFirstName(String firstName) {
		getStateHelper().put(PropertyKeys.firstName, firstName);
	}

	public String getLastName() {
		return (String) getStateHelper().eval(PropertyKeys.lastName, "???lastName???");
	}

	public void setLastName(String lastName) {
		getStateHelper().put(PropertyKeys.lastName, lastName);
	}
}
