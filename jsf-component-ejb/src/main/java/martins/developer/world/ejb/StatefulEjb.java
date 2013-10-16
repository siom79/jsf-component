package martins.developer.world.ejb;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Local;
import javax.ejb.Stateful;
import java.io.IOException;
import java.io.InputStream;

@Stateful
@Local(StatefulEjbLocal.class)
public class StatefulEjb {
    private static final Logger logger = LoggerFactory.getLogger(StatefulEjb.class);
    private byte[] image = new byte[0];

    public byte[] getImage() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return image;
    }

    @PostConstruct
    public void postConstruct() {
        InputStream resource = null;
        try {
            resource = StatefulEjb.class.getClassLoader().getResourceAsStream("jsf-logo.jpg");
            if (resource != null) {
                try {
                    image = IOUtils.toByteArray(resource);
                } catch (IOException e) {
                    logger.error("Failed to read input stream: "+e.getMessage());
                }
            }
        } finally {
            if(resource != null) {
                IOUtils.closeQuietly(resource);
            }
        }
    }
}
