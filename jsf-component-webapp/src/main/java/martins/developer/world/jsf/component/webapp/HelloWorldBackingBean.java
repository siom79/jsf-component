package martins.developer.world.jsf.component.webapp;

import javax.inject.Named;

@Named
public class HelloWorldBackingBean {
	private String firstName = "Martin";

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
