package martins.developer.world.jsf.component.webapp;

import martins.developer.world.ejb.StatefulEjb;
import martins.developer.world.ejb.StatefulEjbLocal;

import javax.ejb.EJB;
import javax.inject.Named;

@Named
public class HelloWorldBackingBean {
	private String firstName = "Martin";
    @EJB
    private StatefulEjbLocal statefulEjb;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public StatefulEjbLocal getStatefulEjb() {
        return statefulEjb;
    }

    public void setStatefulEjb(StatefulEjbLocal statefulEjb) {
        this.statefulEjb = statefulEjb;
    }
}
