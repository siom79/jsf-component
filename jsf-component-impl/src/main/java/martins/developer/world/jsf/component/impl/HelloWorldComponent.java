package martins.developer.world.jsf.component.impl;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@ResourceDependencies({ @ResourceDependency(name = "css/jsf-component.css", target = "head") })
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
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("span", this);
		writer.writeAttribute("class", "helloWorldClass", "");
		writer.writeText(String.format("Hello %s %s!", getFirstName(), getLastName()), "");
		writer.endElement("span");
	}

	public String getFirstName() {
		return (String) getStateHelper().eval(PropertyKeys.firstName);
	}

	public void setFirstName(String firstName) {
		getStateHelper().put(PropertyKeys.firstName, firstName);
	}

	public String getLastName() {
		return (String) getStateHelper().eval(PropertyKeys.lastName);
	}

	public void setLastName(String lastName) {
		getStateHelper().put(PropertyKeys.lastName, lastName);
	}
}
